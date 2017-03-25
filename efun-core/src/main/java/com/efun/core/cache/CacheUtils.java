package com.efun.core.cache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * CacheUtils
 * 缓存工具
 *
 * @author Galen
 * @since 2016/8/14
 */
public class CacheUtils {

    private static final Logger logger = LogManager.getLogger(CacheUtils.class);

    /**
     * 缓存-数据库-get操作规范接口
     * @param getter
     * @param <T>
     * @return
     */
    public static <T> T get(Getter<T> getter) {
        T result = getter.getFromCache();
        if (result == null) {
            logger.info("get "+ getter.objectName() + " from db");
            result = getter.getFromDb();
            getter.setToCache(result);
        }
        return result;
    }

    /**
     * 缓存-数据库-set操作规范接口
     * @param setter
     * @param <T>
     * @return
     */
    public static <T> T set(Setter<T> setter) {
        T result = setter.setToDB();
        if (result != null) {
            logger.info("set "+ setter.objectName() + " to cache");
            setter.setToCache(result);
        }
        return result;
    }

    public interface Getter<T> {

        /**
         * 操作对象名称
         * @return
         */
        String objectName();

        /**
         * 从cache中获取数据
         * @return
         */
        T getFromCache();

        /**
         * cache找不到数据，则从db中获取数据
         * @return
         */
        T getFromDb();

        /**
         * 缓存数据到cache
         * @param object
         */
        void setToCache(T object);

    }

    public interface Setter<T> {

        /**
         * 操作对象名称
         * @return
         */
        String objectName();

        /**
         * 将数据写入db
         * @return
         */
        T setToDB();

        /**
         * 写入db成功后，缓存数据到cache
         * @param object
         */
        void setToCache(T object);
    }

}
