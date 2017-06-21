package com.efun.core.mapper.support;

import com.efun.core.mapper.BaseMapper;
import com.efun.core.utils.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.util.List;

/**
 * MapperScannerConfigurer
 *
 * @author Galen
 * @since 2016/6/26
 */
public class MapperScannerConfigurer extends org.mybatis.spring.mapper.MapperScannerConfigurer {

    public void setGenericMappers(List<String> genericMappers) {
        SqlSessionFactoryBean.getMapperRegistry().addGenericMapper(genericMappers);
    }

    @Override
    public void setMarkerInterface(Class<?> superClass) {
        super.setMarkerInterface(superClass);
        if (BaseMapper.class.isAssignableFrom(superClass)) {
            SqlSessionFactoryBean.getMapperRegistry().registerMapper(superClass);
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        super.postProcessBeanDefinitionRegistry(registry);
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)registry;
        String[] names = registry.getBeanDefinitionNames();
        GenericBeanDefinition definition;
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            if (beanDefinition instanceof GenericBeanDefinition) {
                definition = (GenericBeanDefinition) beanDefinition;
                if (StringUtils.isNotBlank(definition.getBeanClassName())
                        && definition.getBeanClassName().equals("org.mybatis.spring.mapper.MapperFactoryBean")) {
                    definition.setBeanClass(MapperFactoryBean.class);
                    definition.getPropertyValues().add("mapperRegistry", SqlSessionFactoryBean.getMapperRegistry());
                    //重新注册BeanDefination，清除factory中的缓存。
                    beanFactory.registerBeanDefinition(name, definition);
                }
            }
        }
    }

}
