package com.efun.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

/**
 * SerializationUtils
 * 序列号工具列
 *
 * @author Galen
 * @since 2016/9/9
 */
public class SerializationUtils extends org.apache.commons.lang3.SerializationUtils {

    private static final Logger logger = LogManager.getLogger(SerializationUtils.class);

    public static byte[] serialize(Serializable object) {
        byte[] result = null;
        try {
            org.apache.commons.lang3.SerializationUtils.serialize(object);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T> T deserialize(byte[] bytes) {
        T result = null;
        try {
            org.apache.commons.lang3.SerializationUtils.deserialize(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static Map<byte[], byte[]> serialize(Map<String, Serializable> map) {
        Map<byte[], byte[]> result = null;
        try {
            result = new HashMap<byte[], byte[]>();
            for (Map.Entry<String, Serializable> item : map.entrySet()) {
                result.put(item.getKey().getBytes(), serialize(item.getValue()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T> Map<String, T> deserialize(Map<byte[], byte[]> map) {
        Map<String, T> result = null;
        try {
            result = new HashMap<String, T>();
            for (Map.Entry<byte[], byte[]> item : map.entrySet()) {
                result.put(new String(item.getKey()), deserialize(item.getValue()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static Set<byte[]> serialize(Set<Serializable> set) {
        Set<byte[]> result = null;
        try {
            result = new HashSet<byte[]>();
            for (Serializable item : set) {
                result.add(serialize(item));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T> Set<T> deserialize(Set<byte[]> set) {
        Set<T> result = null;
        try {
            result = new HashSet<T>();
            for (byte[] item : set) {
                result.add(deserialize(item));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static List<byte[]> serialize(List<Serializable> list) {
        List<byte[]> result = null;
        try {
            result = new ArrayList<byte[]>();
            for (Serializable item : list) {
                result.add(serialize(item));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T> List<T> deserialize(List<byte[]> list) {
        List<T> result = null;
        try {
            result = new ArrayList<T>();
            for (byte[] item : list) {
                result.add(deserialize(item));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
