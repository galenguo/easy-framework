package com.efun.core.cache.redis.copy;

import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

//注释掉的为ShardedJedis中不支持的操作，在Jedis中是支持的。

/**
 * redis客户端一致性哈希集群配置下常用的功能操作方法接口类
 * 
 * @author Administrator
 */
public interface IRedisService {

	Long append(byte[] key, byte[] value);

	Long append(String key, String value);

	Long bitcount(byte[] key);

	Long bitcount(byte[] key, long start, long end);

	Long bitcount(String key);

	Long bitcount(String key, long start, long end);

	List<byte[]> blpop(byte[] key);

	// Long del(byte[]... key);

	List<String> blpop(int timeout, String key);

	// Long del(String... key);

	List<String> blpop(String key);

	List<byte[]> brpop(byte[] key);

	List<String> brpop(int timeout, String key);

	List<String> brpop(String key);

	Long decr(byte[] key);

	Long decr(String key);

	Long decrBy(byte[] key, long value);

	Long decrBy(String key, long value);

	Long del(byte[] key, boolean delay);

	Long del(String key, boolean delay);

	Boolean exists(byte[] key);

	Boolean exists(String key);

	Long expire(byte[] key, int seconds);

	Long expire(String key, int seconds);

	Long expireAt(byte[] key, long unixTime);

	Long expireAt(String key, long unixTime);

	byte[] get(byte[] key);

	String get(String key);

	byte[] getrange(byte[] key, long startOffset, long endOffset);

	String getrange(String key, long startOffset, long endOffset);

	byte[] getSet(byte[] key, byte[] value);

	String getSet(String key, String value);

	Long hdel(byte[] key, byte[]... fields);

	Long hdel(String key, String... fields);

	Boolean hexists(byte[] key, byte[] field);

	Boolean hexists(String key, String field);

	byte[] hget(byte[] key, byte[] field);

	String hget(String key, String field);

	Map<byte[], byte[]> hgetAll(byte[] key);

	Map<String, String> hgetAll(String key);

	Long hincrBy(byte[] key, byte[] field, long value);

	Long hincrBy(String key, String field, long value);

	// Set<byte[]> keys(byte[] pattern);

	// Set<String> keys(String pattern);

	Double hincrByFloat(byte[] key, byte[] field, double value);

	Double hincrByFloat(String key, String field, double value);

	Set<byte[]> hkeys(byte[] key);

	Set<String> hkeys(String key);

	Long hlen(byte[] key);

	Long hlen(String key);

	List<byte[]> hmget(byte[] key, byte[]... fields);

	List<String> hmget(String key, String... fields);

	String hmset(byte[] key, Map<byte[], byte[]> hash);

	String hmset(String key, Map<String, String> hash);

	Long hset(byte[] key, byte[] field, byte[] value);

	Long hset(String key, String field, String value);

	Long hsetnx(byte[] key, byte[] field, byte[] value);

	Long hsetnx(String key, String field, String value);

	Collection<byte[]> hvals(byte[] key);

	List<String> hvals(String key);

	Long incr(byte[] key);

	Long incr(String key);

	// List<byte[]> mget(byte[]... keys);

	// List<String> mget(String... keys);

	// Long persist(byte[] key);

	// Long persist(String key);

	// Long pexpire(byte[] key, long milliseconds);

	// Long pexpire(String key, long milliseconds);

	// Long pexpireAt(byte[] key, long milliseconds);

	// Long pexpireAt(String key, long milliseconds);

	// String rename(byte[] oldkey, byte[] newkey);

	// String rename(String oldkey, String newkey);

	// Long renamenx(byte[] oldkey, byte[] newkey);

	// Long renamenx(String oldkey, String newkey);

	Long incrBy(byte[] key, long value);

	Long incrBy(String key, long value);

	// byte[] rpoplpush(byte[] srckey, byte[] dstkey);

	// String rpoplpush(String srckey, String dstkey);

	Double incrByFloat(byte[] key, double value);

	Double incrByFloat(String key, double value);

	byte[] lindex(byte[] key, long index);

	String lindex(String key, long index);

	Long llen(byte[] key);

	Long llen(String key);

	byte[] lpop(byte[] key);

	String lpop(String key);

	Long lpush(byte[] key, byte[]... strings);

	Long lpush(String key, String... strings);

	Long lpushx(byte[] key, byte[]... strings);

	Long lpushx(String key, String... strings);

	List<byte[]> lrange(byte[] key, long start, long end);

	List<String> lrange(String key, long start, long end);

	Long lrem(byte[] key, long count, byte[] value);

	Long lrem(String key, long count, String value);

	String lset(byte[] key, long index, byte[] value);

	String lset(String key, long index, String value);

	String ltrim(byte[] key, long start, long end);

	String ltrim(String key, long start, long end);

	Long pfadd(byte[] key, byte[]... elements);

	Long pfadd(String key, String... elements);

	// Set<byte[]> sunion(byte[]... key);

	// Set<String> sunion(String... key);

	long pfcount(byte[] key);

	long pfcount(String key);

	byte[] rpop(byte[] key);

	String rpop(String key);

	Long rpush(byte[] key, byte[]... strings);

	Long rpush(String key, String... strings);

