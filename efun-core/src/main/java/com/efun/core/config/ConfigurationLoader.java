package com.efun.core.config;

import org.springframework.core.Ordered;

/**
 * ConfigurationLoader
 * <p>配置载入接口
 *
 * @author Galen
 * @since 2016/5/30
 */
public interface ConfigurationLoader extends Ordered {

    public void setProperties() throws Exception;
}
