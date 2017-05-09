package com.efun.core.cache.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RedisClientCluster
 * 客户端集群（客户端一致性哈希实现）
 *
 * @author Galen
 * @since 2016/8/2
 */
public class RedisClientCluster implements CommonRedisCommands {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private ShardedJedisSentinelPool pool;

    public RedisClientCluster(ShardedJedisSentinelPool shardedJedisSentinelPool) {
        this.pool = shardedJedisSentinelPool;
    }

    public ShardedJedis getJedis() {
        return this.pool.getResource();
    }

    public void destroy() {
        this.pool.destroy();
    }

    private <T> T execute(RedisCallBack<T> redisCallBack) {
        ShardedJedis shardedJedis = null;
        T result = null;
        try {
            shardedJedis = getJedis();
            result = redisCallBack.callBack(shardedJedis);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shardedJedis.close();
        }
        return result;
    }

    private interface RedisCallBack<T> {
        T callBack(ShardedJedis shardedJedis);
    }

    @Override
    public String set(byte[] key, byte[] value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value);
            }
        });
    }

    @Override
    public String set(byte[] key, byte[] value, byte[] nxxx) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value, nxxx);
            }
        });
    }

    @Override
    public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value, nxxx);
            }
        });
    }

    @Override
    public byte[] get(byte[] key) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });
    }

    @Override
    public Boolean exists(byte[] key) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.exists(key);
            }
        });
    }

    @Override
    public Long persist(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.persist(key);
            }
        });
    }

    @Override
    public String type(byte[] key) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.type(key);
            }
        });
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, seconds);
            }
        });
    }

    @Override
    public Long pexpire(byte[] key, long milliseconds) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pexpire(key, milliseconds);
            }
        });
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.expireAt(key, unixTime);
            }
        });
    }

    @Override
    public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pexpireAt(key, millisecondsTimestamp);
            }
        });
    }

    @Override
    public Long ttl(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.ttl(key);
            }
        });
    }

    @Override
    public Boolean setbit(byte[] key, long offset, boolean value) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setbit(key, offset, value);
            }
        });
    }

    @Override
    public Boolean setbit(byte[] key, long offset, byte[] value) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setbit(key, offset, value);
            }
        });
    }

    @Override
    public Boolean getbit(byte[] key, long offset) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.getbit(key, offset);
            }
        });
    }

    @Override
    public Long setrange(byte[] key, long offset, byte[] value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setrange(key, offset, value);
            }
        });
    }

    @Override
    public byte[] getrange(byte[] key, long startOffset, long endOffset) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.getrange(key, startOffset, endOffset);
            }
        });
    }

    @Override
    public byte[] getSet(byte[] key, byte[] value) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.getSet(key, value);
            }
        });
    }

    @Override
    public Long setnx(byte[] key, byte[] value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setnx(key, value);
            }
        });
    }

    @Override
    public String setex(byte[] key, int seconds, byte[] value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setex(key, seconds, value);
            }
        });
    }

    @Override
    public Long decrBy(byte[] key, long integer) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.decrBy(key, integer);
            }
        });
    }

    @Override
    public Long decr(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.decr(key);
            }
        });
    }

    @Override
    public Long incrBy(byte[] key, long integer) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.incrBy(key, integer);
            }
        });
    }

    @Override
    public Double incrByFloat(byte[] key, double value) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.incrByFloat(key, value);
            }
        });
    }

    @Override
    public Long incr(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.incr(key);
            }
        });
    }

    @Override
    public Long append(byte[] key, byte[] value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.append(key, value);
            }
        });
    }

    @Override
    public byte[] substr(byte[] key, int start, int end) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.substr(key, start, end);
            }
        });
    }

    @Override
    public Long hset(byte[] key, byte[] field, byte[] value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hset(key, field, value);
            }
        });
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hget(key, field);
            }
        });
    }

    @Override
    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hsetnx(key, field, value);
            }
        });
    }

    @Override
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hmset(key, hash);
            }
        });
    }

    @Override
    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hmget(key, fields);
            }
        });
    }

    @Override
    public Long hincrBy(byte[] key, byte[] field, long value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hincrBy(key, field, value);
            }
        });
    }

    @Override
    public Double hincrByFloat(byte[] key, byte[] field, double value) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hincrByFloat(key, field, value);
            }
        });
    }

    @Override
    public Boolean hexists(byte[] key, byte[] field) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hexists(key, field);
            }
        });
    }

    @Override
    public Long hdel(byte[] key, byte[]... field) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hdel(key, field);
            }
        });
    }

    @Override
    public Long hlen(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hlen(key);
            }
        });
    }

    @Override
    public Set<byte[]> hkeys(byte[] key) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hkeys(key);
            }
        });
    }

    @Override
    public Collection<byte[]> hvals(byte[] key) {
        return this.execute(new RedisCallBack<Collection<byte[]>>() {
            @Override
            public Collection<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hvals(key);
            }
        });
    }

    @Override
    public Map<byte[], byte[]> hgetAll(byte[] key) {
        return this.execute(new RedisCallBack<Map<byte[], byte[]>>() {
            @Override
            public Map<byte[], byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hgetAll(key);
            }
        });
    }

    @Override
    public Long rpush(byte[] key, byte[]... args) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.rpush(key, args);
            }
        });
    }

    @Override
    public Long lpush(byte[] key, byte[]... args) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lpush(key, args);
            }
        });
    }

    @Override
    public Long llen(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.llen(key);
            }
        });
    }

    @Override
    public List<byte[]> lrange(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lrange(key, start, end);
            }
        });
    }

    @Override
    public String ltrim(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.ltrim(key, start, end);
            }
        });
    }

    @Override
    public byte[] lindex(byte[] key, long index) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lindex(key, index);
            }
        });
    }

    @Override
    public String lset(byte[] key, long index, byte[] value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lset(key, index, value);
            }
        });
    }

    @Override
    public Long lrem(byte[] key, long count, byte[] value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lrem(key, count, value);
            }
        });
    }

    @Override
    public byte[] lpop(byte[] key) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lpop(key);
            }
        });
    }

    @Override
    public byte[] rpop(byte[] key) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.rpop(key);
            }
        });
    }

    @Override
    public Long sadd(byte[] key, byte[]... member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sadd(key, member);
            }
        });
    }

    @Override
    public Set<byte[]> smembers(byte[] key) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.smembers(key);
            }
        });
    }

    @Override
    public Long srem(byte[] key, byte[]... member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.srem(key, member);
            }
        });
    }

    @Override
    public byte[] spop(byte[] key) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.spop(key);
            }
        });
    }

    @Override
    public Set<byte[]> spop(byte[] key, long count) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.spop(key, count);
            }
        });
    }

    @Override
    public Long scard(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.scard(key);
            }
        });
    }

    @Override
    public Boolean sismember(byte[] key, byte[] member) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sismember(key, member);
            }
        });
    }

    @Override
    public byte[] srandmember(byte[] key) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.srandmember(key);
            }
        });
    }

    @Override
    public List<byte[]> srandmember(byte[] key, int count) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.srandmember(key, count);
            }
        });
    }

    @Override
    public Long strlen(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.strlen(key);
            }
        });
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, score, member);
            }
        });
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, score, member, params);
            }
        });
    }

    @Override
    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, scoreMembers);
            }
        });
    }

    @Override
    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, scoreMembers, params);
            }
        });
    }

    @Override
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrange(key, start, end);
            }
        });
    }

    @Override
    public Long zrem(byte[] key, byte[]... member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrem(key, member);
            }
        });
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zincrby(key, score, member);
            }
        });
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zincrby(key, score, member, params);
            }
        });
    }

    @Override
    public Long zrank(byte[] key, byte[] member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrank(key, member);
            }
        });
    }

    @Override
    public Long zrevrank(byte[] key, byte[] member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrank(key, member);
            }
        });
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrange(key, start, end);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeWithScores(key, start, end);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeWithScores(key, start, end);
            }
        });
    }

    @Override
    public Long zcard(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zcard(key);
            }
        });
    }

    @Override
    public Double zscore(byte[] key, byte[] member) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zscore(key, member);
            }
        });
    }

    @Override
    public List<byte[]> sort(byte[] key) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sort(key);
            }
        });
    }

    @Override
    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sort(key, sortingParameters);
            }
        });
    }

    @Override
    public Long zcount(byte[] key, double min, double max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zcount(key, min, max);
            }
        });
    }

    @Override
    public Long zcount(byte[] key, byte[] min, byte[] max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zcount(key, min, max);
            }
        });
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max);
            }
        });
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max);
            }
        });
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min);
            }
        });
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min);
            }
        });
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Long zremrangeByRank(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByRank(key, start, end);
            }
        });
    }

    @Override
    public Long zremrangeByScore(byte[] key, double start, double end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByScore(key, start, end);
            }
        });
    }

    @Override
    public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByScore(key, start, end);
            }
        });
    }

    @Override
    public Long zlexcount(byte[] key, byte[] min, byte[] max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zlexcount(key, min, max);
            }
        });
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByLex(key, min, max);
            }
        });
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByLex(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByLex(key, max, min);
            }
        });
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<byte[]>>() {
            @Override
            public Set<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByLex(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByLex(key, min, max);
            }
        });
    }

    @Override
    public Long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.linsert(key, where, pivot, value);
            }
        });
    }

    @Override
    public Long lpushx(byte[] key, byte[]... arg) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lpush(key, arg);
            }
        });
    }

    @Override
    public Long rpushx(byte[] key, byte[]... arg) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.rpushx(key, arg);
            }
        });
    }

    @Override
    public List<byte[]> blpop(byte[] arg) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.blpop(arg);
            }
        });
    }

    @Override
    public List<byte[]> brpop(byte[] arg) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.brpop(arg);
            }
        });
    }

    @Override
    public Long del(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

    @Override
    public byte[] echo(byte[] arg) {
        return this.execute(new RedisCallBack<byte[]>() {
            @Override
            public byte[] callBack(ShardedJedis shardedJedis) {
                return shardedJedis.echo(arg);
            }
        });
    }

    @Override
    public Long move(byte[] key, int dbIndex) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.move(key, dbIndex);
            }
        });
    }

    @Override
    public Long bitcount(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.bitcount(key);
            }
        });
    }

    @Override
    public Long bitcount(byte[] key, long start, long end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.bitcount(key, start, end);
            }
        });
    }

    @Override
    public Long pfadd(byte[] key, byte[]... elements) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pfadd(key, elements);
            }
        });
    }

    @Override
    public long pfcount(byte[] key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pfcount(key);
            }
        });
    }

    @Override
    public Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geoadd(key, longitude, latitude, member);
            }
        });
    }

    @Override
    public Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geoadd(key, memberCoordinateMap);
            }
        });
    }

    @Override
    public Double geodist(byte[] key, byte[] member1, byte[] member2) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geodist(key, member1, member2);
            }
        });
    }

    @Override
    public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geodist(key, member1, member2, unit);
            }
        });
    }

    @Override
    public List<byte[]> geohash(byte[] key, byte[]... members) {
        return this.execute(new RedisCallBack<List<byte[]>>() {
            @Override
            public List<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geohash(key, members);
            }
        });
    }

    @Override
    public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
        return this.execute(new RedisCallBack<List<GeoCoordinate>>() {
            @Override
            public List<GeoCoordinate> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geopos(key, members);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadius(key, longitude, latitude, radius, unit);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadius(key, longitude, latitude, radius, unit, param);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadiusByMember(key, member, radius, unit);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadiusByMember(key, member, radius, unit, param);
            }
        });
    }

    @Override
    public ScanResult<byte[]> scan(byte[] bytes, ScanParams scanParams) {
        return null;
    }

    @Override
    public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
        return this.execute(new RedisCallBack<ScanResult<Map.Entry<byte[], byte[]>>>() {
            @Override
            public ScanResult<Map.Entry<byte[], byte[]>> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
        return this.execute(new RedisCallBack<ScanResult<Map.Entry<byte[], byte[]>>>() {
            @Override
            public ScanResult<Map.Entry<byte[], byte[]>> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hscan(key, cursor, params);
            }
        });
    }

    @Override
    public ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
        return this.execute(new RedisCallBack<ScanResult<byte[]>>() {
            @Override
            public ScanResult<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
        return this.execute(new RedisCallBack<ScanResult<byte[]>>() {
            @Override
            public ScanResult<byte[]> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sscan(key, cursor, params);
            }
        });
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
        return this.execute(new RedisCallBack<ScanResult<Tuple>>() {
            @Override
            public ScanResult<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
        return this.execute(new RedisCallBack<ScanResult<Tuple>>() {
            @Override
            public ScanResult<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zscan(key, cursor, params);
            }
        });
    }

    @Override
    public List<byte[]> bitfield(byte[] bytes, byte[]... bytes1) {
        return null;
    }

    @Override
    public String set(String key, String value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value);
            }
        });
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value, nxxx, expx, time);
            }
        });
    }

    @Override
    public String set(String key, String value, String nxxx) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.set(key, value, nxxx);
            }
        });
    }

    @Override
    public String get(String key) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });
    }

    @Override
    public Boolean exists(String key) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.exists(key);
            }
        });
    }

    @Override
    public Long persist(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.persist(key);
            }
        });
    }

    @Override
    public String type(String key) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.type(key);
            }
        });
    }

    @Override
    public Long expire(String key, int seconds) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.expire(key, seconds);
            }
        });
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pexpire(key, milliseconds);
            }
        });
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.expireAt(key, unixTime);
            }
        });
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pexpireAt(key, millisecondsTimestamp);
            }
        });
    }

    @Override
    public Long ttl(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.ttl(key);
            }
        });
    }

    @Override
    public Long pttl(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pttl(key);
            }
        });
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setbit(key, offset, value);
            }
        });
    }

    @Override
    public Boolean setbit(String key, long offset, String value) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setbit(key, offset, value);
            }
        });
    }

    @Override
    public Boolean getbit(String key, long offset) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.getbit(key, offset);
            }
        });
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setrange(key, offset, value);
            }
        });
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.getrange(key, startOffset, endOffset);
            }
        });
    }

    @Override
    public String getSet(String key, String value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.getSet(key, value);
            }
        });
    }

    @Override
    public Long setnx(String key, String value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setnx(key, value);
            }
        });
    }

    @Override
    public String setex(String key, int seconds, String value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.setex(key, seconds, value);
            }
        });
    }

    @Override
    public String psetex(String key, long milliseconds, String value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.psetex(key, milliseconds, value);
            }
        });
    }

    @Override
    public Long decrBy(String key, long integer) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.decrBy(key, integer);
            }
        });
    }

    @Override
    public Long decr(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.decr(key);
            }
        });
    }

    @Override
    public Long incrBy(String key, long integer) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.incrBy(key, integer);
            }
        });
    }

    @Override
    public Double incrByFloat(String key, double value) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.incrByFloat(key, value);
            }
        });
    }

    @Override
    public Long incr(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.incr(key);
            }
        });
    }

    @Override
    public Long append(String key, String value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.append(key, value);
            }
        });
    }

    @Override
    public String substr(String key, int start, int end) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.substr(key, start, end);
            }
        });
    }

    @Override
    public Long hset(String key, String field, String value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hset(key, field, value);
            }
        });
    }

    @Override
    public String hget(String key, String field) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hget(key, field);
            }
        });
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hsetnx(key, field, value);
            }
        });
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hmset(key, hash);
            }
        });
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hmget(key, fields);
            }
        });
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hincrBy(key, field, value);
            }
        });
    }

    @Override
    public Double hincrByFloat(String key, String field, double value) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hincrByFloat(key, field, value);
            }
        });
    }

    @Override
    public Boolean hexists(String key, String field) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hexists(key, field);
            }
        });
    }

    @Override
    public Long hdel(String key, String... field) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hdel(key, field);
            }
        });
    }

    @Override
    public Long hlen(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hlen(key);
            }
        });
    }

    @Override
    public Set<String> hkeys(String key) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hkeys(key);
            }
        });
    }

    @Override
    public List<String> hvals(String key) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hvals(key);
            }
        });
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return this.execute(new RedisCallBack<Map<String, String>>() {
            @Override
            public Map<String, String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hgetAll(key);
            }
        });
    }

    @Override
    public Long rpush(String key, String... string) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.rpush(key, string);
            }
        });
    }

    @Override
    public Long lpush(String key, String... string) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lpush(key, string);
            }
        });
    }

    @Override
    public Long llen(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.llen(key);
            }
        });
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lrange(key, start, end);
            }
        });
    }

    @Override
    public String ltrim(String key, long start, long end) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.ltrim(key, start, end);
            }
        });
    }

    @Override
    public String lindex(String key, long index) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lindex(key, index);
            }
        });
    }

    @Override
    public String lset(String key, long index, String value) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lset(key, index, value);
            }
        });
    }

    @Override
    public Long lrem(String key, long count, String value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lrem(key, count, value);
            }
        });
    }

    @Override
    public String lpop(String key) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lpop(key);
            }
        });
    }

    @Override
    public String rpop(String key) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.rpop(key);
            }
        });
    }

    @Override
    public Long sadd(String key, String... member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sadd(key, member);
            }
        });
    }

    @Override
    public Set<String> smembers(String key) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.smembers(key);
            }
        });
    }

    @Override
    public Long srem(String key, String... member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.srem(key, member);
            }
        });
    }

    @Override
    public String spop(String key) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.spop(key);
            }
        });
    }

    @Override
    public Set<String> spop(String key, long count) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.spop(key, count);
            }
        });
    }

    @Override
    public Long scard(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.scard(key);
            }
        });
    }

    @Override
    public Boolean sismember(String key, String member) {
        return this.execute(new RedisCallBack<Boolean>() {
            @Override
            public Boolean callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sismember(key, member);
            }
        });
    }

    @Override
    public String srandmember(String key) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.srandmember(key);
            }
        });
    }

    @Override
    public List<String> srandmember(String key, int count) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.srandmember(key, count);
            }
        });
    }

    @Override
    public Long strlen(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.strlen(key);
            }
        });
    }

    @Override
    public Long zadd(String key, double score, String member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, score, member);
            }
        });
    }

    @Override
    public Long zadd(String key, double score, String member, ZAddParams params) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, score, member, params);
            }
        });
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, scoreMembers);
            }
        });
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zadd(key, scoreMembers, params);
            }
        });
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrange(key, start, end);
            }
        });
    }

    @Override
    public Long zrem(String key, String... member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrem(key, member);
            }
        });
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zincrby(key, score, member);
            }
        });
    }

    @Override
    public Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zincrby(key, score, member, params);
            }
        });
    }

    @Override
    public Long zrank(String key, String member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrank(key, member);
            }
        });
    }

    @Override
    public Long zrevrank(String key, String member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrank(key, member);
            }
        });
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrange(key, start, end);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeWithScores(key, start, end);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeWithScores(key, start, end);
            }
        });
    }

    @Override
    public Long zcard(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zcard(key);
            }
        });
    }

    @Override
    public Double zscore(String key, String member) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zscore(key, member);
            }
        });
    }

    @Override
    public List<String> sort(String key) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sort(key);
            }
        });
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sort(key, sortingParameters);
            }
        });
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zcount(key, min, max);
            }
        });
    }

    @Override
    public Long zcount(String key, String min, String max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zcount(key, min, max);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min);
            }
        });
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScore(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScore(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min);
            }
        });
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByScoreWithScores(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<Tuple>>() {
            @Override
            public Set<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByScoreWithScores(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByRank(key, start, end);
            }
        });
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByScore(key, start, end);
            }
        });
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByScore(key, start, end);
            }
        });
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zlexcount(key, min, max);
            }
        });
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByLex(key, min, max);
            }
        });
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrangeByLex(key, min, max, offset, count);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByLex(key, max, min);
            }
        });
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return this.execute(new RedisCallBack<Set<String>>() {
            @Override
            public Set<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zrevrangeByLex(key, max, min, offset, count);
            }
        });
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zremrangeByLex(key, min, max);
            }
        });
    }

    @Override
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.linsert(key, where, pivot, value);
            }
        });
    }

    @Override
    public Long lpushx(String key, String... string) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.lpushx(key, string);
            }
        });
    }

    @Override
    public Long rpushx(String key, String... string) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.rpushx(key, string);
            }
        });
    }

    @Override
    public List<String> blpop(String arg) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.blpop(arg);
            }
        });
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.blpop(timeout, key);
            }
        });
    }

    @Override
    public List<String> brpop(String arg) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.brpop(arg);
            }
        });
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.brpop(timeout, key);
            }
        });
    }

    @Override
    public Long del(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

    @Override
    public String echo(String string) {
        return this.execute(new RedisCallBack<String>() {
            @Override
            public String callBack(ShardedJedis shardedJedis) {
                return shardedJedis.echo(string);
            }
        });
    }

    @Override
    public Long move(String key, int dbIndex) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.move(key, dbIndex);
            }
        });
    }

    @Override
    public Long bitcount(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.bitcount(key);
            }
        });
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.bitcount(key, start, end);
            }
        });
    }

    @Override
    public Long bitpos(String key, boolean value) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.bitpos(key, value);
            }
        });
    }

    @Override
    public Long bitpos(String key, boolean value, BitPosParams params) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.bitpos(key, value, params);
            }
        });
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return this.execute(new RedisCallBack<ScanResult<Map.Entry<String, String>>>() {
            @Override
            public ScanResult<Map.Entry<String, String>> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<String> sscan(String key, int cursor) {
        return this.execute(new RedisCallBack<ScanResult<String>>() {
            @Override
            public ScanResult<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<Tuple> zscan(String key, int cursor) {
        return this.execute(new RedisCallBack<ScanResult<Tuple>>() {
            @Override
            public ScanResult<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return this.execute(new RedisCallBack<ScanResult<Map.Entry<String, String>>>() {
            @Override
            public ScanResult<Map.Entry<String, String>> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return this.execute(new RedisCallBack<ScanResult<Map.Entry<String, String>>>() {
            @Override
            public ScanResult<Map.Entry<String, String>> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.hscan(key, cursor, params);
            }
        });
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        return this.execute(new RedisCallBack<ScanResult<String>>() {
            @Override
            public ScanResult<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return this.execute(new RedisCallBack<ScanResult<String>>() {
            @Override
            public ScanResult<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.sscan(key, cursor, params);
            }
        });
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        return this.execute(new RedisCallBack<ScanResult<Tuple>>() {
            @Override
            public ScanResult<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zscan(key, cursor);
            }
        });
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return this.execute(new RedisCallBack<ScanResult<Tuple>>() {
            @Override
            public ScanResult<Tuple> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.zscan(key, cursor, params);
            }
        });
    }

    @Override
    public Long pfadd(String key, String... elements) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pfadd(key, elements);
            }
        });
    }

    @Override
    public long pfcount(String key) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.pfcount(key);
            }
        });
    }

    @Override
    public Long geoadd(String key, double longitude, double latitude, String member) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geoadd(key, longitude, latitude, member);
            }
        });
    }

    @Override
    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return this.execute(new RedisCallBack<Long>() {
            @Override
            public Long callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geoadd(key, memberCoordinateMap);
            }
        });
    }

    @Override
    public Double geodist(String key, String member1, String member2) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geodist(key, member1, member2);
            }
        });
    }

    @Override
    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return this.execute(new RedisCallBack<Double>() {
            @Override
            public Double callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geodist(key, member1, member2, unit);
            }
        });
    }

    @Override
    public List<String> geohash(String key, String... members) {
        return this.execute(new RedisCallBack<List<String>>() {
            @Override
            public List<String> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geohash(key, members);
            }
        });
    }

    @Override
    public List<GeoCoordinate> geopos(String key, String... members) {
        return this.execute(new RedisCallBack<List<GeoCoordinate>>() {
            @Override
            public List<GeoCoordinate> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.geopos(key, members);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadius(key, longitude, latitude, radius, unit);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadius(key, longitude, latitude, radius, unit, param);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadiusByMember(key, member, radius, unit);
            }
        });
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return this.execute(new RedisCallBack<List<GeoRadiusResponse>>() {
            @Override
            public List<GeoRadiusResponse> callBack(ShardedJedis shardedJedis) {
                return shardedJedis.georadiusByMember(key, member, radius, unit, param);
            }
        });
    }

    @Override
    public List<Long> bitfield(String s, String... strings) {
        return null;
    }
}