	Long rpushx(byte[] key, byte[]... strings);

	Long rpushx(String key, String... strings);

	Long sadd(byte[] key, byte[]... members);

	Long sadd(String key, String... members);

	Long scard(byte[] key);

	Long scard(String key);

	String set(byte[] key, byte[] value);

	String set(String key, String value);
	
	String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time);

	String set(String key, String value, String nxxx, String expx, long time);

	String setex(byte[] key, int seconds, byte[] value);

	String setex(String key, int seconds, String value);

	// Long pttl(String key);

	// Long pttl(byte[] key);

	Long setnx(byte[] key, byte[] value);

	Long setnx(String key, String value);

	Long setrange(byte[] key, long offset, byte[] value);

	Long setrange(String key, long offset, String value);

	Boolean sismember(byte[] key, byte[] member);

	Boolean sismember(String key, String member);

	Set<byte[]> smembers(byte[] key);

	Set<String> smembers(String key);

	List<byte[]> sort(byte[] key);

	List<byte[]> sort(byte[] key, SortingParams sortingParameters);

	List<String> sort(String key);

	List<String> sort(String key, SortingParams sortingParameters);

	byte[] spop(byte[] key);

	String spop(String key);

	Long srem(byte[] key, byte[]... members);

	Long srem(String key, String... members);

	Long strlen(byte[] key);

	Long strlen(String key);

	Long ttl(byte[] key);

	Long ttl(String key);

	String type(byte[] key);

	String type(String key);

	Long zadd(byte[] key, double score, byte[] member);

	Long zadd(byte[] key, Map<byte[], Double> scoreMembers);

	Long zadd(String key, double score, String member);

	Long zadd(String key, Map<String, Double> scoreMembers);

	Long zcard(byte[] key);

	Long zcard(String key);

	Long zcount(byte[] key, byte[] min, byte[] max);

	Long zcount(byte[] key, double min, double max);

	Long zcount(String key, double min, double max);

	Long zcount(String key, String min, String max);

	Double zincrby(byte[] key, double score, byte[] member);

	Double zincrby(String key, double score, String member);

	Long zlexcount(byte[] key, byte[] min, byte[] max);

	Long zlexcount(String key, String min, String max);

	Set<byte[]> zrange(byte[] key, long start, long end);

	Set<String> zrange(String key, long start, long end);

	Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max);

	Set<byte[]> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count);

	Set<String> zrangeByLex(String key, String min, String max);

	Set<String> zrangeByLex(String key, String min, String max, int offset, int count);

	Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max);

	Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count);

	Set<byte[]> zrangeByScore(byte[] key, double min, double max);

	Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count);

	Set<String> zrangeByScore(String key, double min, double max);

	Set<String> zrangeByScore(String key, double min, double max, int offset, int count);

	Set<String> zrangeByScore(String key, String min, String max);

	Set<String> zrangeByScore(String key, String min, String max, int offset, int count);

	Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max);

	Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count);

	Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max);

	Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count);

	Set<Tuple> zrangeByScoreWithScores(String key, double min, double max);

	Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);

	Set<Tuple> zrangeByScoreWithScores(String key, String min, String max);

	Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count);

	Set<Tuple> zrangeWithScores(byte[] key, long start, long end);

	Set<Tuple> zrangeWithScores(String key, long start, long end);

	Long zrank(byte[] key, byte[] member);

	Long zrank(String key, String member);

	Long zrem(byte[] key, byte[]... members);

	Long zrem(String key, String... members);

	Long zremrangeByLex(byte[] key, byte[] min, byte[] max);

	Long zremrangeByLex(String key, String min, String max);

	Long zremrangeByRank(byte[] key, long start, long end);

	Long zremrangeByRank(String key, long start, long end);

	Long zremrangeByScore(byte[] key, byte[] start, byte[] end);

	Long zremrangeByScore(byte[] key, double start, double end);

	Long zremrangeByScore(String key, double start, double end);

	Long zremrangeByScore(String key, String start, String end);

	Set<byte[]> zrevrange(byte[] key, long start, long end);

	Set<String> zrevrange(String key, long start, long end);

	Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min);

	Set<byte[]> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count);

	Set<String> zrevrangeByLex(String key, String max, String min);

	Set<String> zrevrangeByLex(String key, String max, String min, int offset, int count);

	Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min);

	Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count);

	Set<byte[]> zrevrangeByScore(byte[] key, double max, double min);

	Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count);

	Set<String> zrevrangeByScore(String key, double max, double min);

	Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count);

	Set<String> zrevrangeByScore(String key, String max, String min);

	Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min);

	Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min);

	Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min);

	Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

	Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min);

	Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count);

	Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end);

	Set<Tuple> zrevrangeWithScores(String key, long start, long end);

	Long zrevrank(byte[] key, byte[] member);

	Long zrevrank(String key, String member);

	Double zscore(byte[] key, byte[] member);

	Double zscore(String key, String member);

	boolean tryLock(String key, boolean permanent);

	boolean tryLock(String key, long timeoutMillis);

	boolean tryLock(String key, int seconds, long timeoutMillis);

	boolean lock(String key, boolean permanent);

	boolean lock(String key, int seconds);
	
	boolean isLocked(String key);

	Long unlock(String key);

}
