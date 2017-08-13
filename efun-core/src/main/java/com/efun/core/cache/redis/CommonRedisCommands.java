package com.efun.core.cache.redis;

import redis.clients.jedis.BinaryJedisClusterCommands;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CommonRedisCommands
 * redis常用命令接口
 *
 * @author Galen
 * @since 2016/8/2
 */
public interface CommonRedisCommands extends JedisCommands, BinaryJedisCommands, BinaryJedisClusterCommands {

    <T> T get(String key, Class<T> clz);

    <T> String set(String key, T value);

    <T> Long setnx(String key, T value);

    <T> String setex(String key, int seconds, T value);

    <T> T hget(String key, String field, Class<T> clz);

    <T> Long hset(String key, String field, T value);

    <T> Long hsetnx(String key, String field, T value);

    <T> Map<String, T> hgetAll(String key, Class<T> clz);

    <T> T rpop(String key, Class<T> clz);

    <T> Long rpush(String key, T... value);

    <T> Long rpushx(String key, T... value);

    <T> T lpop(String key, Class<T> clz);

    <T> Long lpush(String key, T... value);

    <T> Long lpushx(String key, T... value);

    <T> List<T> lrange(String key, long start, long end, Class<T> clz);

    <T> Long sadd(String key, T... value);

    <T> Set<T> smembers(String key, Class<T> clz);

    <T> T spop(String key, Class<T> clz);

    <T> Long zadd(String key, double score, T member);

    <T> Set<T> zrange(String key, long start, long end, Class<T> clz);

    <T> Set<T> zrevrange(String key, long start, long end, Class<T> clz);

    <T> Set<T> zrangeByScore(String key, long start, long end, Class<T> clz);

    <T> Set<T> zrevrangeByScore(String key, long start, long end, Class<T> clz);

    <T> Set<T> zrangeByLex(String key, String min, String max, int offset, int count, Class<T> clz);

}
