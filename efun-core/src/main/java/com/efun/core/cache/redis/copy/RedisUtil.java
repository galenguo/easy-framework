package com.efun.core.cache.redis.copy;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis集群下常用的功能操作方法
 * 
 * @author Administrator
 * 
 */
public class RedisUtil {
	private static final IRedisService redisService = new RedisService();
	private static final boolean IS_CLUSTER = Redis.isCluster();

	public static final Long append(byte[] key, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().append(key, value);
		}
		return redisService.append(key, value);
	}

	public static final Long append(String key, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().append(key, value);
		}
		return redisService.append(key, value);
	}

	public static final Long bitcount(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().bitcount(key);
		}
		return redisService.bitcount(key);
	}

	public static final Long bitcount(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().bitcount(key, start, end);
		}
		return redisService.bitcount(key, start, end);
	}

	public static final Long bitcount(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().bitcount(key);
		}
		return redisService.bitcount(key);
	}

	public static final Long bitcount(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().bitcount(key, start, end);
		}
		return redisService.bitcount(key, start, end);
	}

	public static final List<String> blpop(int timeout, String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().blpop(timeout, key);
		}
		return redisService.blpop(timeout, key);
	}

	public static final List<String> brpop(int timeout, String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().brpop(timeout, key);
		}
		return redisService.brpop(timeout, key);
	}

	public static final Long decr(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().decr(key);
		}
		return redisService.decr(key);
	}

	public static final Long decr(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().decr(key);
		}
		return redisService.decr(key);
	}

	public static final Long decrBy(byte[] key, long value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().decrBy(key, value);
		}
		return redisService.decrBy(key, value);
	}

	// /**
	// * ShardedJedis中不支持
	// * @param key
	// * @return
	// */
	// public static final Long del(byte[]... key) {
	// return redisService.del(key);
	// }

	public static final Long decrBy(String key, long value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().decrBy(key, value);
		}
		return redisService.decrBy(key, value);
	}

	// /**
	// * ShardedJedis中不支持
	// * @param key
	// * @return
	// */
	// public static final Long del(String... key) {
	// return redisService.del(key);
	// }

	public static final Long del(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().del(key);
		}
		return del(key, false);
	}

	public static final Long del(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().del(key);
		}
		return del(key, false);
	}

	public static final Long del(byte[] key, boolean delay) {
		if (IS_CLUSTER) {
			if (delay) {
				// 间隔一段时间后再次执行，避免数据库主从同步延迟导致的问题
				Cluster.getInstance().zadd(Redis.getCacheQueueKeyByte(), System.currentTimeMillis(), key);
			}
			return Cluster.getInstance().del(key);
		}
		return redisService.del(key, delay);
	}

	public static final Long del(String key, boolean delay) {
		if (IS_CLUSTER) {
			if (delay) {
				// 间隔一段时间后再次执行，避免数据库主从同步延迟导致的问题
				Cluster.getInstance().zadd(Redis.getCacheQueueKeyString(), System.currentTimeMillis(),
						key);
			}
			return Cluster.getInstance().del(key);
		}
		return redisService.del(key, delay);
	}

	public static final Boolean exists(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().exists(key);
		}
		return redisService.exists(key);
	}

	public static final Boolean exists(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().exists(key);
		}
		return redisService.exists(key);
	}

	public static final Long expire(byte[] key, int seconds) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().expire(key, seconds);
		}
		return redisService.expire(key, seconds);
	}

	public static final Long expire(String key, int seconds) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().expire(key, seconds);
		}
		return redisService.expire(key, seconds);
	}

	public static final Long expireAt(byte[] key, long unixTime) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().expireAt(key, unixTime);
		}
		return redisService.expireAt(key, unixTime);
	}

	public static final Long expireAt(String key, long unixTime) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().expireAt(key, unixTime);
		}
		return redisService.expireAt(key, unixTime);
	}

	public static final byte[] get(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().get(key);
		}
		return redisService.get(key);
	}

	public static final String get(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().get(key);
		}
		return redisService.get(key);
	}

	public static final byte[] getrange(byte[] key, long startOffset, long endOffset) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().getrange(key, startOffset, endOffset);
		}
		return redisService.getrange(key, startOffset, endOffset);
	}

	public static final String getrange(String key, long startOffset, long endOffset) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().getrange(key, startOffset, endOffset);
		}
		return redisService.getrange(key, startOffset, endOffset);
	}

	/**
	 * 调用此方法，使用完redis之后，一定要调用
	 * {@link com.efun.mainland.util.redis.RedisUtil#returnRedis(ShardedJedis redis)}
	 * <br/>
	 * 如果是redis服务器端集群，则返回null
	 * {@link com.efun.mainland.util.redis.RedisUtil#returnRedis(ShardedJedis redis)}
	 * 
	 * @see com.efun.mainland.util.redis.RedisUtil#returnRedis(ShardedJedis redis)
	 * @return
	 * @throws Exception 
	 */
	public static final ShardedJedis loadRedis() throws Exception {
		return Redis.loadRedis();
	}

	/**
	 * 如果不是使用redis服务器端集群，则返回null
	 * 
	 * @return
	 */
	public static final RedisCluster loadCluster() {
		return Redis.loadCluster();
	}

	/**
	 * 调用此方法，使用完redis之后，一定要调用
	 * {@link com.efun.mainland.util.redis.RedisUtil#returnRedis(ShardedJedis redis)}
	 * 
	 * @see com.efun.mainland.util.redis.RedisUtil#returnRedis(ShardedJedis redis)
	 * @return
	 * @throws Exception 
	 */
	public static final ShardedJedis loadRedis(boolean readonly) throws Exception {
		return Redis.loadRedis(readonly);
	}

	public static final byte[] getSet(byte[] key, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().getSet(key, value);
		}
		return redisService.getSet(key, value);
	}

	public static final String getSet(String key, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().getSet(key, value);
		}
		return redisService.getSet(key, value);
	}

	public static final Long hdel(byte[] key, byte[]... fields) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hdel(key, fields);
		}
		return redisService.hdel(key, fields);
	}

	public static final Long hdel(String key, String... fields) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hdel(key, fields);
		}
		return redisService.hdel(key, fields);
	}

	public static final Boolean hexists(byte[] key, byte[] field) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hexists(key, field);
		}
		return redisService.hexists(key, field);
	}

	public static final Boolean hexists(String key, String field) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hexists(key, field);
		}
		return redisService.hexists(key, field);
	}

	public static final byte[] hget(byte[] key, byte[] field) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hget(key, field);
		}
		return redisService.hget(key, field);
	}

	public static final String hget(String key, String field) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hget(key, field);
		}
		return redisService.hget(key, field);
	}

	public static final Map<byte[], byte[]> hgetAll(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hgetAll(key);
		}
		return redisService.hgetAll(key);
	}

	public static final Map<String, String> hgetAll(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hgetAll(key);
		}
		return redisService.hgetAll(key);
	}

	public static final Long hincrBy(byte[] key, byte[] field, long value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hincrBy(key, field, value);
		}
		return redisService.hincrBy(key, field, value);
	}

	public static final Long hincrBy(String key, String field, long value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hincrBy(key, field, value);
		}
		return redisService.hincrBy(key, field, value);
	}

	public static final Double hincrByFloat(byte[] key, byte[] field, double value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hincrByFloat(key, field, value);
		}
		return redisService.hincrByFloat(key, field, value);
	}

	public static final Double hincrByFloat(String key, String field, double value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hincrByFloat(key, field, value);
		}
		return redisService.hincrByFloat(key, field, value);
	}

	public static final Set<byte[]> hkeys(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hkeys(key);
		}
		return redisService.hkeys(key);
	}

	public static final Set<String> hkeys(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hkeys(key);
		}
		return redisService.hkeys(key);
	}

	public static final Long hlen(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hlen(key);
		}
		return redisService.hlen(key);
	}

	public static final Long hlen(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hlen(key);
		}
		return redisService.hlen(key);
	}

	public static final List<byte[]> hmget(byte[] key, byte[]... fields) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hmget(key, fields);
		}
		return redisService.hmget(key, fields);
	}

	// //ShardedJedis中不支持
	// public static final Set<byte[]> keys(byte[] pattern) {
	// return redisService.keys(pattern);
	// }
	// ShardedJedis中不支持
	// public static final Set<String> keys(String pattern) {
	// return redisService.keys(pattern);
	// }

	public static final List<String> hmget(String key, String... fields) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hmget(key, fields);
		}
		return redisService.hmget(key, fields);
	}

	public static final String hmset(byte[] key, Map<byte[], byte[]> hash) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hmset(key, hash);
		}
		return redisService.hmset(key, hash);
	}

	public static final String hmset(String key, Map<String, String> hash) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hmset(key, hash);
		}
		return redisService.hmset(key, hash);
	}

	public static final Long hset(byte[] key, byte[] field, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hset(key, field, value);
		}
		return redisService.hset(key, field, value);
	}

	public static final Long hset(String key, String field, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hset(key, field, value);
		}
		return redisService.hset(key, field, value);
	}

	public static final Long hsetnx(byte[] key, byte[] field, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hsetnx(key, field, value);
		}
		return redisService.hsetnx(key, field, value);
	}

	public static final Long hsetnx(String key, String field, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hsetnx(key, field, value);
		}
		return redisService.hsetnx(key, field, value);
	}

	public static final Collection<byte[]> hvals(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hvals(key);
		}
		return redisService.hvals(key);
	}

	public static final List<String> hvals(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().hvals(key);
		}
		return redisService.hvals(key);
	}

	public static final Long incr(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().incr(key);
		}
		return redisService.incr(key);
	}

	public static final Long incr(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().incr(key);
		}
		return redisService.incr(key);
	}

	public static final Long incrBy(byte[] key, long value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().incrBy(key, value);
		}
		return redisService.incrBy(key, value);
	}

	public static final Long incrBy(String key, long value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().incrBy(key, value);
		}
		return redisService.incrBy(key, value);
	}

	public static final Double incrByFloat(byte[] key, double value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().incrByFloat(key, value);
		}
		return redisService.incrByFloat(key, value);
	}

	public static final Double incrByFloat(String key, double value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().incrByFloat(key, value);
		}
		return redisService.incrByFloat(key, value);
	}

	public static final byte[] lindex(byte[] key, long index) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lindex(key, index);
		}
		return redisService.lindex(key, index);
	}

	public static final String lindex(String key, long index) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lindex(key, index);
		}
		return redisService.lindex(key, index);
	}

	public static final Long llen(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().llen(key);
		}
		return redisService.llen(key);
	}

	// //ShardedJedis中不支持
	// public static final List<byte[]> mget(byte[]... keys) {
	// return redisService.mget(keys);
	// }
	// //ShardedJedis中不支持
	// public static final List<String> mget(String... keys) {
	// return redisService.mget(keys);
	// }
	// //ShardedJedis中不支持
	// public static final Long persist(byte[] key) {
	// return redisService.persist(key);
	// }
	// //ShardedJedis中不支持
	// public static final Long persist(String key) {
	// return redisService.persist(key);
	// }
	// //ShardedJedis中不支持
	// public static final Long pexpire(byte[] key, long milliseconds) {
	// return redisService.pexpire(key, milliseconds);
	// }
	// //ShardedJedis中不支持
	// public static final Long pexpire(String key, long milliseconds) {
	// return redisService.pexpire(key, milliseconds);
	// }
	// //ShardedJedis中不支持
	// public static final Long pexpireAt(byte[] key, long milliseconds) {
	// return redisService.pexpireAt(key, milliseconds);
	// }
	// //ShardedJedis中不支持
	// public static final Long pexpireAt(String key, long milliseconds) {
	// return redisService.pexpireAt(key, milliseconds);
	// }
	// //ShardedJedis中不支持
	// public static final String rename(byte[] oldkey, byte[] newkey) {
	// return redisService.rename(oldkey, newkey);
	// }
	// //ShardedJedis中不支持
	// public static final String rename(String oldkey, String newkey) {
	// return redisService.rename(oldkey, newkey);
	// }
	// //ShardedJedis中不支持
	// public static final Long renamenx(byte[] oldkey, byte[] newkey) {
	// return redisService.renamenx(oldkey, newkey);
	// }
	// //ShardedJedis中不支持
	// public static final Long renamenx(String oldkey, String newkey) {
	// return redisService.renamenx(oldkey, newkey);
	// }

	public static final Long llen(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().llen(key);
		}
		return redisService.llen(key);
	}

	public static final byte[] lpop(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lpop(key);
		}
		return redisService.lpop(key);
	}

	// //ShardedJedis中不支持
	// public static final byte[] rpoplpush(byte[] srckey, byte[] dstkey) {
	// return redisService.rpoplpush(srckey, dstkey);
	// }
	// //ShardedJedis中不支持
	// public static final String rpoplpush(String srckey, String dstkey) {
	// return redisService.rpoplpush(srckey, dstkey);
	// }

	public static final String lpop(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lpop(key);
		}
		return redisService.lpop(key);
	}

	public static final Long lpush(byte[] key, byte[]... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lpush(key, strings);
		}
		return redisService.lpush(key, strings);
	}

	public static final Long lpush(String key, String... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lpush(key, strings);
		}
		return redisService.lpush(key, strings);
	}

	public static final Long lpushx(byte[] key, byte[]... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lpushx(key, strings);
		}
		return redisService.lpushx(key, strings);
	}

	public static final Long lpushx(String key, String... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lpushx(key, strings);
		}
		return redisService.lpushx(key, strings);
	}

	public static final List<byte[]> lrange(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lrange(key, start, end);
		}
		return redisService.lrange(key, start, end);
	}

	public static final List<String> lrange(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lrange(key, start, end);
		}
		return redisService.lrange(key, start, end);
	}

	public static final Long lrem(byte[] key, long count, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lrem(key, count, value);
		}
		return redisService.lrem(key, count, value);
	}

	public static final Long lrem(String key, long count, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lrem(key, count, value);
		}
		return redisService.lrem(key, count, value);
	}

	public static final String lset(byte[] key, long index, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lset(key, index, value);
		}
		return redisService.lset(key, index, value);
	}

	public static final String lset(String key, long index, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().lset(key, index, value);
		}
		return redisService.lset(key, index, value);
	}

	public static final String ltrim(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().ltrim(key, start, end);
		}
		return redisService.ltrim(key, start, end);
	}

	public static final String ltrim(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().ltrim(key, start, end);
		}
		return redisService.ltrim(key, start, end);
	}

	public static final Long pfadd(byte[] key, byte[]... elements) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().pfadd(key, elements);
		}
		return redisService.pfadd(key, elements);
	}

	public static final Long pfadd(String key, String... elements) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().pfadd(key, elements);
		}
		return redisService.pfadd(key, elements);
	}

	public static final long pfcount(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().pfcount(key);
		}
		return redisService.pfcount(key);
	}

	public static final long pfcount(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().pfcount(key);
		}
		return redisService.pfcount(key);
	}

	/**
	 * 释放redis链接
	 * 
	 * @param redis
	 * @throws Exception
	 */
	public static final void returnRedis(ShardedJedis redis) {
		if (IS_CLUSTER) {
			return;
		}
		Redis.returnRedis(redis);
	}

	public static final byte[] rpop(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().rpop(key);
		}
		return redisService.rpop(key);
	}

	public static final String rpop(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().rpop(key);
		}
		return redisService.rpop(key);
	}

	public static final Long rpush(byte[] key, byte[]... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().rpush(key, strings);
		}
		return redisService.rpush(key, strings);
	}

	public static final Long rpush(String key, String... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().rpush(key, strings);
		}
		return redisService.rpush(key, strings);
	}

	// //ShardedJedis中不支持
	// public static final Set<byte[]> sunion(byte[]... key) {
	// return redisService.sunion(key);
	// }
	// //ShardedJedis中不支持
	// public static final Set<String> sunion(String... key) {
	// return redisService.sunion(key);
	// }

	public static final Long rpushx(byte[] key, byte[]... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().rpushx(key, strings);
		}
		return redisService.rpushx(key, strings);
	}

	public static final Long rpushx(String key, String... strings) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().rpushx(key, strings);
		}
		return redisService.rpushx(key, strings);
	}

	public static final Long sadd(byte[] key, byte[]... members) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sadd(key, members);
		}
		return redisService.sadd(key, members);
	}

	public static final Long sadd(String key, String... members) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sadd(key, members);
		}
		return redisService.sadd(key, members);
	}

	public static final Long scard(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().scard(key);
		}
		return redisService.scard(key);
	}

	public static final Long scard(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().scard(key);
		}
		return redisService.scard(key);
	}

	public static final String set(byte[] key, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().set(key, value);
		}
		return redisService.set(key, value);
	}

	public static final String set(String key, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().set(key, value);
		}
		return redisService.set(key, value);
	}

	public static final String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().set(key, value, nxxx, expx, time);
		}
		return redisService.set(key, value, nxxx, expx, time);
	}

	public static final String set(String key, String value, String nxxx, String expx, long time) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().set(key, value, nxxx, expx, time);
		}
		return redisService.set(key, value, nxxx, expx, time);
	}

	public static final String setex(byte[] key, int seconds, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().setex(key, seconds, value);
		}
		return redisService.setex(key, seconds, value);
	}

	public static final String setex(String key, int seconds, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().setex(key, seconds, value);
		}
		return redisService.setex(key, seconds, value);
	}

	public static final Long setnx(byte[] key, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().setnx(key, value);
		}
		return redisService.setnx(key, value);
	}

	public static final Long setnx(String key, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().setnx(key, value);
		}
		return redisService.setnx(key, value);
	}

	public static final Long setrange(byte[] key, long offset, byte[] value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().setrange(key, offset, value);
		}
		return redisService.setrange(key, offset, value);
	}

	public static final Long setrange(String key, long offset, String value) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().setrange(key, offset, value);
		}
		return redisService.setrange(key, offset, value);
	}

	public static final Boolean sismember(byte[] key, byte[] member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sismember(key, member);
		}
		return redisService.sismember(key, member);
	}

	public static final Boolean sismember(String key, String member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sismember(key, member);
		}
		return redisService.sismember(key, member);
	}

	// //ShardedJedis中不支持
	// public static final Long pttl(String key){
	// return redisService.pttl(key);
	// }
	// //ShardedJedis中不支持
	// public static final Long pttl(byte[] key){
	// return redisService.pttl(key);
	// }

	public static final Set<byte[]> smembers(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().smembers(key);
		}
		return redisService.smembers(key);
	}

	public static final Set<String> smembers(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().smembers(key);
		}
		return redisService.smembers(key);
	}

	public static final List<byte[]> sort(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sort(key);
		}
		return redisService.sort(key);
	}

	public static final List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sort(key, sortingParameters);
		}
		return redisService.sort(key, sortingParameters);
	}

	public static final List<String> sort(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sort(key);
		}
		return redisService.sort(key);
	}

	public static final List<String> sort(String key, SortingParams sortingParameters) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().sort(key, sortingParameters);
		}
		return redisService.sort(key, sortingParameters);
	}

	public static final byte[] spop(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().spop(key);
		}
		return redisService.spop(key);
	}

	public static final String spop(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().spop(key);
		}
		return redisService.spop(key);
	}

	public static final Long srem(byte[] key, byte[]... members) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().srem(key, members);
		}
		return redisService.srem(key, members);
	}

	public static final Long srem(String key, String... members) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().srem(key, members);
		}
		return redisService.srem(key, members);
	}

	public static final Long strlen(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().strlen(key);
		}
		return redisService.strlen(key);
	}

	public static final Long strlen(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().strlen(key);
		}
		return redisService.strlen(key);
	}

	public static final Long ttl(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().ttl(key);
		}
		return redisService.ttl(key);
	}

	public static final Long ttl(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().ttl(key);
		}
		return redisService.ttl(key);
	}

	public static final String type(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().type(key);
		}
		return redisService.type(key);
	}

	public static final String type(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().type(key);
		}
		return redisService.type(key);
	}

	public static final Long zadd(byte[] key, double score, byte[] member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zadd(key, score, member);
		}
		return redisService.zadd(key, score, member);
	}

	public static final Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zadd(key, scoreMembers);
		}
		return redisService.zadd(key, scoreMembers);
	}

	public static final Long zadd(String key, double score, String member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zadd(key, score, member);
		}
		return redisService.zadd(key, score, member);
	}

	public static final Long zadd(String key, Map<String, Double> scoreMembers) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zadd(key, scoreMembers);
		}
		return redisService.zadd(key, scoreMembers);
	}

	public static final Long zcard(byte[] key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zcard(key);
		}
		return redisService.zcard(key);
	}

	public static final Long zcard(String key) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zcard(key);
		}
		return redisService.zcard(key);
	}

	public static final Long zcount(byte[] key, byte[] min, byte[] max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zcount(key, min, max);
		}
		return redisService.zcount(key, min, max);
	}

	public static final Long zcount(byte[] key, double min, double max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zcount(key, min, max);
		}
		return redisService.zcount(key, min, max);
	}

	public static final Long zcount(String key, double min, double max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zcount(key, min, max);
		}
		return redisService.zcount(key, min, max);
	}

	public static final Long zcount(String key, String min, String max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zcount(key, min, max);
		}
		return redisService.zcount(key, min, max);
	}

	public static final Double zincrby(byte[] key, double score, byte[] member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zincrby(key, score, member);
		}
		return redisService.zincrby(key, score, member);
	}

	public static final Double zincrby(String key, double score, String member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zincrby(key, score, member);
		}
		return redisService.zincrby(key, score, member);
	}

	public static final Long zlexcount(byte[] key, byte[] min, byte[] max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zlexcount(key, min, max);
		}
		return redisService.zlexcount(key, min, max);
	}

	public static final Long zlexcount(String key, String min, String max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zlexcount(key, min, max);
		}
		return redisService.zlexcount(key, min, max);
	}

	public static final Set<byte[]> zrange(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrange(key, start, end);
		}
		return redisService.zrange(key, start, end);
	}

	public static final Set<String> zrange(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrange(key, start, end);
		}
		return redisService.zrange(key, start, end);
	}

	public static final Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByLex(key, min, max);
		}
		return redisService.zrangeByLex(key, min, max);
	}

	public static final Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByLex(key, min, max, offset, count);
		}
		return redisService.zrangeByLex(key, min, max, offset, count);
	}

	public static final Set<String> zrangeByLex(String key, String min, String max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByLex(key, min, max);
		}
		return redisService.zrangeByLex(key, min, max);
	}

	public static final Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByLex(key, min, max, offset, count);
		}
		return redisService.zrangeByLex(key, min, max, offset, count);
	}

	public static final Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max);
		}
		return redisService.zrangeByScore(key, min, max);
	}

	public static final Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max, offset, count);
		}
		return redisService.zrangeByScore(key, min, max, offset, count);
	}

	public static final Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max);
		}
		return redisService.zrangeByScore(key, min, max);
	}

	public static final Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max, offset, count);
		}
		return redisService.zrangeByScore(key, min, max, offset, count);
	}

	public static final Set<String> zrangeByScore(String key, double min, double max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max);
		}
		return redisService.zrangeByScore(key, min, max);
	}

	public static final Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max, offset, count);
		}
		return redisService.zrangeByScore(key, min, max, offset, count);
	}

	public static final Set<String> zrangeByScore(String key, String min, String max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max);
		}
		return redisService.zrangeByScore(key, min, max);
	}

	public static final Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScore(key, min, max, offset, count);
		}
		return redisService.zrangeByScore(key, min, max, offset, count);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max);
		}
		return redisService.zrangeByScoreWithScores(key, min, max);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
		}
		return redisService.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max);
		}
		return redisService.zrangeByScoreWithScores(key, min, max);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
		}
		return redisService.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max);
		}
		return redisService.zrangeByScoreWithScores(key, min, max);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
		}
		return redisService.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max);
		}
		return redisService.zrangeByScoreWithScores(key, min, max);
	}

	public static final Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
		}
		return redisService.zrangeByScoreWithScores(key, min, max, offset, count);
	}

	public static final Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeWithScores(key, start, end);
		}
		return redisService.zrangeWithScores(key, start, end);
	}

	public static final Set<Tuple> zrangeWithScores(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrangeWithScores(key, start, end);
		}
		return redisService.zrangeWithScores(key, start, end);
	}

	public static final Long zrank(byte[] key, byte[] member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrank(key, member);
		}
		return redisService.zrank(key, member);
	}

	public static final Long zrank(String key, String member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrank(key, member);
		}
		return redisService.zrank(key, member);
	}

	public static final Long zrem(byte[] key, byte[]... members) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrem(key, members);
		}
		return redisService.zrem(key, members);
	}

	public static final Long zrem(String key, String... members) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrem(key, members);
		}
		return redisService.zrem(key, members);
	}

	public static final Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByLex(key, min, max);
		}
		return redisService.zremrangeByLex(key, min, max);
	}

	public static final Long zremrangeByLex(String key, String min, String max) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByLex(key, min, max);
		}
		return redisService.zremrangeByLex(key, min, max);
	}

	public static final Long zremrangeByRank(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByRank(key, start, end);
		}
		return redisService.zremrangeByRank(key, start, end);
	}

	public static final Long zremrangeByRank(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByRank(key, start, end);
		}
		return redisService.zremrangeByRank(key, start, end);
	}

	public static final Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByScore(key, start, end);
		}
		return redisService.zremrangeByScore(key, start, end);
	}

	public static final Long zremrangeByScore(byte[] key, double start, double end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByScore(key, start, end);
		}
		return redisService.zremrangeByScore(key, start, end);
	}

	public static final Long zremrangeByScore(String key, double start, double end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByScore(key, start, end);
		}
		return redisService.zremrangeByScore(key, start, end);
	}

	public static final Long zremrangeByScore(String key, String start, String end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zremrangeByScore(key, start, end);
		}
		return redisService.zremrangeByScore(key, start, end);
	}

	public static final Set<byte[]> zrevrange(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrange(key, start, end);
		}
		return redisService.zrevrange(key, start, end);
	}

	public static final Set<String> zrevrange(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrange(key, start, end);
		}
		return redisService.zrevrange(key, start, end);
	}

	public static final Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByLex(key, max, min);
		}
		return redisService.zrevrangeByLex(key, max, min);
	}

	public static final Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByLex(key, max, min, offset, count);
		}
		return redisService.zrevrangeByLex(key, max, min, offset, count);
	}

	public static final Set<String> zrevrangeByLex(String key, String max, String min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByLex(key, max, min);
		}
		return redisService.zrevrangeByLex(key, max, min);
	}

	public static final Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByLex(key, max, min, offset, count);
		}
		return redisService.zrevrangeByLex(key, max, min, offset, count);
	}

	public static final Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min);
		}
		return redisService.zrevrangeByScore(key, max, min);
	}

	public static final Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScore(key, max, min, offset, count);
	}

	public static final Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min);
		}
		return redisService.zrevrangeByScore(key, max, min);
	}

	public static final Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScore(key, max, min, offset, count);
	}

	public static final Set<String> zrevrangeByScore(String key, double max, double min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min);
		}
		return redisService.zrevrangeByScore(key, max, min);
	}

	public static final Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScore(key, max, min, offset, count);
	}

	public static final Set<String> zrevrangeByScore(String key, String max, String min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min);
		}
		return redisService.zrevrangeByScore(key, max, min);
	}

	public static final Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScore(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScore(key, max, min, offset, count);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset,
			int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset,
			int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset,
			int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min);
	}

	public static final Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset,
			int count) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
		}
		return redisService.zrevrangeByScoreWithScores(key, max, min, offset, count);
	}

	public static final Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeWithScores(key, start, end);
		}
		return redisService.zrevrangeWithScores(key, start, end);
	}

	public static final Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrangeWithScores(key, start, end);
		}
		return redisService.zrevrangeWithScores(key, start, end);
	}

	public static final Long zrevrank(byte[] key, byte[] member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrank(key, member);
		}
		return redisService.zrevrank(key, member);
	}

	public static final Long zrevrank(String key, String member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zrevrank(key, member);
		}
		return redisService.zrevrank(key, member);
	}

	public static final Double zscore(byte[] key, byte[] member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zscore(key, member);
		}
		return redisService.zscore(key, member);
	}

	public static final Double zscore(String key, String member) {
		if (IS_CLUSTER) {
			return Cluster.getInstance().zscore(key, member);
		}
		return redisService.zscore(key, member);
	}

	public static final boolean tryLock(String key, boolean permanent) {
		return redisService.tryLock(key, permanent);
	}

	public static final boolean tryLock(String key, long timeoutMillis) {
		return redisService.tryLock(key, timeoutMillis);
	}

	public static final boolean tryLock(String key, int seconds, long timeoutMillis) {
		return redisService.tryLock(key, seconds, timeoutMillis);
	}

	public static final boolean lock(String key, boolean permanent) {
		return redisService.lock(key, permanent);
	}

	public static final boolean lock(String key, int seconds) {
		return redisService.lock(key, seconds);
	}
	
	public static final boolean isLocked(String key){
		return redisService.isLocked(key);
	}

	public static final Long unlock(String key) {
		return redisService.unlock(key);
	}

	protected RedisUtil() {
	}

}
