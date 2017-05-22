package com.efun.core.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * properties file loader.
 * <p>properties配置文件载入loader
 *
 * @author Galen
 * @since 2016/2/25
 */
public class PropertyConfigurationLoader extends PropertyPlaceholderConfigurer implements ConfigurationLoader, ResourceLoaderAware, DisposableBean {

    protected Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 平台地区环境变量标识
     */
    protected final String EFUN_PLATFORM_REGION = "efunPlatformRegion";

    /**
     * loader加载顺序
     */
    protected int order = 1;

    private WatchService watcher = null;

    private ResourceLoader resourceLoader;

    private Resource[] locations = null;

    private int intervalSeconds = 30;

    private ScheduledExecutorService executor;

    private volatile boolean running = true;

    /**
     * 设置文件位置
     * @param baseLocation
     */
    public void setBaseLocation(Resource... baseLocation) {
        this.locations = baseLocation;
        super.setLocations(this.locations);

    }

    public void setIntervalSeconds(int intervalSeconds) {
        this.intervalSeconds = intervalSeconds;
    }

    /**
     * 创建资源
     *
     * @param baseLocation
     * @return
     */
    private Resource[] createResouces(String baseLocation) {
        Resource[] resources = new Resource[0];
        try {
            resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(baseLocation);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return resources;
    }

    @Override
    public void setProperties() throws Exception {
        if (locations != null && locations.length != 0) {
            for (Resource resource : locations) {
                loadProperties(resource);
            }
            //启动文件监听
            starterListener(this.locations);
        }
    }

    /**
     * 文件监听
     * @param resources
     * @throws Exception
     */
    private void starterListener(Resource[] resources) throws Exception {
        Map<String, Resource> resourceMap = new HashMap<String, Resource>();
        for (Resource resource : resources) {
            resourceMap.put(resource.getFilename(), resource);
        }
        watcher = FileSystems.getDefault().newWatchService();
        Path dir = Paths.get(resources[0].getFile().getParentFile().toURI());
        WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    WatchKey key = watcher.take();
                    for(WatchEvent<?> event : key.pollEvents()){
                        String fileName = event.context().toString();
                        String kind = event.kind().toString();
                        switch (kind) {
                            case "ENTRY_CREATE": {
                                String fileLocation = dir + File.separator + fileName;
                                Resource resource = new FileSystemResource(fileLocation);
                                resourceMap.put(fileName, resource);
                                loadProperties(resource);
                                break;
                            }
                            case "ENTRY_MODIFY": {
                                Resource resource = resourceMap.get(fileName);
                                if (resource != null) {
                                    loadProperties(resource);
                                }
                                break;
                            }
                        }
                    }
                    //重置key状态，实现连续地监控目录
                    if(!key.reset()){
                        return;
                    }
                } catch (Throwable throwable) {
                    logger.error(throwable.getMessage(), throwable);
                }

            }
        }, 0, intervalSeconds, TimeUnit.SECONDS);
    }

    private void loadProperties(Resource resource) throws Exception {
        logger.info("loading properties from {} ", resource.getFilename());
        LinkedProperties properties = new LinkedProperties();
        PropertiesLoaderUtils.fillProperties(properties, resource);
        if (null != properties) {
            for (Object item : properties.keySet()) {
                String key = (String) item;
                String value = (String) properties.get(key);
                //导入配置
                Configuration.putProperty((String) key, value);
                //导入log4j2上下文
                ThreadContext.put((String) key, value);
                //刷新log4j2配置
                if (((String) key).startsWith("log")) {
                    ((org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false)).reconfigure();
                }
                logger.info("putProperty {}={}", key, value);
            }
        } else {
            logger.error("Loading Error from file: {}", resource.getFilename());
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void destroy() throws Exception {
        this.executor.shutdown();
        while (this.executor.awaitTermination(1, TimeUnit.SECONDS)){

        }
        this.watcher.close();
    }

    static class LinkedProperties extends Properties {
        private final HashSet<Object> keys = new LinkedHashSet<Object>();

        public LinkedProperties() {
        }

        public Enumeration<Object> keys() {
            return Collections.<Object>enumeration(keys);
        }

        @Override
        public Set<Object> keySet() {
            return keys;
        }

        public Object put(Object key, Object value) {
            keys.add(key);
            return super.put(key, value);
        }
    }
}
