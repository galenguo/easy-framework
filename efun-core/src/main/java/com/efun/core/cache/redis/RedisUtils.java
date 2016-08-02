package com.efun.core.cache.redis;

import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

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

    
    public static String set(byte[] key, byte[] value) {
        return null;
    }

    
    public static String set(byte[] key, byte[] value, byte[] nxxx) {
        return null;
    }

    
    public static String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        return null;
    }

    
    public static byte[] get(byte[] key) {
        return new byte[0];
    }

    
    public static Boolean exists(byte[] key) {
        return null;
    }

    
    public static Long persist(byte[] key) {
        return null;
    }

    
    public static String type(byte[] key) {
        return null;
    }

    
    public static Long expire(byte[] key, int seconds) {
        return null;
    }

    
    public static Long pexpire(byte[] key, long milliseconds) {
        return null;
    }

    
    public static Long expireAt(byte[] key, long unixTime) {
        return null;
    }

    
    public static Long pexpireAt(byte[] key, long millisecondsTimestamp) {
        return null;
    }

    
    public static Long ttl(byte[] key) {
        return null;
    }

    
    public static Boolean setbit(byte[] key, long offset, boolean value) {
        return null;
    }

    
    public static Boolean setbit(byte[] key, long offset, byte[] value) {
        return null;
    }

    
    public static Boolean getbit(byte[] key, long offset) {
        return null;
    }

    
    public static Long setrange(byte[] key, long offset, byte[] value) {
        return null;
    }

    
    public static byte[] getrange(byte[] key, long startOffset, long endOffset) {
        return new byte[0];
    }

    
    public static byte[] getSet(byte[] key, byte[] value) {
        return new byte[0];
    }

    
    public static Long setnx(byte[] key, byte[] value) {
        return null;
    }

    
    public static String setex(byte[] key, int seconds, byte[] value) {
        return null;
    }

    
    public static Long decrBy(byte[] key, long integer) {
        return null;
    }

    
    public static Long decr(byte[] key) {
        return null;
    }

    
    public static Long incrBy(byte[] key, long integer) {
        return null;
    }

    
    public static Double incrByFloat(byte[] key, double value) {
        return null;
    }

    
    public static Long incr(byte[] key) {
        return null;
    }

    
    public static Long append(byte[] key, byte[] value) {
        return null;
    }

    
    public static byte[] substr(byte[] key, int start, int end) {
        return new byte[0];
    }

    
    public static Long hset(byte[] key, byte[] field, byte[] value) {
        return null;
    }

    
    public static byte[] hget(byte[] key, byte[] field) {
        return new byte[0];
    }

    
    public static Long hsetnx(byte[] key, byte[] field, byte[] value) {
        return null;
    }

    
    public static String hmset(byte[] key, Map<byte[], byte[]> hash) {
        return null;
    }

    
    public static List<byte[]> hmget(byte[] key, byte[]... fields) {
        return null;
    }

    
    public static Long hincrBy(byte[] key, byte[] field, long value) {
        return null;
    }

    
    public static Double hincrByFloat(byte[] key, byte[] field, double value) {
        return null;
    }

    
    public static Boolean hexists(byte[] key, byte[] field) {
        return null;
    }

    
    public static Long hdel(byte[] key, byte[]... field) {
        return null;
    }

    
    public static Long hlen(byte[] key) {
        return null;
    }

    
    public static Set<byte[]> hkeys(byte[] key) {
        return null;
    }

    
    public static Collection<byte[]> hvals(byte[] key) {
        return null;
    }

    
    public static Map<byte[], byte[]> hgetAll(byte[] key) {
        return null;
    }

    
    public static Long rpush(byte[] key, byte[]... args) {
        return null;
    }

    
    public static Long lpush(byte[] key, byte[]... args) {
        return null;
    }

    
    public static Long llen(byte[] key) {
        return null;
    }

    
    public static List<byte[]> lrange(byte[] key, long start, long end) {
        return null;
    }

    
    public static String ltrim(byte[] key, long start, long end) {
        return null;
    }

    
    public static byte[] lindex(byte[] key, long index) {
        return new byte[0];
    }

    
    public static String lset(byte[] key, long index, byte[] value) {
        return null;
    }

    
    public static Long lrem(byte[] key, long count, byte[] value) {
        return null;
    }

    
    public static byte[] lpop(byte[] key) {
        return new byte[0];
    }

    
    public static byte[] rpop(byte[] key) {
        return new byte[0];
    }

    
    public static Long sadd(byte[] key, byte[]... member) {
        return null;
    }

    
    public static Set<byte[]> smembers(byte[] key) {
        return null;
    }

    
    public static Long srem(byte[] key, byte[]... member) {
        return null;
    }

    
    public static byte[] spop(byte[] key) {
        return new byte[0];
    }

    
    public static Set<byte[]> spop(byte[] key, long count) {
        return null;
    }

    
    public static Long scard(byte[] key) {
        return null;
    }

    
    public static Boolean sismember(byte[] key, byte[] member) {
        return null;
    }

    
    public static byte[] srandmember(byte[] key) {
        return new byte[0];
    }

    
    public static List<byte[]> srandmember(byte[] key, int count) {
        return null;
    }

    
    public static Long strlen(byte[] key) {
        return null;
    }

    
    public static Long zadd(byte[] key, double score, byte[] member) {
        return null;
    }

    
    public static Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
        return null;
    }

    
    public static Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        return null;
    }

    
    public static Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
        return null;
    }

    
    public static Set<byte[]> zrange(byte[] key, long start, long end) {
        return null;
    }

    
    public static Long zrem(byte[] key, byte[]... member) {
        return null;
    }

    
    public static Double zincrby(byte[] key, double score, byte[] member) {
        return null;
    }

    
    public static Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params) {
        return null;
    }

    
    public static Long zrank(byte[] key, byte[] member) {
        return null;
    }

    
    public static Long zrevrank(byte[] key, byte[] member) {
        return null;
    }

    
    public static Set<byte[]> zrevrange(byte[] key, long start, long end) {
        return null;
    }

    
    public static Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
        return null;
    }

    
    public static Long zcard(byte[] key) {
        return null;
    }

    
    public static Double zscore(byte[] key, byte[] member) {
        return null;
    }

    
    public static List<byte[]> sort(byte[] key) {
        return null;
    }

    
    public static List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        return null;
    }

    
    public static Long zcount(byte[] key, double min, double max) {
        return null;
    }

    
    public static Long zcount(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return null;
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return null;
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        return null;
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
        return null;
    }

    
    public static Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return null;
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        return null;
    }

    
    public static Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return null;
    }

    
    public static Long zremrangeByRank(byte[] key, long start, long end) {
        return null;
    }

    
    public static Long zremrangeByScore(byte[] key, double start, double end) {
        return null;
    }

    
    public static Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
        return null;
    }

    
    public static Long zlexcount(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    
    public static Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    
    public static Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return null;
    }

    
    public static Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        return null;
    }

    
    public static Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return null;
    }

    
    public static Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    
    public static Long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value) {
        return null;
    }

    
    public static Long lpushx(byte[] key, byte[]... arg) {
        return null;
    }

    
    public static Long rpushx(byte[] key, byte[]... arg) {
        return null;
    }

    
    public static List<byte[]> blpop(byte[] arg) {
        return null;
    }

    
    public static List<byte[]> brpop(byte[] arg) {
        return null;
    }

    
    public static Long del(byte[] key) {
        return null;
    }

    
    public static byte[] echo(byte[] arg) {
        return new byte[0];
    }

    
    public static Long move(byte[] key, int dbIndex) {
        return null;
    }

    
    public static Long bitcount(byte[] key) {
        return null;
    }

    
    public static Long bitcount(byte[] key, long start, long end) {
        return null;
    }

    
    public static Long pfadd(byte[] key, byte[]... elements) {
        return null;
    }

    
    public static long pfcount(byte[] key) {
        return 0;
    }

    
    public static Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
        return null;
    }

    
    public static Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return null;
    }

    
    public static Double geodist(byte[] key, byte[] member1, byte[] member2) {
        return null;
    }

    
    public static Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
        return null;
    }

    
    public static List<byte[]> geohash(byte[] key, byte[]... members) {
        return null;
    }

    
    public static List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    
    public static ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
        return null;
    }

    
    public static ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
        return null;
    }

    
    public static ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
        return null;
    }

    
    public static ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
        return null;
    }

    
    public static ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
        return null;
    }

    
    public static ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
        return null;
    }

    
    public static String set(String key, String value) {
        return null;
    }

    
    public static String set(String key, String value, String nxxx, String expx, long time) {
        return null;
    }

    
    public static String set(String key, String value, String nxxx) {
        return null;
    }

    
    public static String get(String key) {
        return null;
    }

    
    public static Boolean exists(String key) {
        return null;
    }

    
    public static Long persist(String key) {
        return null;
    }

    
    public static String type(String key) {
        return null;
    }

    
    public static Long expire(String key, int seconds) {
        return null;
    }

    
    public static Long pexpire(String key, long milliseconds) {
        return null;
    }

    
    public static Long expireAt(String key, long unixTime) {
        return null;
    }

    
    public static Long pexpireAt(String key, long millisecondsTimestamp) {
        return null;
    }

    
    public static Long ttl(String key) {
        return null;
    }

    
    public static Long pttl(String key) {
        return null;
    }

    
    public static Boolean setbit(String key, long offset, boolean value) {
        return null;
    }

    
    public static Boolean setbit(String key, long offset, String value) {
        return null;
    }

    
    public static Boolean getbit(String key, long offset) {
        return null;
    }

    
    public static Long setrange(String key, long offset, String value) {
        return null;
    }

    
    public static String getrange(String key, long startOffset, long endOffset) {
        return null;
    }

    
    public static String getSet(String key, String value) {
        return null;
    }

    
    public static Long setnx(String key, String value) {
        return null;
    }

    
    public static String setex(String key, int seconds, String value) {
        return null;
    }

    
    public static String psetex(String key, long milliseconds, String value) {
        return null;
    }

    
    public static Long decrBy(String key, long integer) {
        return null;
    }

    
    public static Long decr(String key) {
        return null;
    }

    
    public static Long incrBy(String key, long integer) {
        return null;
    }

    
    public static Double incrByFloat(String key, double value) {
        return null;
    }

    
    public static Long incr(String key) {
        return null;
    }

    
    public static Long append(String key, String value) {
        return null;
    }

    
    public static String substr(String key, int start, int end) {
        return null;
    }

    
    public static Long hset(String key, String field, String value) {
        return null;
    }

    
    public static String hget(String key, String field) {
        return null;
    }

    
    public static Long hsetnx(String key, String field, String value) {
        return null;
    }

    
    public static String hmset(String key, Map<String, String> hash) {
        return null;
    }

    
    public static List<String> hmget(String key, String... fields) {
        return null;
    }

    
    public static Long hincrBy(String key, String field, long value) {
        return null;
    }

    
    public static Double hincrByFloat(String key, String field, double value) {
        return null;
    }

    
    public static Boolean hexists(String key, String field) {
        return null;
    }

    
    public static Long hdel(String key, String... field) {
        return null;
    }

    
    public static Long hlen(String key) {
        return null;
    }

    
    public static Set<String> hkeys(String key) {
        return null;
    }

    
    public static List<String> hvals(String key) {
        return null;
    }

    
    public static Map<String, String> hgetAll(String key) {
        return null;
    }

    
    public static Long rpush(String key, String... string) {
        return null;
    }

    
    public static Long lpush(String key, String... string) {
        return null;
    }

    
    public static Long llen(String key) {
        return null;
    }

    
    public static List<String> lrange(String key, long start, long end) {
        return null;
    }

    
    public static String ltrim(String key, long start, long end) {
        return null;
    }

    
    public static String lindex(String key, long index) {
        return null;
    }

    
    public static String lset(String key, long index, String value) {
        return null;
    }

    
    public static Long lrem(String key, long count, String value) {
        return null;
    }

    
    public static String lpop(String key) {
        return null;
    }

    
    public static String rpop(String key) {
        return null;
    }

    
    public static Long sadd(String key, String... member) {
        return null;
    }

    
    public static Set<String> smembers(String key) {
        return null;
    }

    
    public static Long srem(String key, String... member) {
        return null;
    }

    
    public static String spop(String key) {
        return null;
    }

    
    public static Set<String> spop(String key, long count) {
        return null;
    }

    
    public static Long scard(String key) {
        return null;
    }

    
    public static Boolean sismember(String key, String member) {
        return null;
    }

    
    public static String srandmember(String key) {
        return null;
    }

    
    public static List<String> srandmember(String key, int count) {
        return null;
    }

    
    public static Long strlen(String key) {
        return null;
    }

    
    public static Long zadd(String key, double score, String member) {
        return null;
    }

    
    public static Long zadd(String key, double score, String member, ZAddParams params) {
        return null;
    }

    
    public static Long zadd(String key, Map<String, Double> scoreMembers) {
        return null;
    }

    
    public static Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return null;
    }

    
    public static Set<String> zrange(String key, long start, long end) {
        return null;
    }

    
    public static Long zrem(String key, String... member) {
        return null;
    }

    
    public static Double zincrby(String key, double score, String member) {
        return null;
    }

    
    public static Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return null;
    }

    
    public static Long zrank(String key, String member) {
        return null;
    }

    
    public static Long zrevrank(String key, String member) {
        return null;
    }

    
    public static Set<String> zrevrange(String key, long start, long end) {
        return null;
    }

    
    public static Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return null;
    }

    
    public static Long zcard(String key) {
        return null;
    }

    
    public static Double zscore(String key, String member) {
        return null;
    }

    
    public static List<String> sort(String key) {
        return null;
    }

    
    public static List<String> sort(String key, SortingParams sortingParameters) {
        return null;
    }

    
    public static Long zcount(String key, double min, double max) {
        return null;
    }

    
    public static Long zcount(String key, String min, String max) {
        return null;
    }

    
    public static Set<String> zrangeByScore(String key, double min, double max) {
        return null;
    }

    
    public static Set<String> zrangeByScore(String key, String min, String max) {
        return null;
    }

    
    public static Set<String> zrevrangeByScore(String key, double max, double min) {
        return null;
    }

    
    public static Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return null;
    }

    
    public static Set<String> zrevrangeByScore(String key, String max, String min) {
        return null;
    }

    
    public static Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return null;
    }

    
    public static Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return null;
    }

    
    public static Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return null;
    }

    
    public static Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return null;
    }

    
    public static Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return null;
    }

    
    public static Long zremrangeByRank(String key, long start, long end) {
        return null;
    }

    
    public static Long zremrangeByScore(String key, double start, double end) {
        return null;
    }

    
    public static Long zremrangeByScore(String key, String start, String end) {
        return null;
    }

    
    public static Long zlexcount(String key, String min, String max) {
        return null;
    }

    
    public static Set<String> zrangeByLex(String key, String min, String max) {
        return null;
    }

    
    public static Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return null;
    }

    
    public static Set<String> zrevrangeByLex(String key, String max, String min) {
        return null;
    }

    
    public static Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return null;
    }

    
    public static Long zremrangeByLex(String key, String min, String max) {
        return null;
    }

    
    public static Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return null;
    }

    
    public static Long lpushx(String key, String... string) {
        return null;
    }

    
    public static Long rpushx(String key, String... string) {
        return null;
    }

    
    public static List<String> blpop(String arg) {
        return null;
    }

    
    public static List<String> blpop(int timeout, String key) {
        return null;
    }

    
    public static List<String> brpop(String arg) {
        return null;
    }

    
    public static List<String> brpop(int timeout, String key) {
        return null;
    }

    
    public static Long del(String key) {
        return null;
    }

    
    public static String echo(String string) {
        return null;
    }

    
    public static Long move(String key, int dbIndex) {
        return null;
    }

    
    public static Long bitcount(String key) {
        return null;
    }

    
    public static Long bitcount(String key, long start, long end) {
        return null;
    }

    
    public static Long bitpos(String key, boolean value) {
        return null;
    }

    
    public static Long bitpos(String key, boolean value, BitPosParams params) {
        return null;
    }

    
    public static ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return null;
    }

    
    public static ScanResult<String> sscan(String key, int cursor) {
        return null;
    }

    
    public static ScanResult<Tuple> zscan(String key, int cursor) {
        return null;
    }

    
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return null;
    }

    
    public static ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return null;
    }

    
    public static ScanResult<String> sscan(String key, String cursor) {
        return null;
    }

    
    public static ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return null;
    }

    
    public static ScanResult<Tuple> zscan(String key, String cursor) {
        return null;
    }

    
    public static ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return null;
    }

    
    public static Long pfadd(String key, String... elements) {
        return null;
    }

    
    public static long pfcount(String key) {
        return 0;
    }

    
    public static Long geoadd(String key, double longitude, double latitude, String member) {
        return null;
    }

    
    public static Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return null;
    }

    
    public static Double geodist(String key, String member1, String member2) {
        return null;
    }

    
    public static Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return null;
    }

    
    public static List<String> geohash(String key, String... members) {
        return null;
    }

    
    public static List<GeoCoordinate> geopos(String key, String... members) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return null;
    }

    
    public static List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }
}
