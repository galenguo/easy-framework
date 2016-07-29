package com.efun.core.cache.redis;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * redis客户端一致性哈希集群配置
 * 
 * @author Administrator
 * 
 */
public final class Redis {
	private Redis() {
	}

	public static final String NX = "NX";
	public static final String XX = "XX";
	public static final String PX = "PX";
	public static final String EX = "EX";

	/**
	 * redis配置文件<br/>
	 * classpath目录
	 */
	public static final String REDIS_CONFIG_FILE = "redis.properties";

	protected static final Logger logger = LogManager.getLogger(Redis.class);
	private static ShardedJedisSentinelPool2 pool = null;
	private static AtomicBoolean poolState = new AtomicBoolean(false);
	/**
	 * 为了避免数据库主从同步延迟导致的问题，需要再次删除缓存key值的队列key
	 */
	private static String CACHE_KEYS_QUEUE_STRING;
	private static byte[] CACHE_KEYS_QUEUE_BYTE;

	static {
		CACHE_KEYS_QUEUE_STRING = CacheUtil.getCachePrefix() + "_CACHE_KEYS_QUEUE_STRING";

		try {
			CACHE_KEYS_QUEUE_BYTE = (CacheUtil.getCachePrefix() + "_CACHE_KEYS_QUEUE_BYTE").getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.error("UTF-8 UnsupportedEncoding", e);
		}
		CacheEntityUtil.containsKey("init");

		initPool();
	}

	/**
	 * 为了避免数据库主从同步延迟导致的问题，需要再次删除缓存key值的队列key
	 */
	public static final String getCacheQueueKeyString() {
		return CACHE_KEYS_QUEUE_STRING;
	}

	/**
	 * 为了避免数据库主从同步延迟导致的问题，需要再次删除缓存key值的队列key
	 */
	public static final byte[] getCacheQueueKeyByte() {
		return CACHE_KEYS_QUEUE_BYTE;
	}

