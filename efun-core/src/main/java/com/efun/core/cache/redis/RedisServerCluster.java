package com.efun.core.cache.redis;

import org.springframework.stereotype.Service;
import redis.clients.jedis.*;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RedisServerCluster
 * 服务端集群（暂时实现部分常用接口）
 *
 * @author Galen
 * @since 2016/8/2
 */
public class RedisServerCluster implements CommonRedisCommands {

    private JedisCluster cluster;

    public RedisServerCluster(JedisCluster jedisCluster) {
        this.cluster = jedisCluster;
    }

    public void destroy() throws IOException {
        this.cluster.close();
    }

    @Override
    public String set(byte[] key, byte[] value) {
        return null;
    }

    @Override
    public String set(byte[] key, byte[] value, byte[] nxxx) {
        return null;
    }

    @Override
    public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time) {
        return null;
    }

    @Override
    public byte[] get(byte[] key) {
        return new byte[0];
    }

    @Override
    public Boolean exists(byte[] key) {
        return null;
    }

    @Override
    public Long persist(byte[] key) {
        return null;
    }

    @Override
    public String type(byte[] key) {
        return null;
    }

    @Override
    public Long expire(byte[] key, int seconds) {
        return null;
    }

    @Override
    public Long pexpire(byte[] key, long milliseconds) {
        return null;
    }

    @Override
    public Long expireAt(byte[] key, long unixTime) {
        return null;
    }

    @Override
    public Long pexpireAt(byte[] key, long millisecondsTimestamp) {
        return null;
    }

    @Override
    public Long ttl(byte[] key) {
        return null;
    }

    @Override
    public Boolean setbit(byte[] key, long offset, boolean value) {
        return null;
    }

    @Override
    public Boolean setbit(byte[] key, long offset, byte[] value) {
        return null;
    }

    @Override
    public Boolean getbit(byte[] key, long offset) {
        return null;
    }

    @Override
    public Long setrange(byte[] key, long offset, byte[] value) {
        return null;
    }

    @Override
    public byte[] getrange(byte[] key, long startOffset, long endOffset) {
        return new byte[0];
    }

    @Override
    public byte[] getSet(byte[] key, byte[] value) {
        return new byte[0];
    }

    @Override
    public Long setnx(byte[] key, byte[] value) {
        return null;
    }

    @Override
    public String setex(byte[] key, int seconds, byte[] value) {
        return null;
    }

    @Override
    public Long decrBy(byte[] key, long integer) {
        return null;
    }

    @Override
    public Long decr(byte[] key) {
        return null;
    }

    @Override
    public Long incrBy(byte[] key, long integer) {
        return null;
    }

    @Override
    public Double incrByFloat(byte[] key, double value) {
        return null;
    }

    @Override
    public Long incr(byte[] key) {
        return null;
    }

    @Override
    public Long append(byte[] key, byte[] value) {
        return null;
    }

    @Override
    public byte[] substr(byte[] key, int start, int end) {
        return new byte[0];
    }

    @Override
    public Long hset(byte[] key, byte[] field, byte[] value) {
        return null;
    }

    @Override
    public byte[] hget(byte[] key, byte[] field) {
        return new byte[0];
    }

    @Override
    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
        return null;
    }

    @Override
    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
        return null;
    }

    @Override
    public List<byte[]> hmget(byte[] key, byte[]... fields) {
        return null;
    }

    @Override
    public Long hincrBy(byte[] key, byte[] field, long value) {
        return null;
    }

    @Override
    public Double hincrByFloat(byte[] key, byte[] field, double value) {
        return null;
    }

    @Override
    public Boolean hexists(byte[] key, byte[] field) {
        return null;
    }

    @Override
    public Long hdel(byte[] key, byte[]... field) {
        return null;
    }

    @Override
    public Long hlen(byte[] key) {
        return null;
    }

    @Override
    public Set<byte[]> hkeys(byte[] key) {
        return null;
    }

    @Override
    public Collection<byte[]> hvals(byte[] key) {
        return null;
    }

    @Override
    public Map<byte[], byte[]> hgetAll(byte[] key) {
        return null;
    }

    @Override
    public Long rpush(byte[] key, byte[]... args) {
        return null;
    }

    @Override
    public Long lpush(byte[] key, byte[]... args) {
        return null;
    }

    @Override
    public Long llen(byte[] key) {
        return null;
    }

    @Override
    public List<byte[]> lrange(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public String ltrim(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public byte[] lindex(byte[] key, long index) {
        return new byte[0];
    }

    @Override
    public String lset(byte[] key, long index, byte[] value) {
        return null;
    }

    @Override
    public Long lrem(byte[] key, long count, byte[] value) {
        return null;
    }

    @Override
    public byte[] lpop(byte[] key) {
        return new byte[0];
    }

    @Override
    public byte[] rpop(byte[] key) {
        return new byte[0];
    }

    @Override
    public Long sadd(byte[] key, byte[]... member) {
        return null;
    }

    @Override
    public Set<byte[]> smembers(byte[] key) {
        return null;
    }

    @Override
    public Long srem(byte[] key, byte[]... member) {
        return null;
    }

    @Override
    public byte[] spop(byte[] key) {
        return new byte[0];
    }

    @Override
    public Set<byte[]> spop(byte[] key, long count) {
        return null;
    }

    @Override
    public Long scard(byte[] key) {
        return null;
    }

    @Override
    public Boolean sismember(byte[] key, byte[] member) {
        return null;
    }

    @Override
    public byte[] srandmember(byte[] key) {
        return new byte[0];
    }

    @Override
    public List<byte[]> srandmember(byte[] key, int count) {
        return null;
    }

    @Override
    public Long strlen(byte[] key) {
        return null;
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member) {
        return null;
    }

    @Override
    public Long zadd(byte[] key, double score, byte[] member, ZAddParams params) {
        return null;
    }

    @Override
    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        return null;
    }

    @Override
    public Long zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
        return null;
    }

    @Override
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public Long zrem(byte[] key, byte[]... member) {
        return null;
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member) {
        return null;
    }

    @Override
    public Double zincrby(byte[] key, double score, byte[] member, ZIncrByParams params) {
        return null;
    }

    @Override
    public Long zrank(byte[] key, byte[] member) {
        return null;
    }

    @Override
    public Long zrevrank(byte[] key, byte[] member) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public Long zcard(byte[] key) {
        return null;
    }

    @Override
    public Double zscore(byte[] key, byte[] member) {
        return null;
    }

    @Override
    public List<byte[]> sort(byte[] key) {
        return null;
    }

    @Override
    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
        return null;
    }

    @Override
    public Long zcount(byte[] key, double min, double max) {
        return null;
    }

    @Override
    public Long zcount(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return null;
    }

    @Override
    public Long zremrangeByRank(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public Long zremrangeByScore(byte[] key, double start, double end) {
        return null;
    }

    @Override
    public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
        return null;
    }

    @Override
    public Long zlexcount(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        return null;
    }

    @Override
    public Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return null;
    }

    @Override
    public Long zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        return null;
    }

    @Override
    public Long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value) {
        return null;
    }

    @Override
    public Long lpushx(byte[] key, byte[]... arg) {
        return null;
    }

    @Override
    public Long rpushx(byte[] key, byte[]... arg) {
        return null;
    }

    @Override
    public List<byte[]> blpop(byte[] arg) {
        return null;
    }

    @Override
    public List<byte[]> brpop(byte[] arg) {
        return null;
    }

    @Override
    public Long del(byte[] key) {
        return null;
    }

    @Override
    public byte[] echo(byte[] arg) {
        return new byte[0];
    }

    @Override
    public Long move(byte[] key, int dbIndex) {
        return null;
    }

    @Override
    public Long bitcount(byte[] key) {
        return null;
    }

    @Override
    public Long bitcount(byte[] key, long start, long end) {
        return null;
    }

    @Override
    public Long pfadd(byte[] key, byte[]... elements) {
        return null;
    }

    @Override
    public long pfcount(byte[] key) {
        return 0;
    }

    @Override
    public Long geoadd(byte[] key, double longitude, double latitude, byte[] member) {
        return null;
    }

    @Override
    public Long geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return null;
    }

    @Override
    public Double geodist(byte[] key, byte[] member1, byte[] member2) {
        return null;
    }

    @Override
    public Double geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
        return null;
    }

    @Override
    public List<byte[]> geohash(byte[] key, byte[]... members) {
        return null;
    }

    @Override
    public List<GeoCoordinate> geopos(byte[] key, byte[]... members) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadius(byte[] key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(byte[] key, byte[] member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    @Override
    public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor) {
        return null;
    }

    @Override
    public ScanResult<Map.Entry<byte[], byte[]>> hscan(byte[] key, byte[] cursor, ScanParams params) {
        return null;
    }

    @Override
    public ScanResult<byte[]> sscan(byte[] key, byte[] cursor) {
        return null;
    }

    @Override
    public ScanResult<byte[]> sscan(byte[] key, byte[] cursor, ScanParams params) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(byte[] key, byte[] cursor, ScanParams params) {
        return null;
    }

    @Override
    public String set(String key, String value) {
        return null;
    }

    @Override
    public String set(String key, String value, String nxxx, String expx, long time) {
        return null;
    }

    @Override
    public String set(String key, String value, String nxxx) {
        return null;
    }

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public Boolean exists(String key) {
        return null;
    }

    @Override
    public Long persist(String key) {
        return null;
    }

    @Override
    public String type(String key) {
        return null;
    }

    @Override
    public Long expire(String key, int seconds) {
        return null;
    }

    @Override
    public Long pexpire(String key, long milliseconds) {
        return null;
    }

    @Override
    public Long expireAt(String key, long unixTime) {
        return null;
    }

    @Override
    public Long pexpireAt(String key, long millisecondsTimestamp) {
        return null;
    }

    @Override
    public Long ttl(String key) {
        return null;
    }

    @Override
    public Long pttl(String key) {
        return null;
    }

    @Override
    public Boolean setbit(String key, long offset, boolean value) {
        return null;
    }

    @Override
    public Boolean setbit(String key, long offset, String value) {
        return null;
    }

    @Override
    public Boolean getbit(String key, long offset) {
        return null;
    }

    @Override
    public Long setrange(String key, long offset, String value) {
        return null;
    }

    @Override
    public String getrange(String key, long startOffset, long endOffset) {
        return null;
    }

    @Override
    public String getSet(String key, String value) {
        return null;
    }

    @Override
    public Long setnx(String key, String value) {
        return null;
    }

    @Override
    public String setex(String key, int seconds, String value) {
        return null;
    }

    @Override
    public String psetex(String key, long milliseconds, String value) {
        return null;
    }

    @Override
    public Long decrBy(String key, long integer) {
        return null;
    }

    @Override
    public Long decr(String key) {
        return null;
    }

    @Override
    public Long incrBy(String key, long integer) {
        return null;
    }

    @Override
    public Double incrByFloat(String key, double value) {
        return null;
    }

    @Override
    public Long incr(String key) {
        return null;
    }

    @Override
    public Long append(String key, String value) {
        return null;
    }

    @Override
    public String substr(String key, int start, int end) {
        return null;
    }

    @Override
    public Long hset(String key, String field, String value) {
        return null;
    }

    @Override
    public String hget(String key, String field) {
        return null;
    }

    @Override
    public Long hsetnx(String key, String field, String value) {
        return null;
    }

    @Override
    public String hmset(String key, Map<String, String> hash) {
        return null;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        return null;
    }

    @Override
    public Long hincrBy(String key, String field, long value) {
        return null;
    }

    @Override
    public Double hincrByFloat(String key, String field, double value) {
        return null;
    }

    @Override
    public Boolean hexists(String key, String field) {
        return null;
    }

    @Override
    public Long hdel(String key, String... field) {
        return null;
    }

    @Override
    public Long hlen(String key) {
        return null;
    }

    @Override
    public Set<String> hkeys(String key) {
        return null;
    }

    @Override
    public List<String> hvals(String key) {
        return null;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return null;
    }

    @Override
    public Long rpush(String key, String... string) {
        return null;
    }

    @Override
    public Long lpush(String key, String... string) {
        return null;
    }

    @Override
    public Long llen(String key) {
        return null;
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return null;
    }

    @Override
    public String ltrim(String key, long start, long end) {
        return null;
    }

    @Override
    public String lindex(String key, long index) {
        return null;
    }

    @Override
    public String lset(String key, long index, String value) {
        return null;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        return null;
    }

    @Override
    public String lpop(String key) {
        return null;
    }

    @Override
    public String rpop(String key) {
        return null;
    }

    @Override
    public Long sadd(String key, String... member) {
        return null;
    }

    @Override
    public Set<String> smembers(String key) {
        return null;
    }

    @Override
    public Long srem(String key, String... member) {
        return null;
    }

    @Override
    public String spop(String key) {
        return null;
    }

    @Override
    public Set<String> spop(String key, long count) {
        return null;
    }

    @Override
    public Long scard(String key) {
        return null;
    }

    @Override
    public Boolean sismember(String key, String member) {
        return null;
    }

    @Override
    public String srandmember(String key) {
        return null;
    }

    @Override
    public List<String> srandmember(String key, int count) {
        return null;
    }

    @Override
    public Long strlen(String key) {
        return null;
    }

    @Override
    public Long zadd(String key, double score, String member) {
        return null;
    }

    @Override
    public Long zadd(String key, double score, String member, ZAddParams params) {
        return null;
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        return null;
    }

    @Override
    public Long zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return null;
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return null;
    }

    @Override
    public Long zrem(String key, String... member) {
        return null;
    }

    @Override
    public Double zincrby(String key, double score, String member) {
        return null;
    }

    @Override
    public Double zincrby(String key, double score, String member, ZIncrByParams params) {
        return null;
    }

    @Override
    public Long zrank(String key, String member) {
        return null;
    }

    @Override
    public Long zrevrank(String key, String member) {
        return null;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        return null;
    }

    @Override
    public Long zcard(String key) {
        return null;
    }

    @Override
    public Double zscore(String key, String member) {
        return null;
    }

    @Override
    public List<String> sort(String key) {
        return null;
    }

    @Override
    public List<String> sort(String key, SortingParams sortingParameters) {
        return null;
    }

    @Override
    public Long zcount(String key, double min, double max) {
        return null;
    }

    @Override
    public Long zcount(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min) {
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, String min, String max, int offset, int count) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min) {
        return null;
    }

    @Override
    public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count) {
        return null;
    }

    @Override
    public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count) {
        return null;
    }

    @Override
    public Long zremrangeByRank(String key, long start, long end) {
        return null;
    }

    @Override
    public Long zremrangeByScore(String key, double start, double end) {
        return null;
    }

    @Override
    public Long zremrangeByScore(String key, String start, String end) {
        return null;
    }

    @Override
    public Long zlexcount(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max) {
        return null;
    }

    @Override
    public Set<String> zrangeByLex(String key, String min, String max, int offset, int count) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min) {
        return null;
    }

    @Override
    public Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return null;
    }

    @Override
    public Long zremrangeByLex(String key, String min, String max) {
        return null;
    }

    @Override
    public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value) {
        return null;
    }

    @Override
    public Long lpushx(String key, String... string) {
        return null;
    }

    @Override
    public Long rpushx(String key, String... string) {
        return null;
    }

    @Override
    public List<String> blpop(String arg) {
        return null;
    }

    @Override
    public List<String> blpop(int timeout, String key) {
        return null;
    }

    @Override
    public List<String> brpop(String arg) {
        return null;
    }

    @Override
    public List<String> brpop(int timeout, String key) {
        return null;
    }

    @Override
    public Long del(String key) {
        return null;
    }

    @Override
    public String echo(String string) {
        return null;
    }

    @Override
    public Long move(String key, int dbIndex) {
        return null;
    }

    @Override
    public Long bitcount(String key) {
        return null;
    }

    @Override
    public Long bitcount(String key, long start, long end) {
        return null;
    }

    @Override
    public Long bitpos(String key, boolean value) {
        return null;
    }

    @Override
    public Long bitpos(String key, boolean value, BitPosParams params) {
        return null;
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor) {
        return null;
    }

    @Override
    public ScanResult<String> sscan(String key, int cursor) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(String key, int cursor) {
        return null;
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor) {
        return null;
    }

    @Override
    public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params) {
        return null;
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor) {
        return null;
    }

    @Override
    public ScanResult<String> sscan(String key, String cursor, ScanParams params) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor) {
        return null;
    }

    @Override
    public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params) {
        return null;
    }

    @Override
    public Long pfadd(String key, String... elements) {
        return null;
    }

    @Override
    public long pfcount(String key) {
        return 0;
    }

    @Override
    public Long geoadd(String key, double longitude, double latitude, String member) {
        return null;
    }

    @Override
    public Long geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return null;
    }

    @Override
    public Double geodist(String key, String member1, String member2) {
        return null;
    }

    @Override
    public Double geodist(String key, String member1, String member2, GeoUnit unit) {
        return null;
    }

    @Override
    public List<String> geohash(String key, String... members) {
        return null;
    }

    @Override
    public List<GeoCoordinate> geopos(String key, String... members) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit) {
        return null;
    }

    @Override
    public List<GeoRadiusResponse> georadiusByMember(String key, String member, double radius, GeoUnit unit, GeoRadiusParam param) {
        return null;
    }
}
