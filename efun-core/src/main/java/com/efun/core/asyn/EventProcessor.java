package com.efun.core.asyn;

import com.efun.core.exception.EfunException;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
     * 线程工厂
     */
    private ThreadFactory threadFactory;

    Disruptor<EventWapper> disruptor;

    private int bufferSize = 1024;

    private volatile boolean flag = true;

    public void setEventHandlers(List<EventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public void setPublisher(EventPublisher publisher) {
        this.publisher = publisher;
    }

    protected void initProcesser() {
        //初始化disruptor相关对象
        threadFactory = Executors.defaultThreadFactory();
        EventWapperFactory factory = new EventWapperFactory();
        disruptor = new Disruptor<EventWapper>(factory, bufferSize, threadFactory);
        disruptor.handleEventsWith(new EventHandlerSwitch());
        disruptor.start();
        RingBuffer<EventWapper> ringBuffer = disruptor.getRingBuffer();
        EventWapperProducer producer = new EventWapperProducer(ringBuffer);

        //监听时间发布队列，再发布到disruptor。
        Runnable runnable = new Runnable() {
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
        };
        thread = new Thread(runnable);
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
    public class EventHandlerSwitch implements com.lmax.disruptor.EventHandler<EventWapper> {

        /**
         * 事件处理handler的Map
         */
        Map<Class, EventHandler> handlerMap = new HashMap<Class, EventHandler>();

        /**
         * 注册handler
         * @param eventHandler
         */
        public void register(EventHandler eventHandler) {
            Type[] types = eventHandler.getClass().getGenericInterfaces();
            Class<?> clazz = (Class<?>) ((ParameterizedType)types[0]).getActualTypeArguments()[0];
            handlerMap.put(clazz, eventHandler);
        }

        @Override
        public void onEvent(EventWapper eventWapper, long sequence, boolean endOfBatch) throws Exception {
            Event event = eventWapper.getActualEvent();
            EventHandler handler = handlerMap.get(event.getClass());
            if (handler == null) {
                throw new EfunException("eventType :" + event.getClass().getName() + "'s handler can not find");
            }
            try {
                handler.onEvent(event, sequence, endOfBatch);
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
     * 生产者
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

}
