package com.efun.core.cache.redis.copy;

import com.efun.core.cache.redis.TimeUnitSeconds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis集群下常用的功能操作方法接口实现类
 * 
 * @author Administrator
 * 
 */
public final class RedisService implements IRedisService {
	private static final boolean IS_CLUSTER = Redis.isCluster();
	protected static final Logger logger = LogManager.getLogger(RedisPoolManager.class);

	protected RedisService() {
	}

	public Long append(byte[] key, byte[] value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.append(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long append(String key, String value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.append(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long bitcount(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.bitcount(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long bitcount(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.bitcount(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long bitcount(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.bitcount(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long bitcount(String key, long start, long end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.bitcount(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<byte[]> blpop(byte[] key) {
		ShardedJedis redis = null;
		List<byte[]> result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.blpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public Long del(byte[]... key) { //
	// ShardedJedis redis = null; //
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.del(key);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public List<String> blpop(int timeout, String key) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.blpop(timeout, key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public Long del(String... key) { //
	// ShardedJedis redis = null; //
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.del(key);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public List<String> blpop(String key) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.blpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<byte[]> brpop(byte[] key) {
		ShardedJedis redis = null;
		List<byte[]> result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.brpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<String> brpop(int timeout, String key) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.brpop(timeout, key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<String> brpop(String key) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.brpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long decr(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.decr(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long decr(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.decr(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long decrBy(byte[] key, long value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.decrBy(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long decrBy(String key, long value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.decrBy(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long del(byte[] key, boolean delay) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.del(key);
			if (delay) {
				// 间隔一段时间后再次执行，避免数据库主从同步延迟导致的问题
				redis.zadd(Redis.getCacheQueueKeyByte(), System.currentTimeMillis(), key);
			}
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long del(String key, boolean delay) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.del(key);
			if (delay) {
				// 间隔一段时间后再次执行，避免数据库主从同步延迟导致的问题
				redis.zadd(Redis.getCacheQueueKeyString(), System.currentTimeMillis(), key);
			}
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Boolean exists(byte[] key) {
		ShardedJedis redis = null;
		Boolean result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.exists(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Boolean exists(String key) {
		ShardedJedis redis = null;
		Boolean result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.exists(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long expire(byte[] key, int seconds) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long expire(String key, int seconds) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.expire(key, seconds);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long expireAt(byte[] key, long unixTime) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.expireAt(key, unixTime);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long expireAt(String key, long unixTime) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.expireAt(key, unixTime);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public byte[] get(byte[] key) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.get(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String get(String key) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.get(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public byte[] getrange(byte[] key, long startOffset, long endOffset) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String getrange(String key, long startOffset, long endOffset) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.getrange(key, startOffset, endOffset);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public byte[] getSet(byte[] key, byte[] value) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.getSet(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String getSet(String key, String value) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.getSet(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hdel(byte[] key, byte[]... fields) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hdel(key, fields);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hdel(String key, String... fields) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hdel(key, fields);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Boolean hexists(byte[] key, byte[] field) {
		ShardedJedis redis = null;
		Boolean result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hexists(key, field);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Boolean hexists(String key, String field) {
		ShardedJedis redis = null;
		Boolean result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hexists(key, field);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public byte[] hget(byte[] key, byte[] field) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hget(key, field);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String hget(String key, String field) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hget(key, field);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Map<byte[], byte[]> hgetAll(byte[] key) {
		ShardedJedis redis = null;
		Map<byte[], byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hgetAll(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Map<String, String> hgetAll(String key) {
		ShardedJedis redis = null;
		Map<String, String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hgetAll(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hincrBy(byte[] key, byte[] field, long value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hincrBy(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hincrBy(String key, String field, long value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hincrBy(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public Set<byte[]> keys(byte[] pattern) { //
	// ShardedJedis redis = null; //
	//
	// Set<byte[]> result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.keys(pattern);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	// public Set<String> keys(String pattern) { //
	// ShardedJedis redis = null; //
	//
	// Set<String> result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.keys(pattern);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public Double hincrByFloat(byte[] key, byte[] field, double value) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hincrByFloat(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Double hincrByFloat(String key, String field, double value) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hincrByFloat(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> hkeys(byte[] key) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hkeys(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> hkeys(String key) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hkeys(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hlen(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hlen(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hlen(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hlen(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<byte[]> hmget(byte[] key, byte[]... fields) {
		ShardedJedis redis = null;
		List<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hmget(key, fields);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<String> hmget(String key, String... fields) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hmget(key, fields);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String hmset(byte[] key, Map<byte[], byte[]> hash) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hmset(key, hash);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String hmset(String key, Map<String, String> hash) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hmset(key, hash);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hset(byte[] key, byte[] field, byte[] value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hset(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hset(String key, String field, String value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hset(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hsetnx(byte[] key, byte[] field, byte[] value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hsetnx(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long hsetnx(String key, String field, String value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.hsetnx(key, field, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Collection<byte[]> hvals(byte[] key) {
		ShardedJedis redis = null;
		Collection<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hvals(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<String> hvals(String key) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.hvals(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long incr(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.incr(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long incr(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.incr(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public List<byte[]> mget(byte[]... keys) { //
	// ShardedJedis redis = null; //
	//
	// List<byte[]> result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.mget(keys);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public List<String> mget(String... keys) { //
	// ShardedJedis redis = null; //
	//
	// List<String> result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.mget(keys);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public Long incrBy(byte[] key, long value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.incrBy(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long incrBy(String key, long value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.incrBy(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public Long pexpire(byte[] key, long milliseconds) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.pexpire(key, milliseconds);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public Long pexpire(String key, long milliseconds) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.pexpire(key, milliseconds);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public Long pexpireAt(byte[] key, long milliseconds) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.pexpireAt(key, milliseconds);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public Long pexpireAt(String key, long milliseconds) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.pexpireAt(key, milliseconds);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	// /**
	// * ShardedJedis中不支持
	// */
	// public String rename(byte[] oldkey, byte[] newkey) { //
	// ShardedJedis redis = null; //
	//
	// String result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.rename(oldkey, newkey);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public String rename(String oldkey, String newkey) { //
	// ShardedJedis redis = null; //
	//
	// String result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.rename(oldkey, newkey);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public Long renamenx(byte[] oldkey, byte[] newkey) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.renamenx(oldkey, newkey);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public Long renamenx(String oldkey, String newkey) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.renamenx(oldkey, newkey);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public Double incrByFloat(byte[] key, double value) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.incrByFloat(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Double incrByFloat(String key, double value) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.incrByFloat(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public byte[] rpoplpush(byte[] srckey, byte[] dstkey) { //
	// ShardedJedis redis = null; //
	//
	// byte[] result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.rpoplpush(srckey, dstkey);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public String rpoplpush(String srckey, String dstkey) { //
	// ShardedJedis redis = null; //
	//
	// String result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.rpoplpush(srckey, dstkey);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public byte[] lindex(byte[] key, long index) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.lindex(key, index);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String lindex(String key, long index) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.lindex(key, index);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long llen(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.llen(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long llen(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.llen(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public byte[] lpop(byte[] key) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String lpop(String key) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long lpush(byte[] key, byte[]... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lpush(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long lpush(String key, String... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lpush(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long lpushx(byte[] key, byte[]... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lpushx(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long lpushx(String key, String... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lpushx(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<byte[]> lrange(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		List<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.lrange(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<String> lrange(String key, long start, long end) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.lrange(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long lrem(byte[] key, long count, byte[] value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lrem(key, count, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long lrem(String key, long count, String value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lrem(key, count, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String lset(byte[] key, long index, byte[] value) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lset(key, index, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String lset(String key, long index, String value) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.lset(key, index, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String ltrim(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.ltrim(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String ltrim(String key, long start, long end) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.ltrim(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long persist(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.persist(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long persist(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.persist(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public Set<byte[]> sunion(byte[]... keys) { //
	// ShardedJedis redis = null; //
	//
	// Set<byte[]> result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.sunion(keys);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public Set<String> sunion(String... keys) { //
	// ShardedJedis redis = null; //
	//
	// Set<String> result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.sunion(keys);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public Long pfadd(byte[] key, byte[]... elements) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.pfadd(key, elements);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long pfadd(String key, String... elements) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.pfadd(key, elements);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public long pfcount(byte[] key) {
		ShardedJedis redis = null;
		long result = 0L;
		try {
			redis = Redis.loadRedis(true);
			result = redis.pfcount(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public long pfcount(String key) {
		ShardedJedis redis = null;
		long result = 0L;
		try {
			redis = Redis.loadRedis(true);
			result = redis.pfcount(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public byte[] rpop(byte[] key) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.rpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String rpop(String key) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.rpop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long rpush(byte[] key, byte[]... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.rpush(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long rpush(String key, String... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.rpush(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long rpushx(byte[] key, byte[]... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.rpushx(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long rpushx(String key, String... strings) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.rpushx(key, strings);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long sadd(byte[] key, byte[]... members) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.sadd(key, members);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long sadd(String key, String... members) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.sadd(key, members);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long scard(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.scard(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long scard(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.scard(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String set(byte[] key, byte[] value) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.set(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String set(String key, String value) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.set(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.set(key, value, nxxx, expx, time);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String set(String key, String value, String nxxx, String expx, long time) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.set(key, value, nxxx, expx, time);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String setex(byte[] key, int seconds, byte[] value) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.setex(key, seconds, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String setex(String key, int seconds, String value) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.setex(key, seconds, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long setnx(byte[] key, byte[] value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.setnx(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long setnx(String key, String value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.setnx(key, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long setrange(byte[] key, long offset, byte[] value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.setrange(key, offset, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long setrange(String key, long offset, String value) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.setrange(key, offset, value);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Boolean sismember(byte[] key, byte[] member) {
		ShardedJedis redis = null;
		Boolean result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.sismember(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Boolean sismember(String key, String member) {
		ShardedJedis redis = null;
		Boolean result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.sismember(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> smembers(byte[] key) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.smembers(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> smembers(String key) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.smembers(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<byte[]> sort(byte[] key) {
		ShardedJedis redis = null;
		List<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.sort(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		ShardedJedis redis = null;
		List<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.sort(key, sortingParameters);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<String> sort(String key) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.sort(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public List<String> sort(String key, SortingParams sortingParameters) {
		ShardedJedis redis = null;
		List<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.sort(key, sortingParameters);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	// /**
	// * ShardedJedis中不支持
	// */
	// public Long pttl(String key) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.pttl(key);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }
	// /**
	// * ShardedJedis中不支持
	// */
	// public Long pttl(byte[] key) { //
	// ShardedJedis redis = null; //
	//
	// Long result = null;
	// try { // redis = Redis.loadRedis();
	// result = redis.pttl(key);
	// } catch (Exception e) { //
	//
	// logger.error("pool or redis object or command exception:"+e.getMessage(),e);
	// } finally { // Redis.returnRedis(redis,isBroken);
	// }
	// return result;
	// }

	public byte[] spop(byte[] key) {
		ShardedJedis redis = null;
		byte[] result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.spop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String spop(String key) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.spop(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long srem(byte[] key, byte[]... members) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.srem(key, members);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long srem(String key, String... members) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.srem(key, members);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long strlen(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.strlen(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long strlen(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.strlen(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long ttl(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.ttl(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long ttl(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.ttl(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String type(byte[] key) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.type(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public String type(String key) {
		ShardedJedis redis = null;
		String result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.type(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zadd(byte[] key, double score, byte[] member) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zadd(key, score, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zadd(key, scoreMembers);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zadd(String key, double score, String member) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zadd(key, score, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zadd(key, scoreMembers);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zcard(byte[] key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zcard(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zcard(String key) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zcard(key);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zcount(byte[] key, byte[] min, byte[] max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zcount(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zcount(byte[] key, double min, double max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zcount(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zcount(String key, double min, double max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zcount(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zcount(String key, String min, String max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zcount(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Double zincrby(byte[] key, double score, byte[] member) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zincrby(key, score, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Double zincrby(String key, double score, String member) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zincrby(key, score, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zlexcount(byte[] key, byte[] min, byte[] max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zlexcount(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zlexcount(String key, String min, String max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zlexcount(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrange(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrange(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrange(String key, long start, long end) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrange(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByLex(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByLex(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrangeByLex(String key, String min, String max) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByLex(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByLex(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrangeByScore(String key, double min, double max) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrangeByScore(String key, String min, String max) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScore(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeByScoreWithScores(key, min, max, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeWithScores(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrangeWithScores(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zrank(byte[] key, byte[] member) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrank(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zrank(String key, String member) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrank(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zrem(byte[] key, byte[]... members) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zrem(key, members);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zrem(String key, String... members) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zrem(key, members);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByLex(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByLex(String key, String min, String max) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByLex(key, min, max);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByRank(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByRank(String key, long start, long end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByRank(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByScore(byte[] key, double start, double end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByScore(String key, double start, double end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zremrangeByScore(String key, String start, String end) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis();
			result = redis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrevrange(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrevrange(String key, long start, long end) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrange(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByLex(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByLex(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrevrangeByLex(String key, String max, String min) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByLex(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByLex(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		ShardedJedis redis = null;
		Set<byte[]> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrevrangeByScore(String key, String max, String min) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		ShardedJedis redis = null;
		Set<String> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScore(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeByScoreWithScores(key, max, min, offset, count);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		ShardedJedis redis = null;
		Set<Tuple> result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zrevrank(byte[] key, byte[] member) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrank(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Long zrevrank(String key, String member) {
		ShardedJedis redis = null;
		Long result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zrevrank(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Double zscore(byte[] key, byte[] member) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zscore(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public Double zscore(String key, String member) {
		ShardedJedis redis = null;
		Double result = null;
		try {
			redis = Redis.loadRedis(true);
			result = redis.zscore(key, member);
		} catch (Exception e) {
			logger.error("pool or redis object or command exception:" + e.getMessage(), e);
		} finally {
			Redis.returnRedis(redis);
		}
		return result;
	}

	public boolean tryLock(String key, boolean permanent) {
		boolean result = false;
		if (IS_CLUSTER) {
			while (!result) {
				try {
					if (permanent) {
						Long temp = Cluster.getInstance().setnx(key, "1");
						if (temp != null && temp.intValue() > 0) {
							result = true;
						}
					} else {
						String temp = Cluster.getInstance().set(key, "1", Redis.NX, Redis.EX,
								TimeUnitSeconds.CACHE_1_HOUR);
						result = "OK".equals(temp);
					}
				} catch (Exception e) {
					logger.error("pool or redis object or command exception:" + e.getMessage(), e);
				}
			}
		} else {
			ShardedJedis redis = null;
			while (!result) {
				try {
					redis = Redis.loadRedis();
					if (permanent) {
						Long temp = redis.setnx(key, "1");
						if (temp != null && temp.intValue() > 0) {
							result = true;
						}
					} else {
						String temp = redis.set(key, "1", Redis.NX, Redis.EX, TimeUnitSeconds.CACHE_1_HOUR);
						result = "OK".equals(temp);
					}
				} catch (Exception e) {
					logger.error("pool or redis object or command exception:" + e.getMessage(), e);
				} finally {
					Redis.returnRedis(redis);
				}
			}
		}

		return result;
	}

	public boolean tryLock(String key, long timeoutMillis) {
		if (timeoutMillis <= 0L) {
			return tryLock(key, false);
		} else {
			boolean result = false;
			if (IS_CLUSTER) {
				long startTime = System.currentTimeMillis();
				long currentTime = startTime;
				while ((!result) && (currentTime - startTime < timeoutMillis)) {
					try {
						String temp = Cluster.getInstance().set(key, "1", Redis.NX, Redis.EX,
								TimeUnitSeconds.CACHE_1_HOUR);
						result = "OK".equals(temp);
					} catch (Exception e) {
						logger.error("pool or redis object or command exception:" + e.getMessage(), e);
					} finally {
						currentTime = System.currentTimeMillis();
					}
				}
			} else {
				ShardedJedis redis = null;
				long startTime = System.currentTimeMillis();
				long currentTime = startTime;
				while ((!result) && (currentTime - startTime < timeoutMillis)) {
					try {
						redis = Redis.loadRedis();
						String temp = redis.set(key, "1", Redis.NX, Redis.EX, TimeUnitSeconds.CACHE_1_HOUR);
						result = "OK".equals(temp);
					} catch (Exception e) {
						logger.error("pool or redis object or command exception:" + e.getMessage(), e);
					} finally {
						Redis.returnRedis(redis);
						currentTime = System.currentTimeMillis();
					}
				}
			}

			return result;
		}
	}

	public boolean tryLock(String key, int seconds, long timeoutMillis) {
		if (timeoutMillis <= 0L) {
			return tryLock(key, false);
		} else {
			boolean result = false;
			if (IS_CLUSTER) {
				long startTime = System.currentTimeMillis();
				long currentTime = startTime;
				while ((!result) && (currentTime - startTime < timeoutMillis)) {
					try {
						String temp = Cluster.getInstance().set(key, "1", Redis.NX, Redis.EX,
								seconds > 0 ? seconds : TimeUnitSeconds.CACHE_1_HOUR);
						result = "OK".equals(temp);
					} catch (Exception e) {
						logger.error("pool or redis object or command exception:" + e.getMessage(), e);
					} finally {
						currentTime = System.currentTimeMillis();
					}
				}
			} else {
				ShardedJedis redis = null;
				long startTime = System.currentTimeMillis();
				long currentTime = startTime;
				while ((!result) && (currentTime - startTime < timeoutMillis)) {
					try {
						redis = Redis.loadRedis();
						String temp = redis.set(key, "1", Redis.NX, Redis.EX,
								seconds > 0 ? seconds : TimeUnitSeconds.CACHE_1_HOUR);
						result = "OK".equals(temp);
					} catch (Exception e) {
						logger.error("pool or redis object or command exception:" + e.getMessage(), e);
					} finally {
						Redis.returnRedis(redis);
						currentTime = System.currentTimeMillis();
					}
				}
			}

			return result;
		}
	}

	public boolean lock(String key, boolean permanent) {
		boolean result = false;
		if (IS_CLUSTER) {
			try {
				if (permanent) {
					Long temp = Cluster.getInstance().setnx(key, "1");
					if (temp != null && temp.intValue() > 0) {
						result = true;
					}
				} else {
					String temp = Cluster.getInstance().set(key, "1", Redis.NX, Redis.EX,
							TimeUnitSeconds.CACHE_1_HOUR);
					result = "OK".equals(temp);
				}
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			}
		} else {
			ShardedJedis redis = null;
			try {
				redis = Redis.loadRedis();
				if (permanent) {
					Long temp = redis.setnx(key, "1");
					if (temp != null && temp.intValue() > 0) {
						result = true;
					}
				} else {
					String temp = redis.set(key, "1", Redis.NX, Redis.EX, TimeUnitSeconds.CACHE_1_HOUR);
					result = "OK".equals(temp);
				}
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			} finally {
				Redis.returnRedis(redis);
			}
		}

		return result;
	}

	public boolean lock(String key, int seconds) {
		boolean result = false;
		if (IS_CLUSTER) {
			try {
				String temp = Cluster.getInstance().set(key, "1", Redis.NX, Redis.EX, seconds > 0 ? seconds : TimeUnitSeconds.CACHE_1_HOUR);
				result = "OK".equals(temp);
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			}
		} else {
			ShardedJedis redis = null;
			try {
				redis = Redis.loadRedis();
				String temp = redis.set(key, "1", Redis.NX, Redis.EX, seconds > 0 ? seconds : TimeUnitSeconds.CACHE_1_HOUR);
				result = "OK".equals(temp);
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			} finally {
				Redis.returnRedis(redis);
			}
		}
		
		return result;
	}
	
	public boolean isLocked(String key) {
		if (IS_CLUSTER) {
			try {
				return Cluster.getInstance().exists(key);
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			}
		} else {
			ShardedJedis redis = null;
			try {
				redis = Redis.loadRedis();
				return redis.exists(key);
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			} finally {
				Redis.returnRedis(redis);
			}
		}
		
		return false;
	}

	public Long unlock(String key) {
		Long result = null;
		if (IS_CLUSTER) {
			try {
				result = Cluster.getInstance().del(key);
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			}
		} else {
			ShardedJedis redis = null;
			try {
				redis = Redis.loadRedis();
				result = redis.del(key);
			} catch (Exception e) {
				logger.error("pool or redis object or command exception:" + e.getMessage(), e);
			} finally {
				Redis.returnRedis(redis);
			}
		}

		return result;
	}

}
