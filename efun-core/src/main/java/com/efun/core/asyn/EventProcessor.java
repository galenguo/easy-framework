package com.efun.core.asyn;

import com.efun.core.config.Configuration;
import com.efun.core.exception.EfunException;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * EventProcessor
 * 事件处理器
 *
 * @author Galen
 * @since 2016/9/16
 */
public class EventProcessor implements InitializingBean, DisposableBean {

    protected Logger logger = LogManager.getLogger(this.getClass());

    private List<EventHandler> eventHandlers = new ArrayList<EventHandler>();

    /**
     * 事件发布器
     */
    private EventPublisher publisher = null;

    /**
     * 生产者线程
     */
    private Thread thread;

    /**
     * 线程池
     */
    ExecutorService executor;

    Disruptor<EventWapper> disruptor;

    private int bufferSize = 1024;

    private volatile boolean flag = true;

    private EventWapperProducer producer;

    private Integer threadCount;

    public void setEventHandlers(List<EventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public void setPublisher(EventPublisher publisher) {
        this.publisher = publisher;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }

    protected void initProcesser() {
        //初始化disruptor相关对象
        if (threadCount == null) {
            throw new EfunException("EventProcessor: feild threadCount must be setted");
        }
        executor = Executors.newFixedThreadPool(threadCount);
        EventWapperFactory factory = new EventWapperFactory();
        disruptor = new Disruptor<EventWapper>(factory, bufferSize, executor, ProducerType.SINGLE, new YieldingWaitStrategy());

        //启动多个handler，与线程一一对应
        EventHandlerSwitch[] handlerSwitches = new EventHandlerSwitch[threadCount];
        for (int i = 0; i < threadCount; i++) {
            EventHandlerSwitch handlerSwitch = new EventHandlerSwitch();
            for (EventHandler item : eventHandlers) {
                //注册实际的eventHandler
                handlerSwitch.register(item);
            }
            handlerSwitches[i] = handlerSwitch;
        }
        disruptor.handleEventsWithWorkerPool(handlerSwitches);
        disruptor.start();
        RingBuffer<EventWapper> ringBuffer = disruptor.getRingBuffer();
        producer = new EventWapperProducer(ringBuffer);

        //监听时间发布队列，再发布到disruptor。
        // TODO: 2016/9/27 监听策略待优化
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    Event event = publisher.tryGetEvent();
                    if (event != null) {
                        producer.publishEvent(event);
                    }
                }
                flag = true;
            }
        });
        thread.start();
    }

    protected void destoryProcesser() {
        //停止线程
        flag = false;
        //循环等待生成者线程完全结束
        while (!flag) {

        }
        //关闭disruptor相关对象。
        disruptor.shutdown();
        executor.shutdown();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            initProcesser();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        logger.info("initialized eventProcesser");
    }

    @Override
    public void destroy() throws Exception {
        try {
            destoryProcesser();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
        logger.info("destroy eventProcesser");
    }

    /**
     * 事件处理handler选择
     */
    public class EventHandlerSwitch implements WorkHandler<EventWapper> {

        /**
         * 事件处理handler的Map
         */
        Map<Class, EventHandler> handlerMap = new HashMap<Class, EventHandler>();

        /**
         * 注册handler
         * @param eventHandler
         */
        public void register(EventHandler eventHandler) {
            Type type = eventHandler.getClass().getGenericSuperclass();
            Class<?> clazz = (Class<?>) ((ParameterizedType)type).getActualTypeArguments()[0];
            handlerMap.put(clazz, eventHandler);
        }

        @Override
        public void onEvent(EventWapper eventWapper) throws Exception {
            Event event = eventWapper.getActualEvent();
            EventHandler handler = handlerMap.get(event.getClass());
            if (handler == null) {
                throw new EfunException("eventType :" + event.getClass().getName() + "'s handler can not find");
            }
            try {
                if (!handler.onEvent(event)) {
                    logger.warn("event: " + event.getClass().getSimpleName() + " republish");
                    publisher.publish(event);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                publisher.publish(event);
            }
        }
    }

    /**
     * 事件wapper工厂
     */
    public class EventWapperFactory implements EventFactory<EventWapper> {

        @Override
        public EventWapper newInstance() {
            return new EventWapper();
        }
    }

    /**
     * 事件wapper生产者
     */
    public class EventWapperProducer {

        private final RingBuffer<EventWapper> ringBuffer;

        public EventWapperProducer(RingBuffer<EventWapper> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        /**
         * 发布实际的事件
         * @param event
         */
        public void publishEvent(Event event) {
            long sequence = ringBuffer.next();
            try {
                EventWapper eventWapper = ringBuffer.get(sequence);
                eventWapper.setActualEvent(event);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

    /*public static void main(String[] args) throws Exception {
        EventProcessor processor = new EventProcessor();
        List<EventHandler> list = new ArrayList<>();
        list.add(new TestEventHandler());
        processor.setEventHandlers(list);
        processor.afterPropertiesSet();
        for (int i = 0; i < 10; i++) {
            Event event = new TestEvent();
            processor.producer.publishEvent(event);
        }
        processor.destoryProcesser();
    }

    public static class TestEvent implements Event {
        @Override
        public String toString() {
            return "#####" + Thread.currentThread().getName();
        }
    }

    public static class TestEventHandler extends EventHandler<TestEvent> {

        @Override
        void onEvent(TestEvent event) {
            System.out.println(event.toString());
        }
    }*/


}
