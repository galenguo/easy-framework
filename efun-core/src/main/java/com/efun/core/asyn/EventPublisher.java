package com.efun.core.asyn;

import com.efun.core.cache.redis.RedisUtils;
import com.efun.core.config.Configuration;
import com.efun.core.utils.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EventPublisher
 * 事件发布器
 *
 * @author Galen
 * @since 2016/9/16
 */
public class EventPublisher {

    protected Logger logger = LogManager.getLogger(this.getClass());

    private static final String eventQueueCacheKey =Configuration.getProperty("platform") + "_event_queue";

    public void publish(Event event) {
        RedisUtils.rpush(eventQueueCacheKey.getBytes(), SerializationUtils.serialize(event));
    }

    public Event tryGetEvent() {
        return SerializationUtils.deserialize(RedisUtils.lpop(eventQueueCacheKey.getBytes()));
    }
}
