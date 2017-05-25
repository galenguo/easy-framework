package com.efun.core.config;

import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * EnvironmentConfigurationLoader
 * <p>环境变量载入loader
 * @author Galen
 * @since 2016/5/30
 */
@Component
public class EnvironmentConfigurationLoader implements ConfigurationLoader {

    Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void setProperties() throws Exception {
        logger.info("loading properties from System Environment");
        Field[] fields = Configuration.class.getFields();
        if (fields != null) {
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isPublic(mod) && Modifier.isFinal(mod) && Modifier.isStatic(mod)) {
                    String key = field.get(null).toString();

                    String value = System.getenv(key);
                    if (StringUtils.isEmpty(value)) {
                        //导入默认配置
                        value = Configuration.getDefaultConfigValue(key);
                    }
                    if (StringUtils.isNotEmpty(value)) {
                        //导入配置
                        Configuration.putProperty(key, value);
                        logger.info("putProperty {}={}", key, value);
                    }
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
