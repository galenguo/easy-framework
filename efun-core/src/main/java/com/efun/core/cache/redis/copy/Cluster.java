package com.efun.core.cache.redis.copy;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;

import java.util.Set;

public final class Cluster {

	private static RedisCluster redisCluster;

	private Cluster() {
	}

	protected static final void initCluster(Set<HostAndPort> nodes) {
		synchronized (Cluster.class) {
			if (redisCluster == null) {
				redisCluster = new RedisCluster(nodes);
			}
		}
	}

	protected static final void initCluster(Set<HostAndPort> nodes, int timeout) {
		synchronized (Cluster.class) {
			if (redisCluster == null) {
				redisCluster = new RedisCluster(nodes, timeout);
			}
		}
	}

	protected static final void initCluster(Set<HostAndPort> nodes, int timeout, int maxRedirections) {
		synchronized (Cluster.class) {
			if (redisCluster == null) {
				redisCluster = new RedisCluster(nodes, timeout, maxRedirections);
			}
		}
	}

	protected static final void initCluster(Set<HostAndPort> nodes, final GenericObjectPoolConfig poolConfig) {
		synchronized (Cluster.class) {
			if (redisCluster == null) {
				redisCluster = new RedisCluster(nodes, poolConfig);
			}
		}
	}

	protected static final void initCluster(Set<HostAndPort> nodes, int timeout,
			final GenericObjectPoolConfig poolConfig) {
		synchronized (Cluster.class) {
			if (redisCluster == null) {
				redisCluster = new RedisCluster(nodes, timeout, poolConfig);
			}
		}
	}

	protected static final void initCluster(Set<HostAndPort> redisClusterNode, int timeout, int maxRedirections,
			final GenericObjectPoolConfig poolConfig) {
		synchronized (Cluster.class) {
			if (redisCluster == null) {
				redisCluster = new RedisCluster(redisClusterNode, timeout, maxRedirections, poolConfig);
			}
		}
	}

	protected static final void initCluster(Set<HostAndPort> redisClusterNode, int connectionTimeout, int soTimeout,
			int maxRedirections, final GenericObjectPoolConfig poolConfig) {
		synchronized (Cluster.class) {
			if (redisCluster == null) {
				redisCluster = new RedisCluster(redisClusterNode, connectionTimeout, soTimeout, maxRedirections,
						poolConfig);
			}
		}
	}

	protected static final RedisCluster getInstance() {
		return redisCluster;
	}

	protected static final boolean isCluster() {
		return redisCluster != null;
	}

	protected static final void close() throws Exception {
		if (isCluster()) {
			redisCluster.destroy();
		}
	}
}
