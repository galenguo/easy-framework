package com.efun.core.cache.redis;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;
import redis.clients.util.Sharded;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class ShardedJedisSentinelPool2 extends Pool<ShardedJedis> {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	protected Set<String> masterNames;

	protected Set<String> sentinels;

	protected GenericObjectPoolConfig poolConfig;

	protected int timeout = Protocol.DEFAULT_TIMEOUT;

	protected String password;

	protected int database = Protocol.DEFAULT_DATABASE;

	protected volatile Set<MasterHostAndPort> currentHostMasters;

	protected Set<ShardedMasterListener> ShardedMasterListeners = new HashSet<ShardedMasterListener>();

	private volatile ShardedMasterJedisFactory factory;

	private int sentinelRetry = 0;

	private static final int MAX_RETRY_SENTINEL = 10;

	public ShardedJedisSentinelPool2(Set<String> masterNames, Set<String> sentinels,
			final GenericObjectPoolConfig poolConfig) {
		this(masterNames, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
	}

	public ShardedJedisSentinelPool2(Set<String> masterNames, Set<String> sentinels) {
		this(masterNames, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, null,
				Protocol.DEFAULT_DATABASE);
	}

	public ShardedJedisSentinelPool2(Set<String> masterNames, Set<String> sentinels, String password) {
		this(masterNames, sentinels, new GenericObjectPoolConfig(), Protocol.DEFAULT_TIMEOUT, password);
	}

	public ShardedJedisSentinelPool2(Set<String> masterNames, Set<String> sentinels,
			final GenericObjectPoolConfig poolConfig, int timeout, final String password) {
		this(masterNames, sentinels, poolConfig, timeout, password, Protocol.DEFAULT_DATABASE);
	}

	public ShardedJedisSentinelPool2(Set<String> masterNames, Set<String> sentinels,
			final GenericObjectPoolConfig poolConfig, final int timeout) {
		this(masterNames, sentinels, poolConfig, timeout, null, Protocol.DEFAULT_DATABASE);
	}

	public ShardedJedisSentinelPool2(Set<String> masterNames, Set<String> sentinels,
			final GenericObjectPoolConfig poolConfig, final String password) {
		this(masterNames, sentinels, poolConfig, Protocol.DEFAULT_TIMEOUT, password);
	}

	/**
	 * Master-Slave must be use the same config
	 */
	public ShardedJedisSentinelPool2(Set<String> masterNames, Set<String> sentinels,
			final GenericObjectPoolConfig poolConfig, int timeout, final String password, final int database) {
		if (!(masterNames instanceof LinkedHashSet)) {
			throw new IllegalArgumentException("Parameter 'masterNames' must be typeof java.util.LinkedHashSet.");
		}
		this.masterNames = masterNames;
		this.sentinels = sentinels;
		this.poolConfig = poolConfig;
		this.timeout = timeout;
		this.password = password;
		this.database = database;

		initSentinelPool();
		initSentinelLiseners();
	}

	protected void initSentinelPool() {
		Set<MasterHostAndPort> HostAndPorts = sentinelGetMasters();
		initPool(HostAndPorts);
	}

	protected Set<MasterHostAndPort> sentinelGetMasters() {
		Set<MasterHostAndPort> hostAndPorts = new LinkedHashSet<MasterHostAndPort>();
		logger.info("Trying to find Sharded-Masters from available sentinels...");
		Set<String> tempMasterNames = new HashSet<String>();
		Set<String> tempSentinels = new HashSet<String>();

		for (String sentinel : sentinels) {
			sentinelRetry = 0;
			final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
			logger.info("Connecting to sentinel " + hap);
			do {
				sentinelRetry++;
				Jedis jedis = null;
				try {
					jedis = new Jedis(hap.getHost(), hap.getPort(), 2000);
					logger.info("Connected to sentinel " + hap);
					for (String masterName : masterNames) {
						logger.info("Connected to sentinel={},master={} ", hap, masterName);
						List<String> masterAddr = jedis.sentinelGetMasterAddrByName(masterName);
						List<Map<String, String>> slaveAddrs = jedis.sentinelSlaves(masterName);

						if (masterAddr == null || masterAddr.size() != 2) {
							logger.warn("Can not get master addr, master name: {}. Sentinel: {}.", masterName, hap);
							continue;
						} else {
							logger.info("get master addr, master name: {}. Sentinel: {}.", masterName, hap);
						}

						if (slaveAddrs == null || slaveAddrs.isEmpty()) {
							logger.warn("Can not get slave addr, master name: {}. Sentinel: {}.", masterName, hap);
							// continue;
						} else {
							logger.info("get slave addr, master name: {}. Sentinel: {}.", masterName, hap);
						}

						MasterHostAndPort master = new MasterHostAndPort(masterName, toHostAndPort(masterAddr));
						Set<HostAndPort> slaves = new LinkedHashSet<HostAndPort>();

						if (slaveAddrs != null) {
							for (Map<String, String> slave : slaveAddrs) {
								if ("slave".equals(slave.get("flags"))) { // is
																			// normal
																			// worked
																			// slave
																			// at
																			// now
									slaves.add(toHostAndPort(Arrays.asList(slave.get("ip"), slave.get("port"))));
								} else {
									logger.warn("slave={} is down",
											toHostAndPort(Arrays.asList(slave.get("ip"), slave.get("port"))));
								}
							}
						}

						logger.info("Found sharded master-slaves : master={},slaves={}", master, slaves);

						if (tempMasterNames.add(masterName)) {
							hostAndPorts.add(master);
							logger.info("new join.master name={},master={}", masterName, master);
						} else {
							logger.info("already join.master name={},master={}", masterName, master);
						}
					}
					tempSentinels.add(sentinel);
					break;
				} catch (Exception e) {
					logger.warn("Cannot connect to sentinel running @ {}. sleeping 1000ms, Will try again.time={}", hap,
							sentinelRetry);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
				} finally {
					if (jedis != null) {
						jedis.close();
					}
				}
			} while ((!tempSentinels.contains(sentinel)) && (sentinelRetry < MAX_RETRY_SENTINEL));
		}

		Set<String> tempSet = new HashSet<String>(sentinels);
		for (String temp : tempSentinels) {
			tempSet.remove(temp);
		}
		logger.info("src sentinels={}", sentinels);
		if (tempSet.size() == 0) {
			logger.info("all connect success:sentinels={}", tempSentinels);
		} else {
			logger.info("connect success:sentinels={}", tempSentinels);
			logger.warn("connect fail:sentinels={}", tempSet);
		}

		tempSet = new HashSet<String>(masterNames);
		for (String temp : tempMasterNames) {
			tempSet.remove(temp);
		}
		logger.info("src masterNames={}", masterNames);
		if (tempSet.size() == 0) {
			logger.info("all connect success:masterNames={}", masterNames);
		} else {
			logger.info("connect success:masterNames={}", tempMasterNames);
			logger.warn("connect fail:masterNames={}", tempSet);
		}

		return hostAndPorts;
	}

	protected void initSentinelLiseners() {
		if (currentHostMasters != null) {
			logger.info("Starting Sentinel listeners...");
			for (String sentinel : sentinels) {
				try {
					final HostAndPort hap = toHostAndPort(Arrays.asList(sentinel.split(":")));
					ShardedMasterListener ShardedMasterListener = new ShardedMasterListener(masterNames, hap.getHost(),
							hap.getPort());
					ShardedMasterListeners.add(ShardedMasterListener);
					ShardedMasterListener.start();
				} catch (Exception e) {
					logger.warn("Starting Sentinel listeners caught a exception: " + e.getMessage(), e);
				}
			}
		}
	}

	protected synchronized void initPool(Set<MasterHostAndPort> HostAndPorts) {
		if (HostAndPorts != null && !HostAndPorts.equals(currentHostMasters)) {
			currentHostMasters = HostAndPorts;

			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			for (MasterHostAndPort HostAndPort : HostAndPorts) {
				JedisShardInfo masterShard = toJedisShardInfo(HostAndPort.getMaster(), HostAndPort.getMasterName());
				shards.add(masterShard);
			}

			if (factory == null) {
				factory = new ShardedMasterJedisFactory(shards, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
				initPool(poolConfig, factory);
			} else {
				factory.setShards(shards);
				// although we clear the pool, we still have to check the
				// returned object
				// in getResource, this call only clears idle instances, not
				// borrowed instances
				internalPool.clear();
			}

			logger.info("Create Sharded-Masters jedis pool for {}", currentHostMasters);
		}
	}

	protected JedisShardInfo toJedisShardInfo(HostAndPort hostAndPort, String masterName) {
		JedisShardInfo shard = new JedisShardInfo(hostAndPort.getHost(), hostAndPort.getPort(), timeout, masterName);
		if (password != null && password.trim().length() > 0) {
			shard.setPassword(password);
		}
		return shard;
	}

	public ShardedJedis getResource() {
		try {
			ShardedJedis shardedJedis = internalPool.borrowObject();
			shardedJedis.setDataSource(this);
			return shardedJedis;
		} catch (Exception e) {
			throw new JedisConnectionException("Could not get a resource from the pool", e);
		}
	}

	public void returnResourceObject(final ShardedJedis resource) {
		try {
			internalPool.returnObject(resource);
		} catch (Exception e) {
			throw new JedisException("Could not return the resource to the pool", e);
		}
	}

	public void returnBrokenResource(final ShardedJedis resource) {
		returnBrokenResourceObject(resource);
	}

	public void returnResource(final ShardedJedis resource) {
		returnResourceObject(resource);
	}

	public void destroy() {
		for (ShardedMasterListener m : ShardedMasterListeners) {
			m.shutdown();
		}
		super.destroy();
	}

	public void close() {
		destroy();
	}

	public Set<MasterHostAndPort> getCurrentHostMasters() {
		return currentHostMasters;
	}

	public String toString() {
		//return currentHostMasters == null ? "NULL" : CommonUtil.objectConvertString(currentHostMasters.toArray());
		return currentHostMasters == null ? "NULL" : currentHostMasters.toArray().toString();
	}

	protected HostAndPort toHostAndPort(List<String> hostAndPort) {
		return new HostAndPort(hostAndPort.get(0), Integer.parseInt(hostAndPort.get(1)));
	}

	protected class MasterHostAndPort {
		private final String name;
		private final String host;
		private final int port;
		private final HostAndPort hap;

		public MasterHostAndPort(String name, String host, int port) {
			this.name = name;
			this.host = host;
			this.port = port;
			this.hap = new HostAndPort(host, port);
		}

		public MasterHostAndPort(String name, HostAndPort hap) {
			this.name = name;
			this.hap = hap;
			this.host = hap.getHost();
			this.port = hap.getPort();
		}

		public HostAndPort getMaster() {
			return hap;
		}

		public String getMasterName() {
			return name;
		}

		public String getMasterHost() {
			return host;
		}

		public int getMasterPort() {
			return port;
		}

		public int hashCode() {
			return 31 * (this.toString().hashCode());
		}

		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			} else if (obj instanceof MasterHostAndPort) {
				return obj.toString().equals(this.toString());
			} else {
				return false;
			}
		}

		public String toString() {
			return new StringBuilder().append("{").append(name).append(":").append(host).append(":").append(port)
					.append("}").toString();
		}
	}

	protected class ShardedMasterListener extends Thread {

		protected Set<String> masterNames;
		protected String host;
		protected int port;
		protected long subscribeRetryWaitTimeMillis = 5000;
		protected Jedis sentinelJedis;
		protected AtomicBoolean running = new AtomicBoolean(false);

		protected ShardedMasterListener() {
		}

		public ShardedMasterListener(Set<String> masterNames, String host, int port) {
			this.masterNames = masterNames;
			this.host = host;
			this.port = port;
		}

		public ShardedMasterListener(Set<String> masterNames, String host, int port,
				long subscribeRetryWaitTimeMillis) {
			this(masterNames, host, port);
			this.subscribeRetryWaitTimeMillis = subscribeRetryWaitTimeMillis;
		}

		public void run() {
			running.set(true);
			while (running.get()) {
				logger.debug(">>> Runing!");
				sentinelJedis = new Jedis(host, port);
				try {
					sentinelJedis.subscribe(new JedisPubSub() {
						public void onMessage(String channel, String message) {
							logger.info("Sentinel {} published: {} {}", host + ":" + port, channel, message);
							if ("+sdown".equals(channel)) { // has slave offline
								String[] messages = message.split(" ");
								if (messages.length == 8) {
									if ("slave".equals(messages[0])) {
										if (masterNames.contains(messages[5])) {
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.warn("Found unavailable redis slave[{}] for master[{}@{}]",
													slaveIp + ":" + slavePort, messages[5],
													masterIp + ":" + masterPort);
											// initSentinelPool();
										} else {
											logger.warn(
													"Ignoring message on +sdown for master name {}, but our master name is {}!",
													messages[5], masterNames);
										}
									} else {
										logger.warn("Invalid message received on Sentinel {} on channel +sdown: {}",
												host + ":" + port, message);
									}
								}
							}
							if ("-sdown".equals(channel)) { // has slave online
								String[] messages = message.split(" ");
								if (messages.length == 8) {
									if ("slave".equals(messages[0])) {
										if (masterNames.contains(messages[5])) {
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.warn("Found available redis slave[{}] for master[{}@{}]",
													slaveIp + ":" + slavePort, messages[5],
													masterIp + ":" + masterPort);
											// initSentinelPool();
										} else {
											logger.warn(
													"Ignoring message on -sdown for master name {}, but our master name is {}!",
													messages[5], masterNames);
										}
									} else {
										logger.warn("Invalid message received on Sentinel {} on channel -sdown: {}",
												host + ":" + port, message);
									}
								}
							}
							if ("+switch-master".equals(channel)) { // master
																	// has been
																	// switched
								String[] messages = message.split(" ");
								if (messages.length == 5) {
									if (masterNames.contains(messages[0])) {
										String oldMasterIp = messages[1];
										String oldMasterPort = messages[2];
										String newMasterIp = messages[3];
										String newMasterPort = messages[4];
										logger.warn("Switch master {} from [{}] to [{}]", messages[0],
												oldMasterIp + ":" + oldMasterPort, newMasterIp + ":" + newMasterPort);
										initSentinelPool();
									} else {
										logger.warn(
												"Ignoring message on +switch-master for master name {}, but our master name is {}!",
												messages[5], masterNames);
									}
								} else {
									logger.warn("Invalid message received on Sentinel {} on channel +switch-master: {}",
											host + ":" + port, message);
								}
							}
							if ("+slave".equals(channel)) { // has new slave
															// joined
								String[] messages = message.split(" ");
								if (messages.length == 8) {
									if ("slave".equals(messages[0])) {
										if (masterNames.contains(messages[5])) {
											String slaveIp = messages[2];
											String slavePort = messages[3];
											String masterIp = messages[6];
											String masterPort = messages[7];
											logger.warn("Found available redis slave[{}] for master[{}@{}]",
													slaveIp + ":" + slavePort, messages[5],
													masterIp + ":" + masterPort);
											// initSentinelPool();
										} else {
											logger.warn(
													"Ignoring message on +slave for master name {}, but our master name is {}!",
													messages[5], masterNames);
										}
									} else {
										logger.warn("Invalid message received on Sentinel {} on channel +slave: {}",
												host + ":" + port, message);
									}
								}
							}
						}
					}, "+switch-master", "+sdown", "-sdown", "+slave");
				} catch (JedisConnectionException e) {
					if (running.get()) {
						logger.warn("Lost connection to Sentinel at {}. Sleeping {}ms and retrying.", host + ":" + port,
								subscribeRetryWaitTimeMillis);
						try {
							Thread.sleep(subscribeRetryWaitTimeMillis);
						} catch (InterruptedException e1) {
						}
					} else {
						logger.warn("Listener stop running and unsubscribing from Sentinel at {}.", host + ":" + port);
					}
				} catch (Throwable th) {
				}
			}

			if (sentinelJedis != null) {
				try {
					sentinelJedis.quit();
				} catch (Exception e1) {
				}
				try {
					sentinelJedis.disconnect();
				} catch (Exception e) {
				} finally {
					sentinelJedis = null;
				}
			}
		}

		public void shutdown() {
			try {
				logger.info("Shutting down listener on {}.", host + ":" + port);
				running.set(false);
				// This isn't good, the Jedis object is not thread safe

				if (sentinelJedis != null) {
					try {
						sentinelJedis.quit();
					} catch (Exception e1) {
					}
					try {
						sentinelJedis.disconnect();
					} catch (Exception e) {
					} finally {
						sentinelJedis = null;
					}
				}
			} catch (Exception e) {
				logger.warn("Caught exception while shutting down: " + e.getMessage());
			}
		}
	}

	protected class ShardedMasterJedisFactory implements PooledObjectFactory<ShardedJedis> {
		private List<JedisShardInfo> shards;
		private Hashing algo;
		private Pattern keyTagPattern;

		public ShardedMasterJedisFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern) {
			this.shards = shards;
			this.algo = algo;
			this.keyTagPattern = keyTagPattern;
		}

		public void setShards(List<JedisShardInfo> shards) {
			this.shards = shards;
		}

		public PooledObject<ShardedJedis> makeObject() throws Exception {
			ShardedJedis shardedJedis = new ShardedJedis(shards, algo, keyTagPattern);
			return new DefaultPooledObject<ShardedJedis>(shardedJedis);
		}

		public void destroyObject(PooledObject<ShardedJedis> pooledShardedJedis) throws Exception {
			final ShardedJedis shardedJedis = pooledShardedJedis.getObject();
			for (Jedis jedis : shardedJedis.getAllShards()) {
				try {
					try {
						jedis.quit();
					} catch (Exception e) {

					}
					jedis.disconnect();
				} catch (Exception e) {

				}
			}
		}

		public boolean validateObject(PooledObject<ShardedJedis> pooledShardedJedis) {
			try {
				ShardedJedis jedis = pooledShardedJedis.getObject();
				for (Jedis shard : jedis.getAllShards()) {
					if (!shard.ping().equals("PONG")) {
						return false;
					}
				}
				return true;
			} catch (Exception ex) {
				return false;
			}
		}

		public void activateObject(PooledObject<ShardedJedis> p) throws Exception {

		}

		public void passivateObject(PooledObject<ShardedJedis> p) throws Exception {

		}
	}

}
