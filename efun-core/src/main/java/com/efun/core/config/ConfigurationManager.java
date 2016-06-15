package com.efun.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ConfigurationManager
 * 配置管理器
 *
 * @author Galen
 * @since 2016/5/30
 */
@Component
public class ConfigurationManager implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, ConfigurationLoader> matchBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                applicationContext, ConfigurationLoader.class, true, false);

        List<ConfigurationLoader> loaders = new ArrayList<ConfigurationLoader>();
        for (ConfigurationLoader loader : matchBeans.values()) {
            loaders.add(loader);
        }

        OrderComparator.sort(loaders);

        for (ConfigurationLoader loader : loaders) {
            loader.setProperties();
        }
    }
}
