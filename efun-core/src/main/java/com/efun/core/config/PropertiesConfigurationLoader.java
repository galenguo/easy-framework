package com.efun.core.config;

import com.efun.core.utils.FileUtils;
import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.*;

/**
 * properties file loader.
 * <p>properties配置文件载入loader
 *
 * @author Galen
 * @since 2016/2/25
 */
public class PropertiesConfigurationLoader implements ConfigurationLoader {

    protected Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 使用系统环境变量配置路径
     */
    protected boolean useEvnConfigPath = false;

    /**
     * 默认配置文件名称
     */
    protected String defaultConfigFileName = "efun.properties";

    /**
     * 其余配置文件名称
     */
    protected String[] fileNames;

    protected int order = 1;

    public void setUseEvnConfigPath(boolean useEvnConfigPath) {
        this.useEvnConfigPath = useEvnConfigPath;
    }


    public void setDefaultConfigFileName(String defaultConfigFileName) {
        this.defaultConfigFileName = defaultConfigFileName;
    }

    public void setFileNames(String... fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    public void setProperties() throws Exception {
        List<String> fileNameList = new ArrayList<String>();
        fileNameList.add(defaultConfigFileName);
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (StringUtils.isNotBlank(fileName)) {
                    fileNameList.add(fileName);

                }
            }
        }

        /**
         * 区分使用绝对路径方式，还是classpath方式读取，默认是classpath方式读取配置文件。
         */
        String prePath = useEvnConfigPath ? FileUtils.addSeparatorIfNec(Configuration.getConfigPath()) : "";
        for (String fileName : fileNameList) {
            String resourceName =  prePath + fileName;
            logger.info("loading properties from {} ", resourceName);
            LinkedProperties properties = new LinkedProperties();
            Resource resource = useEvnConfigPath ? new FileSystemResource(resourceName) : new ClassPathResource(resourceName);

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
                logger.error("Loading Error from file: {}", resourceName);
            }
        }
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