	synchronized protected final static boolean initPool() {
		if (Charset.isSupported("UTF-8")) {
			System.out.println("UTF-8 Charset SupportedEncoding");
			logger.info("UTF-8 Charset SupportedEncoding");
		} else {
			System.out.println("UTF-8 Charset UnsupportedEncoding");
			logger.error("UTF-8 Charset UnsupportedEncoding");
			return false;
		}
		if (poolState.getAndSet(true)) {
			return true;
		}
		try {
			destroyPool();

			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig
					.setMaxIdle(Integer.valueOf(PropertiesCacheUtil.getValue("redis.pool.maxIdle", REDIS_CONFIG_FILE)));
			poolConfig.setMaxWaitMillis(
					Long.valueOf(PropertiesCacheUtil.getValue("redis.pool.maxWait", REDIS_CONFIG_FILE)));
			poolConfig.setTestOnBorrow(
					Boolean.valueOf(PropertiesCacheUtil.getValue("redis.pool.testOnBorrow", REDIS_CONFIG_FILE)));
			poolConfig.setTestOnReturn(
					Boolean.valueOf(PropertiesCacheUtil.getValue("redis.pool.testOnReturn", REDIS_CONFIG_FILE)));
			poolConfig.setMaxTotal(
					Integer.valueOf(PropertiesCacheUtil.getValue("redis.pool.maxActive", REDIS_CONFIG_FILE)));

			int timeout = Integer.valueOf(PropertiesCacheUtil.getValue("redis.pool.timeout", REDIS_CONFIG_FILE));

			// 以|隔开
			String servers = PropertiesCacheUtil.getValue("redis.serverNames", REDIS_CONFIG_FILE);
			String sentinels = PropertiesCacheUtil.getValue("redis.sentinels", REDIS_CONFIG_FILE);

			String nodes = PropertiesCacheUtil.getValue("cluster.nodes", REDIS_CONFIG_FILE);
			if (CommonUtil.objectIsNotNull(nodes)) {
				//服务器端集群初始化
				Set<HostAndPort> nodesSet = new HashSet<HostAndPort>();
				for (String node : nodes.split("\\,|\\||\\;")) {
					String temp = node.trim();
					String[] strs = temp.split("\\:");
					if (temp.length() > 0 && strs.length == 2) {
						nodesSet.add(new HostAndPort(strs[0], Integer.parseInt(strs[1])));
					}
				}

				Cluster.initCluster(nodesSet, timeout, poolConfig);
				logger.info(new StringBuilder().append("cluster.nodes(config)>>>").append(nodes).toString());
				System.out.println(new StringBuilder().append("cluster.nodes(config)>>>").append(nodes).toString());
				
				StringBuilder clusterBuilder=new StringBuilder("cluster.nodes>>>");
				JedisPool tempPool=loadCluster().getClusterNodes().values().iterator().next();
				Jedis jedis=tempPool.getResource();
				clusterBuilder.append(loadCluster().getClusterNodes().keySet()).append(" message:\n").append(jedis.clusterNodes());
				jedis.close();
				
				logger.info(new StringBuilder().append("cluster.nodes>>>").append(clusterBuilder.toString()).toString());
				System.out.println(new StringBuilder().append("cluster.nodes>>>").append(clusterBuilder.toString()).toString());
			} else {
				//客户端集群初始化
				List<String> serverNameList = new ArrayList<String>();
				Set<String> sentinelSet = new HashSet<String>();
				String[] names = new String[0];
				String[] sents = new String[0];

				if (servers != null && servers.trim().length() > 0) {
					names = servers.split("\\||\\,");
				}
				if (sentinels != null && sentinels.trim().length() > 0) {
					sents = sentinels.split("\\||\\,");
				}
				for (String str : names) {
					if (str.trim().length() != 0) {
						serverNameList.add(str);
					}
				}
				for (String str : sents) {
					if (str.trim().length() != 0) {
						sentinelSet.add(str);
					}
				}
				logger.info(new StringBuilder().append("redis.serverNames>>>").append(servers).toString());
				logger.info(new StringBuilder().append("redis.sentinels>>>").append(sentinels).toString());
				System.out.println(new StringBuilder().append("redis.serverNames>>>").append(servers).toString());
				System.out.println(new StringBuilder().append("redis.sentinels>>>").append(sentinels).toString());

				Set<String> nameSet = new LinkedHashSet<String>(serverNameList);
				pool = new ShardedJedisSentinelPool2(nameSet, sentinelSet, poolConfig, timeout);
				if (pool == null) {
					logger.error("ShardedJedisSentinelPool init fail");
					System.out.println("ShardedJedisSentinelPool init fail");
				} else {
					logger.info("ShardedJedisSentinelPool init success:" + pool.toString());
					System.out.println("ShardedJedisSentinelPool init success:" + pool.toString());
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println(
					"[redis.properties]{" + PropertiesFileLoader.getClassPath() + "} 属性文件中属性配置错误!" + e.getMessage());
			logger.error("[redis.properties]{" + PropertiesFileLoader.getClassPath() + "} 属性文件中属性配置错误!" + e.getMessage(),
					e);
		} finally {
			logger.info(new StringBuilder().append("isCluster>>>").append(isCluster()).toString());
			System.out.println(new StringBuilder().append("isCluster>>>").append(isCluster()).toString());
		}
		return false;
	}

	synchronized protected final static void destroyPool() {
		try {
			if (isCluster()) {
				Cluster.close();
				System.out.println("Cluster.SimpleCluster.REDIS_CLUSTER destroy success");
				logger.info("Cluster.SimpleCluster.REDIS_CLUSTER destroy success");
			} else {
				if (pool != null) {
					pool.destroy();
					System.out.println("ShardedJedisSentinelPool destroy success");
					logger.info("ShardedJedisSentinelPool destroy success");
				}
			}
		} catch (Exception e) {
			if (isCluster()) {
				logger.error("Cluster.SimpleCluster.REDIS_CLUSTER destroy error:" + e.getMessage(), e);
			} else {
				logger.error("ShardedJedisSentinelPool destroy error:" + e.getMessage(), e);
			}
		} finally {
			pool = null;
		}
	}

	/**
	 * 判断是否是使用redis服务器端集群
	 * 
	 * @return
	 */
	public final static boolean isCluster() {
		return Cluster.isCluster();
	}

	/**
	 * 如果不是使用redis服务器端集群，则返回null
	 * 
	 * @return
	 */
	public final static RedisCluster loadCluster() {
		return Cluster.getInstance();
	}

	protected final static ShardedJedis loadRedis() throws Exception {
		return loadRedis(false);
	}

	/**
	 * 
	 * @param readonly
	 *            是否只读，暂不判断该参数，全部操作主库
	 * @return
	 */
	protected final static ShardedJedis loadRedis(boolean readonly) throws Exception{
		if (isCluster()) {
			throw new IllegalAccessException("redis cluster.请使用redis cluster集群命令！");
		}
		return pool.getResource();
	}

	protected final static void returnRedis(ShardedJedis redis) {
		if (redis == null) {
			logger.error("releaseRedis error:redis is null");
			return;
		}
		try {
			redis.close();
		} catch (Exception e) {
			logger.error("releaseRedis exception:" + e.getMessage(), e);
		}
	}
}
