package com.efun.core.mapper.support;

/**
 * MapperFactoryBean
 *
 * @author Galen
 * @since 2016/6/26
 */
public class MapperFactoryBean<T> extends org.mybatis.spring.mapper.MapperFactoryBean<T> {

    protected MapperRegistry mapperRegistry;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        //通用Mapper
        mapperRegistry.registerMapper();
        if (mapperRegistry.isExtendGenericMapper(getObjectType())) {
            mapperRegistry.processConfiguration(getSqlSession().getConfiguration(), getObjectType());
        }
    }

    public void setMapperRegistry(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }
}
