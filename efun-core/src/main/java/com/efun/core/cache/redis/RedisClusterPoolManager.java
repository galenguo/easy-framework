package com.efun.core.cache.redis;

import com.efun.core.cache.redis.copy.Cluster;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.*;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * RedisClusterPoolManager
 * redis集群连接池管理器
 * 支持客户端一致性哈希分布式、支持服务端分布式
 *
 * @author Galen
 * @since 2016/7/29
 */
public class RedisClusterPoolManager implements InitializingBean, DisposableBean {

    private final Logger logger = LogManager.getLogger(this.getClass());

    //客户端集群
    private static RedisClientCluster clientCluster;

    //服务端集群
    private static RedisServerCluster serverCluster;

    private static boolean isServerCluster;

    //服务节点名称，多个以英文逗号隔开,请勿重复
    private String serverNames;

    //哨兵节点，多个以英文逗号隔开,请勿重复
    private String sentinels;

    //最大能够保持idel状态的对象数
    private int maxIdle;

    //最大分配的对象数
    private int maxTotal;

    //当池内没有返回对象时，最大等待时间
    private long maxWaitMillis;

    //当池内没有返回对象时，最大等待时间
    private int timeout;

    //当调用borrow Object方法时，是否进行有效性检查
    private boolean testOnBorrow;

    //当调用return Object方法时，是否进行有效性检查
    private boolean testOnReturn;

    //多个以英文逗号隔开,请勿重复
    private String clusterNodes;

    public void setServerNames(String serverNames) {
        this.serverNames = serverNames;
    }

    public void setSentinels(String sentinels) {
        this.sentinels = sentinels;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    public void initPool() {
        //检查应用系统字符集是否为UTF-8
        if (Charset.isSupported("UTF-8")) {
            logger.info("UTF-8 Charset SupportedEncoding");
        } else {
            logger.error("UTF-8 Charset UnsupportedEncoding");
            return;
        }

        //初始化redis连接池
        try {
            JedisPoolConfig poolConfig = createPoolConfig();

            //服务端分布式
            if (StringUtils.isNotBlank(clusterNodes)) {
                Set<HostAndPort> nodes = new HashSet<HostAndPort>();
                for (String node : clusterNodes.split("\\,|\\||\\;")) {
                    String[] hostAndPort = node.trim().split("\\:");
                    nodes.add(new HostAndPort(hostAndPort[0].trim(), Integer.parseInt(hostAndPort[1].trim())));
                }
                JedisCluster cluster = new JedisCluster(nodes, timeout, poolConfig);
                logger.info("cluster.nodes(config) >>> " + clusterNodes);

                JedisPool tempPool=cluster.getClusterNodes().values().iterator().next();
                Jedis jedis=tempPool.getResource();
                jedis.close();
                logger.info("cluster.nodes >>> " + cluster.getClusterNodes().keySet() + " message:\n" + jedis.clusterNodes());
                //jedis集群对象注入服务端集群对象
                serverCluster = new RedisServerCluster(cluster);
                isServerCluster = true;

            //客服端分布式
            } else if (StringUtils.isNotBlank(sentinels)) {
                Set<String> serverNameSet = new LinkedHashSet<String>();
                Set<String> sentinelSet = new LinkedHashSet<String>();

                if (StringUtils.isNotBlank(serverNames)) {
                    String[] names = serverNames.split("\\||\\,");
                    for (String item : names) {
                        if (StringUtils.isNotBlank(item)) {
                            serverNameSet.add(item.trim());
                        }
                    }
                }
                if (StringUtils.isNotBlank(sentinels)) {
                    String[] sentinelArray = sentinels.split("\\||\\,");
                    for (String item : sentinelArray) {
                        if (StringUtils.isNotBlank(item)) {
                            sentinelSet.add(item.trim());
                        }
                    }
                }

                logger.info("redis.serverNames >>> " + serverNames);
                logger.info("redis.sentinels >>> " + sentinels);
                ShardedJedisSentinelPool pool = new ShardedJedisSentinelPool(serverNameSet, sentinelSet, poolConfig, timeout);
                if (pool == null) {
                    logger.error("ShardedJedisSentinelPool init fail");
                    throw new RuntimeException();
                } else {
                    logger.info("ShardedJedisSentinelPool init success:" + pool.toString());
                }

                //连接池注入客户端集群对象
                clientCluster = new RedisClientCluster(pool);

            } else {
                throw new RuntimeException();
            }
        } catch (Exception ex) {
            logger.error("RedisClusterPoolManager配置错误!" + ex.getMessage(), ex);
            throw ex;
        } finally {
            logger.info("isServerCluster >>> " + isServerCluster);
        }
    }

    private JedisPoolConfig createPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setMaxTotal(maxTotal);
        return config;
    }

    public void destroyPool() {
        try {
            if (isServerCluster) {
                serverCluster.destroy();
                logger.info("Cluster.SimpleCluster.REDIS_CLUSTER destroy success");
            } else {
                if (clientCluster != null) {
                    clientCluster.destroy();
                    logger.info("ShardedJedisSentinelPool destroy success");
                }
            }
        } catch (Exception e) {
            if (isServerCluster) {
                logger.error("Cluster.SimpleCluster.REDIS_CLUSTER destroy error:" + e.getMessage(), e);
            } else {
                logger.error("ShardedJedisSentinelPool destroy error:" + e.getMessage(), e);
            }
        }
    }

    public final static CommonRedisCommands getInstance() {
        if (isServerCluster) {
            return serverCluster;
        } else {
            return clientCluster;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initPool();
    }

    @Override
    public void destroy() {
        destroyPool();
    }

}
