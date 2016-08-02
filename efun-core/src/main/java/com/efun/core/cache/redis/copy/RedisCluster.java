package com.efun.core.cache.redis.copy;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Set;

/**
 * redis3服务器端集群
 * 
 * @author Administrator
 *
 */
public class RedisCluster extends JedisCluster {

	public RedisCluster(Set<HostAndPort> nodes) {
		super(nodes);
	}

	public RedisCluster(Set<HostAndPort> nodes, int timeout) {
		super(nodes, timeout);
	}

	public RedisCluster(Set<HostAndPort> nodes, int timeout, int maxRedirections) {
		super(nodes, timeout, maxRedirections);
	}

	public RedisCluster(Set<HostAndPort> nodes, final GenericObjectPoolConfig poolConfig) {
		super(nodes, poolConfig);
	}

	public RedisCluster(Set<HostAndPort> nodes, int timeout, final GenericObjectPoolConfig poolConfig) {
		super(nodes, timeout, poolConfig);
	}

	public RedisCluster(Set<HostAndPort> redisClusterNode, int timeout, int maxRedirections,
			final GenericObjectPoolConfig poolConfig) {
		super(redisClusterNode, timeout, maxRedirections, poolConfig);
	}

	public RedisCluster(Set<HostAndPort> redisClusterNode, int connectionTimeout, int soTimeout, int maxRedirections,
			final GenericObjectPoolConfig poolConfig) {
		super(redisClusterNode, connectionTimeout, soTimeout, maxRedirections, poolConfig);
	}

	/**
	 * throw new IOException("RedisCluster Not Supported:close()");
	 */
	@Override
	public void close() throws IOException {
		throw new IOException("RedisCluster Not Supported:close()");
	}

	protected void destroy() throws IOException {
		super.close();
	}

}
