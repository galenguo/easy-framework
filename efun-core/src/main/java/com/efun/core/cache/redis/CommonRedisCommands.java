package com.efun.core.cache.redis;

import redis.clients.jedis.BinaryJedisClusterCommands;
import redis.clients.jedis.BinaryJedisCommands;
import redis.clients.jedis.JedisCommands;

/**
 * CommonRedisCommands
 * redis常用命令接口
 *
 * @author Galen
 * @since 2016/8/2
 */
public interface CommonRedisCommands extends JedisCommands, BinaryJedisCommands, BinaryJedisClusterCommands {

}
