package com.efun.core.context;

import com.efun.core.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ApplicationInitializer
 * 容器上下文初始化组件（注入属性到environment）
 *
 * @author Galen
 * @since 2016/6/4
 */
public class ApplicationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        Set<String> keySet = Configuration.getPropertyKeys();
        Map<String, Object> map = new HashMap<String, Object>();
        for (String key : keySet) {
            map.put(key, Configuration.getProperty(key));
        }
        env.getPropertySources().addFirst(new MapPropertySource("sysEnv", map));
    }
}
