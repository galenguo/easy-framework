package com.efun.core.cache.redis;

import com.efun.core.config.Configuration;
import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RedisUtils
 * redis工具类
 *
 * @author Galen
 * @since 2016/7/7
 */
public class RedisUtils {

    /**
     * 只在键不存在时，才对键进行设置操作。
     */
    public static final String NX = "NX";

    /**
     * 只在键已经存在时，才对键进行设置操作。
     */
    public static final String XX = "XX";

    /**
     * 设置键的过期时间为 millisecond 毫秒。
     */
    public static final String PX = "PX";

    /**
     * 设置键的过期时间为 second 秒.
     */
    public static final String EX = "EX";

    private static final Logger logger = LogManager.getLogger(RedisUtils.class);

    private static volatile CommonRedisCommands instance = null;

    /**
     * 获取redis实例，开关控制。
     * @return
     */
    public static CommonRedisCommands getInstance() {
        if (instance == null) {
            synchronized(RedisUtils.class) {
                if (instance == null) {
                    String openSwitch = Configuration.getProperty("redisUtils.open.switch");
                    if (StringUtils.isBlank(openSwitch) || Boolean.parseBoolean(openSwitch)) {
                        instance = RedisClusterPoolManager.getInstance();
                    } else {
                        instance = new RedisEmptyCluster();
                    }
                }
            }
        }
        return instance;
    }

    
    public static String set(byte[] key, byte[] value) {
        return getInstance().set(key, value);
    }

    
    public static String set(byte[] key, byte[] value, byte[] nxxx) {
        return getInstance().set(key, value, nxxx);
    }

    
    public static String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        return getInstance().set(key, value, nxxx, expx, time);
    }

    
    public static byte[] get(byte[] key) {
        return getInstance().get(key);
    }

    
    public static Boolean exists(byte[] key) {
        return getInstance().exists(key);
    }

    
    public static Long persist(byte[] key) {
        return getInstance().persist(key);
    }

    
    public static String type(byte[] key) {
        return getInstance().type(key);
    }

    
    public static Long expire(byte[] key, int seconds) {
        return getInstance().expire(key, seconds);
    }

    
    public static Long pexpire(byte[] key, long milliseconds) {
        return getInstance().pexpire(key, milliseconds);
    }

    
    public static Long expireAt(byte[] key, long unixTime) {
        return getInstance().expireAt(key, unixTime);
    }

    
    public static Long pexpireAt(byte[] key, long millisecondsTimestamp) {
        return getInstance().pexpireAt(key, millisecondsTimestamp);
    }

    
    public static Long ttl(byte[] key) {
        return getInstance().ttl(key);
    }

    
    public static Boolean setbit(byte[] key, long offset, boolean value) {
        return getInstance().setbit(key, offset, value);
    }

    
    public static Boolean setbit(byte[] key, long offset, byte[] value) {
        return getInstance().setbit(key, offset, value);
    }

    
    public static Boolean getbit(byte[] key, long offset) {
        return getInstance().getbit(key, offset);
    }

    
    public static Long setrange(byte[] key, long offset, byte[] value) {
        return getInstance().setrange(key, offset, value);
    }

    
    public static byte[] getrange(byte[] key, long startOffset, long endOffset) {
        return getInstance().getrange(key, startOffset, endOffset);
    }

    
    public static byte[] getSet(byte[] key, byte[] value) {
        return getInstance().getSet(key, value);
    }

    
    public static Long setnx(byte[] key, byte[] value) {
        return getInstance().setnx(key, value);
    }

    
    public static String setex(byte[] key, int seconds, byte[] value) {
        return getInstance().setex(key, seconds, value);
    }

    
    public static Long decrBy(byte[] key, long integer) {
        return getInstance().decrBy(key, integer);
    }

    
    public static Long decr(byte[] key) {
        return getInstance().decr(key);
    }

    
    public static Long incrBy(byte[] key, long integer) {
        return getInstance().incrBy(key, integer);
    }

    
    public static Double incrByFloat(byte[] key, double value) {
        return getInstance().incrByFloat(key, value);
    }

    
    public static Long incr(byte[] key) {
        return getInstance().incr(key);
    }

    
    public static Long append(byte[] key, byte[] value) {
        return getInstance().append(key, value);
    }

    
    public static byte[] substr(byte[] key, int start, int end) {
        return getInstance().substr(key, start, end);
    }

    
    public static Long hset(byte[] key, byte[] field, byte[] value) {
        return getInstance().hset(key, field, value);
    }

    
    public static byte[] hget(byte[] key, byte[] field) {
        return getInstance().hget(key, field);
    }

    
    public static Long hsetnx(byte[] key, byte[] field, byte[] value) {
        return getInstance().hsetnx(key, field, value);
    }

    
    public static String hmset(byte[] key, Map<byte[], byte[]> hash) {
        return getInstance().hmset(key, hash);
    }

    
    public static List<byte[]> hmget(byte[] key, byte[]... fields) {
        return getInstance().hmget(key, fields);
    }

    
    public static Long hincrBy(byte[] key, byte[] field, long value) {
        return getInstance().hincrBy(key, field, value);
    }

    
    public static Double hincrByFloat(byte[] key, byte[] field, double value) {
        return getInstance().hincrByFloat(key, field, value);
    }

    
    public static Boolean hexists(byte[] key, byte[] field) {
        return getInstance().hexists(key, field);
    }

    
    public static Long hdel(byte[] key, byte[]... field) {
        return getInstance().hdel(key, field);
    }

    
    public static Long hlen(byte[] key) {
        return getInstance().hlen(key);
    }

    
    public static Set<byte[]> hkeys(byte[] key) {
        return getInstance().hkeys(key);
    }

    
    public static Collection<byte[]> hvals(byte[] key) {
        return getInstance().hvals(key);
    }

    
    public static Map<byte[], byte[]> hgetAll(byte[] key) {
        return getInstance().hgetAll(key);
    }

    
    public static Long rpush(byte[] key, byte[]... args) {
        return getInstance().rpush(key, args);
    }

    
    public static Long lpush(byte[] key, byte[]... args) {
        return getInstance().lpush(key, args);
    }

    
    public static Long llen(byte[] key) {
        return getInstance().llen(key);
    }

    
    public static List<byte[]> lrange(byte[] key, long start, long end) {
        return getInstance().lrange(key, start, end);
    }

    
    public static String ltrim(byte[] key, long start, long end) {
        return getInstance().ltrim(key, start, end);
    }

    
    public static byte[] lindex(byte[] key, long index) {
        return getInstance().lindex(key, index);
    }

    
    public static String lset(byte[] key, long index, byte[] value) {
        return getInstance().lset(key, index, value);
    }

    
    public static Long lrem(byte[] key, long count, byte[] value) {
        return getInstance().lrem(key, count, value);
    }

    
    public static byte[] lpop(byte[] key) {
        return getInstance().lpop(key);
    }

    
    public static byte[] rpop(byte[] key) {
        return getInstance().rpop(key);
    }

    
    public static Long sadd(byte[] key, byte[]... member) {
        return getInstance().sadd(key, member);
    }

    
    public static Set<byte[]> smembers(byte[] key) {
        return getInstance().smembers(key);
    }

    
    public static Long srem(byte[] key, byte[]... member) {
        return getInstance().srem(key, member);
    }

    
    public static byte[] spop(byte[] key) {
        return getInstance().spop(key);
    }

    
    public static Set<byte[]> spop(byte[] key, long count) {
        return getInstance().spop(key, count);
    }

    
    public static Long scard(byte[] key) {
        return getInstance().scard(key);
    }

    
    public static Boolean sismember(byte[] key, byte[] member) {
        return getInstance().sismember(key, member);
    }

    
    public static byte[] srandmember(byte[] key) {
        return getInstance().srandmember(key);
    }

    
    public static List<byte[]> srandmember(byte[] key, int count) {
        return getInstance().srandmember(key, count);
    }

    
    public static Long strlen(byte[] key) {
        return getInstance().strlen(key);
    }

    
    public static Long zadd(byte[] key, double score, byte[] member) {
        return getInstance().zadd(key, score, member);
    }

    
    public static Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
        return getInstance().zadd(key, score, member, params);
    }

    
    public static Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        return getInstance().zadd(key, scoreMembers);
    }

    
    public static Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
        return getInstance().zadd(key, scoreMembers, params);
    }

    
    public static Set<byte[]> zrange(byte[] key, long start, long end) {
        return getInstance().zrange(key, start, end);
    }

    
    public static Long zrem(byte[] key, byte[]... member) {
        return getInstance().zrem(key, member);
    }

    
    public static Double zincrby(byte[] key, double score, byte[] member) {
        return getInstance().zincrby(key, score, member);
    }

    
    public static Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params) {
        return getInstance().zincrby(key, score, member, params);
    }

    
    public static Long zrank(byte[] key, byte[] member) {
        return getInstance().zrank(key, member);
    }

    
    public static Long zrevrank(byte[] key, byte[] member) {
        return getInstance().zrevrank(key, member);
    }

    
    public static Set<byte[]> zrevrange(byte[] key, long start, long end) {
        return getInstance().zrevrange(key, start, end);
    }

    
    public static Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
        return getInstance().zrangeWithScores(key, start, end);
    }

    
    public static Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
        return getInstance().zrevrangeWithScores(key, start, end);
    }

    
    public static Long zcard(byte[] key) {
        return getInstance().zcard(key);
    }

    
    public static Double zscore(byte[] key, byte[] member) {
        return getInstance().zscore(key, member);
    }

    
    public static List<byte[]> sort(byte[] key) {
        return getInstance().sort(key);
    }

    
    public static List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        return getInstance().sort(key, sortingParameters);
    }

    
    public static Long zcount(byte[] key, double min, double max) {
        return getInstance().zcount(key, min, max);
    }

    
    public static Long zcount(byte[] key, byte[] min, byte[] max) {
        return getInstance().zcount(key, min, max);
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return getInstance().zrangeByScore(key, min, max);
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        return getInstance().zrangeByScore(key, min, max);
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return getInstance().zrevrangeByScore(key, max, min);
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        return getInstance().zrangeByScore(key, min, max, offset, count);
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
        return getInstance().zrevrangeByScore(key, max, min);
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return getInstance().zrangeByScore(key, min, max, offset, count);
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return getInstance().zrevrangeByScore(key, max, min, offset, count);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return getInstance().zrangeByScoreWithScores(key, min, max);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        return getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return getInstance().zrevrangeByScore(key, max, min, offset, count);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
        return getInstance().zrangeByScoreWithScores(key, min, max);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    
    public static Long zremrangeByRank(byte[] key, long start, long end) {
        return getInstance().zremrangeByRank(key, start, end);
    }

    
    public static Long zremrangeByScore(byte[] key, double start, double end) {
        return getInstance().zremrangeByScore(key, start, end);
    }

    
    public static Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
        return getInstance().zremrangeByScore(key, start, end);
    }

    
    public static Long zlexcount(byte[] key, byte[] min, byte[] max) {
        return getInstance().zlexcount(key, min, max);
    }

    
    public static Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        return getInstance().zrangeByLex(key, min, max);
    }

    
    public static Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return getInstance().zrangeByLex(key, min, max, offset, count);
    }

    
    public static Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        return getInstance().zrevrangeByLex(key, max, min);
    }

    
    public static Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return getInstance().zrevrangeByLex(key, max, min, offset, count);
    }

    
    public static Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        return getInstance().zremrangeByLex(key, min, max);
    }

    
    public static Long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value) {
        return getInstance().linsert(key, where, pivot, value);
    }

    
    public static Long lpushx(byte[] key, byte[]... arg) {
        return getInstance().lpushx(key, arg);
    }

    
    public static Long rpushx(byte[] key, byte[]... arg) {
        return getInstance().rpushx(key, arg);
    }

    
    public static List<byte[]> blpop(byte[] arg) {
        return getInstance().blpop(arg);
    }

    
    public static List<byte[]> brpop(byte[] arg) {
        return getInstance().brpop(arg);
    }

    
    public static Long del(byte[] key) {
        return getInstance().del(key);
    }

    
    public static byte[] echo(byte[] arg) {
        return getInstance().echo(arg);
    }

    
    public static Long move(byte[] key, int dbIndex) {
        return getInstance().move(key, dbIndex);
    }

    
    public static Long bitcount(byte[] key) {
        return getInstance().bitcount(key);
    }

    
    public static Long bitcount(byte[] key, long start, long end) {
        return getInstance().bitcount(key, start, end);
    }

    
    public static Long pfadd(byte[] key, byte[]... elements) {
        return getInstance().pfadd(key, elements);
    }

    
    public static long pfcount(byte[] key) {
        return getInstance().pfcount(key);
    }

    
    public static Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
        return getInstance().geoadd(key, longitude, latitude, member);
    }

    
    public static Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return getInstance().geoadd(key, memberCoordinateMap);
    }

    
    public static Double geodist(byte[] key, byte[] member1, byte[] member2) {
        return getInstance().geodist(key, member1, member2);
    }

    
    public static Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
        return getInstance().geodist(key, member1, member2, unit);
    }

    
    public static List<byte[]> geohash(byte[] key, byte[]... members) {
        return getInstance().geohash(key, members);
    }

    
    public static List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
        return getInstance().geopos(key, members);
    }

    
    public static List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
        return getInstance().georadius(key, longitude, latitude, radius, unit);
    }

    
    public static List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return getInstance().georadius(key, longitude, latitude, radius, unit, param);
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
        return getInstance().georadiusByMember(key, member, radius, unit);
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return getInstance().georadiusByMember(key, member, radius, unit, param);
    }

    
    public static ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
        return getInstance().hscan(key, cursor);
    }

    
    public static ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
        return getInstance().hscan(key, cursor, params);
    }

    
    public static ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
        return getInstance().sscan(key, cursor);
    }

    
    public static ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
        return getInstance().sscan(key, cursor, params);
    }

    
    public static ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
        return getInstance().zscan(key, cursor);
    }

    
    public static ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
        return getInstance().zscan(key, cursor, params);
    }

    
    public static String set(String key, String value) {
        return getInstance().set(key, value);
    }

    
    public static String set(String key, String value, String nxxx, String expx, long time) {
        return getInstance().set(key, value, nxxx, expx, time);
    }

    
    public static String set(String key, String value, String nxxx) {
        return getInstance().set(key, value, nxxx);
    }

    
    public static String get(String key) {
        return getInstance().get(key);
    }

    
    public static Boolean exists(String key) {
        return getInstance().exists(key);
    }

    
    public static Long persist(String key) {
        return getInstance().persist(key);
    }

    
    public static String type(String key) {
        return getInstance().type(key);
    }

    
    public static Long expire(String key, int seconds) {
        return getInstance().expire(key, seconds);
    }

    
    public static Long pexpire(String key, long milliseconds) {
        return getInstance().pexpire(key, milliseconds);
    }

    
    public static Long expireAt(String key, long unixTime) {
        return getInstance().expireAt(key, unixTime);
    }

    
    public static Long pexpireAt(String key, long millisecondsTimestamp) {
        return getInstance().pexpireAt(key, millisecondsTimestamp);
    }

    
    public static Long ttl(String key) {
        return getInstance().ttl(key);
    }

    
    public static Long pttl(String key) {
        return getInstance().pttl(key);
    }

    
    public static Boolean setbit(String key, long offset, boolean value) {
        return getInstance().setbit(key, offset, value);
    }

    
    public static Boolean setbit(String key, long offset, String value) {
        return getInstance().setbit(key, offset, value);
    }

    
    public static Boolean getbit(String key, long offset) {
        return getInstance().getbit(key, offset);
    }

    
    public static Long setrange(String key, long offset, String value) {
        return getInstance().setrange(key, offset, value);
    }

    
    public static String getrange(String key, long startOffset, long endOffset) {
        return getInstance().getrange(key, startOffset, endOffset);
    }

    
    public static String getSet(String key, String value) {
        return getInstance().getSet(key, value);
    }

    
    public static Long setnx(String key, String value) {
        return getInstance().setnx(key, value);
    }

    
    public static String setex(String key, int seconds, String value) {
        return getInstance().setex(key, seconds, value);
    }

    
    public static String psetex(String key, long milliseconds, String value) {
        return getInstance().psetex(key, milliseconds, value);
    }

    
    public static Long decrBy(String key, long integer) {
        return getInstance().decrBy(key, integer);
    }

    
    public static Long decr(String key) {
        return getInstance().decr(key);
    }

    
    public static Long incrBy(String key, long integer) {
        return getInstance().incrBy(key, integer);
    }

    
    public static Double incrByFloat(String key, double value) {
        return getInstance().incrByFloat(key, value);
    }

    
    public static Long incr(String key) {
        return getInstance().incr(key);
    }

    
    public static Long append(String key, String value) {
        return getInstance().append(key, value);
    }

    
    public static String substr(String key, int start, int end) {
        return getInstance().substr(key, start, end);
    }

    
    public static Long hset(String key, String field, String value) {
        return getInstance().hset(key, field, value);
    }

    
    public static String hget(String key, String field) {
        return getInstance().hget(key, field);
    }

    
    public static Long hsetnx(String key, String field, String value) {
        return getInstance().hsetnx(key, field, value);
    }

    
    public static String hmset(String key, Map<String, String> hash) {
        return getInstance().hmset(key, hash);
    }

    
    public static List<String> hmget(String key, String... fields) {
        return getInstance().hmget(key, fields);
    }

    
    public static Long hincrBy(String key, String field, long value) {
        return getInstance().hincrBy(key, field, value);
    }

    
    public static Double hincrByFloat(String key, String field, double value) {
        return getInstance().hincrByFloat(key, field, value);
    }

    
    public static Boolean hexists(String key, String field) {
        return getInstance().hexists(key, field);
    }

    
    public static Long hdel(String key, String... field) {
        return getInstance().hdel(key, field);
    }

    
    public static Long hlen(String key) {
        return getInstance().hlen(key);
    }

    
    public static Set<String> hkeys(String key) {
        return getInstance().hkeys(key);
    }

    
    public static List<String> hvals(String key) {
        return getInstance().hvals(key);
    }

    
    public static Map<String, String> hgetAll(String key) {
        return getInstance().hgetAll(key);
    }

    
    public static Long rpush(String key, String... string) {
        return getInstance().rpush(key, string);
    }

    
    public static Long lpush(String key, String... string) {
        return getInstance().lpush(key, string);
    }

    
    public static Long llen(String key) {
        return getInstance().llen(key);
    }

    
    public static List<String> lrange(String key, long start, long end) {
        return getInstance().lrange(key, start, end);
    }

    
    public static String ltrim(String key, long start, long end) {
        return getInstance().ltrim(key, start, end);
    }

    
    public static String lindex(String key, long index) {
        return getInstance().lindex(key, index);
    }

    
    public static String lset(String key, long index, String value) {
        return getInstance().lset(key, index, value);
    }

    
    public static Long lrem(String key, long count, String value) {
        return getInstance().lrem(key, count, value);
    }

    
    public static String lpop(String key) {
        return getInstance().lpop(key);
    }

    
    public static String rpop(String key) {
        return getInstance().rpop(key);
    }

    
    public static Long sadd(String key, String... member) {
        return getInstance().sadd(key, member);
    }

    
    public static Set<String> smembers(String key) {
        return getInstance().smembers(key);
    }

    
    public static Long srem(String key, String... member) {
        return getInstance().srem(key, member);
    }

    
    public static String spop(String key) {
        return getInstance().spop(key);
    }

    
    public static Set<String> spop(String key, long count) {
        return getInstance().spop(key, count);
    }

    
    public static Long scard(String key) {
        return getInstance().scard(key);
    }

    
    public static Boolean sismember(String key, String member) {
        return getInstance().sismember(key, member);
    }

    
    public static String srandmember(String key) {
        return getInstance().srandmember(key);
    }

    
    public static List<String> srandmember(String key, int count) {
        return getInstance().srandmember(key, count);
    }

    
    public static Long strlen(String key) {
        return getInstance().strlen(key);
    }

    
    public static Long zadd(String key, double score, String member) {
        return getInstance().zadd(key, score, member);
    }

    
    public static Long zadd(String key, double score, String member, ZAddParams params) {
        return getInstance().zadd(key, score, member, params);
    }

    
    public static Long zadd(String key, Map<String, Double> scoreMembers) {
        return getInstance().zadd(key, scoreMembers);
    }

    
    public static Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return getInstance().zadd(key, scoreMembers, params);
    }

    
    public static Set<String> zrange(String key, long start, long end) {
        return getInstance().zrange(key, start, end);
    }

    
    public static Long zrem(String key, String... member) {
        return getInstance().zrem(key, member);
    }

    
    public static Double zincrby(String key, double score, String member) {
        return getInstance().zincrby(key, score, member);
    }

    
    public static Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return getInstance().zincrby(key, score, member, params);
    }

    
    public static Long zrank(String key, String member) {
        return getInstance().zrank(key, member);
    }

    
    public static Long zrevrank(String key, String member) {
        return getInstance().zrevrank(key, member);
    }

    
    public static Set<String> zrevrange(String key, long start, long end) {
        return getInstance().zrevrange(key, start, end);
    }

    
    public static Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return getInstance().zrangeWithScores(key, start, end);
    }

    
    public static Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return getInstance().zrevrangeWithScores(key, start, end);
    }

    
    public static Long zcard(String key) {
        return getInstance().zcard(key);
    }

    
    public static Double zscore(String key, String member) {
        return getInstance().zscore(key, member);
    }

    
    public static List<String> sort(String key) {
        return getInstance().sort(key);
    }

    
    public static List<String> sort(String key, SortingParams sortingParameters) {
        return getInstance().sort(key, sortingParameters);
    }

    
    public static Long zcount(String key, double min, double max) {
        return getInstance().zcount(key, min, max);
    }

    
    public static Long zcount(String key, String min, String max) {
        return getInstance().zcount(key, min, max);
    }

    
    public static Set<String> zrangeByScore(String key, double min, double max) {
        return getInstance().zrangeByScore(key, min, max);
    }

    
    public static Set<String> zrangeByScore(String key, String min, String max) {
        return getInstance().zrangeByScore(key, min, max);
    }

    
    public static Set<String> zrevrangeByScore(String key, double max, double min) {
        return getInstance().zrevrangeByScore(key, max, min);
    }

    
    public static Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return getInstance().zrangeByScore(key, min, max, offset, count);
    }

    
    public static Set<String> zrevrangeByScore(String key, String max, String min) {
        return getInstance().zrevrangeByScore(key, max, min);
    }

    
    public static Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return getInstance().zrangeByScore(key, min, max, offset, count);
    }

    
    public static Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return getInstance().zrevrangeByScore(key, max, min, offset, count);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return getInstance().zrangeByScoreWithScores(key, min, max);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    
    public static Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return getInstance().zrevrangeByScore(key, max, min, offset, count);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return getInstance().zrangeByScoreWithScores(key, min, max);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min);
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return getInstance().zrangeByScoreWithScores(key, min, max, offset, count);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return getInstance().zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    
    public static Long zremrangeByRank(String key, long start, long end) {
        return getInstance().zremrangeByRank(key, start, end);
    }

    
    public static Long zremrangeByScore(String key, double start, double end) {
        return getInstance().zremrangeByScore(key, start, end);
    }

    
    public static Long zremrangeByScore(String key, String start, String end) {
        return getInstance().zremrangeByScore(key, start, end);
    }

    
    public static Long zlexcount(String key, String min, String max) {
        return getInstance().zlexcount(key, min, max);
    }

    
    public static Set<String> zrangeByLex(String key, String min, String max) {
        return getInstance().zrangeByLex(key, min, max);
    }

    
    public static Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return getInstance().zrangeByLex(key, min, max, offset, count);
    }

    
    public static Set<String> zrevrangeByLex(String key, String max, String min) {
        return getInstance().zrevrangeByLex(key, max, min);
    }

    
    public static Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return getInstance().zrevrangeByLex(key, max, min, offset, count);
    }

    
    public static Long zremrangeByLex(String key, String min, String max) {
        return getInstance().zremrangeByLex(key, min, max);
    }

    
    public static Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return getInstance().linsert(key, where, pivot, value);
    }

    
    public static Long lpushx(String key, String... string) {
        return getInstance().lpushx(key, string);
    }

    
    public static Long rpushx(String key, String... string) {
        return getInstance().rpush(key, string);
    }

    
    public static List<String> blpop(String arg) {
        return getInstance().blpop(arg);
    }

    
    public static List<String> blpop(int timeout, String key) {
        return getInstance().blpop(timeout, key);
    }

    
    public static List<String> brpop(String arg) {
        return getInstance().brpop(arg);
    }

    
    public static List<String> brpop(int timeout, String key) {
        return getInstance().brpop(timeout, key);
    }

    
    public static Long del(String key) {
        return getInstance().del(key);
    }

    
    public static String echo(String string) {
        return getInstance().echo(string);
    }

    
    public static Long move(String key, int dbIndex) {
        return getInstance().move(key, dbIndex);
    }

    
    public static Long bitcount(String key) {
        return getInstance().bitcount(key);
    }

    
    public static Long bitcount(String key, long start, long end) {
        return getInstance().bitcount(key, start, end);
    }

    
    public static Long bitpos(String key, boolean value) {
        return getInstance().bitpos(key, value);
    }

    
    public static Long bitpos(String key, boolean value, BitPosParams params) {
        return getInstance().bitpos(key, value, params);
    }

    
    public static ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return getInstance().hscan(key, cursor);
    }

    
    public static ScanResult<String> sscan(String key, int cursor) {
        return getInstance().sscan(key, cursor);
    }

    
    public static ScanResult<Tuple> zscan(String key, int cursor) {
        return getInstance().zscan(key, cursor);
    }

    
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return getInstance().hscan(key, cursor);
    }

    
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return getInstance().hscan(key, cursor, params);
    }

    
    public static ScanResult<String> sscan(String key, String cursor) {
        return getInstance().sscan(key, cursor);
    }

    
    public static ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return getInstance().sscan(key, cursor, params);
    }

    
    public static ScanResult<Tuple> zscan(String key, String cursor) {
        return getInstance().zscan(key, cursor);
    }

    
    public static ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return getInstance().zscan(key, cursor, params);
    }

    
    public static Long pfadd(String key, String... elements) {
        return getInstance().pfadd(key, elements);
    }

    
    public static long pfcount(String key) {
        return getInstance().pfcount(key);
    }

    
    public static Long geoadd(String key, double longitude, double latitude, String member) {
        return getInstance().geoadd(key, longitude, latitude, member);
    }

    
    public static Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return getInstance().geoadd(key, memberCoordinateMap);
    }

    
    public static Double geodist(String key, String member1, String member2) {
        return getInstance().geodist(key, member1, member2);
    }

    
    public static Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return getInstance().geodist(key, member1, member2, unit);
    }

    
    public static List<String> geohash(String key, String... members) {
        return getInstance().geohash(key, members);
    }

    
    public static List<GeoCoordinate> geopos(String key, String... members) {
        return getInstance().geopos(key, members);
    }

    
    public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return getInstance().georadius(key, longitude, latitude, radius, unit);
    }

    
    public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return getInstance().georadius(key, longitude, latitude, radius, unit, param);
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return getInstance().georadiusByMember(key, member, radius, unit);
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return getInstance().georadiusByMember(key, member, radius, unit, param);
    }


    /*---- 值得序列化与反序列化 ---*/
    public static <T extends Serializable> byte[] serialize(T object) {
        byte[] result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T extends Serializable> T deserialize(byte[] bytes) {
        T result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T extends Serializable> Map<byte[], byte[]> serialize(Map<String, T> map) {
        Map<byte[], byte[]> result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T extends Serializable> Map<String, T>  deserialize(Map<byte[], byte[]> map) {
        Map<String, T> result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T extends Serializable> Set<byte[]> serialize(Set<T> set) {
        Set<byte[]> result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T extends Serializable> Set<T>  deserialize(Set<byte[]> set) {
        Set<T> result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T extends Serializable> List<byte[]> serialize(List<T> list) {
        List<byte[]> result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static <T extends Serializable> List<T>  deserialize(List<byte[]> list) {
        List<T> result = null;
        try {

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

}
