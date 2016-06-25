package com.efun.core.config;

/**
 * XmlConfigurationLoader
 * <p>xml配置文件载入loader（暂时预留）
 *
 * @author Galen
 * @since 2016/5/30
 */
public class XmlConfigurationLoader implements ConfigurationLoader {

    @Override
    public void setProperties() throws Exception {

    }

    @Override
    public int getOrder() {
        return 2;
    }
}
