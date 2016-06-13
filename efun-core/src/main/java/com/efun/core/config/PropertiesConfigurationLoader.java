package com.efun.core.config;

import com.efun.core.utils.CollectionUtils;
import com.efun.core.utils.FileUtils;
import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * properties file loader.
 * <p>properties配置文件载入loader
 *
 * @author Galen
 * @since 2016/2/25
 */
public class PropertiesConfigurationLoader implements ConfigurationLoader {

    protected Logger logger = LogManager.getLogger(this.getClass());

    protected static final String DEFAULT_CONFIG_NAME = "efun.properties";

    protected String[] fileNames;

    protected int order = 1;

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String... fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    public void setProperties() throws Exception {
        List<String> fileNameList = new ArrayList<String>();
        fileNameList.add(DEFAULT_CONFIG_NAME);
        if (fileNames != null) {
            for (String fileName : fileNames) {
                if (StringUtils.isNotBlank(fileName)) {
                    fileNameList.add(fileName);

                }
            }
        }

        for (String fileName : fileNameList) {
            String resourceName =  FileUtils.addSeparatorIfNec(Configuration.getConfigPath()) + fileName;
            logger.info("loading properties from {} ", resourceName);
            Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(resourceName));


            if (null != properties) {

                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    Object key = entry.getKey();
                    String value = (String) entry.getValue();
                    Configuration.putProperty((String) key, value);
                    ThreadContext.put((String) key, value);
                    logger.info("putProperty {}:{} to Configuration", key, value);
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
}
