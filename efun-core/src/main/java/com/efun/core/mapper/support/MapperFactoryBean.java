package com.efun.core.mapper.support;

/**
 * MapperFactoryBean
 *
 * @author Galen
 * @since 2016/6/26
 */
public class MapperFactoryBean<T> extends org.mybatis.spring.mapper.MapperFactoryBean<T> {

    protected MapperRegister mapperRegister;

    public MapperFactoryBean() {
    }

    public MapperFactoryBean(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        //通用Mapper
        mapperRegister.registerMapper();
        if (mapperRegister.isExtendGenericMapper(getObjectType())) {
            mapperRegister.processConfiguration(getSqlSession().getConfiguration(), getObjectType());
        }
        System.out.println("****");
    }

    public void setMapperRegister(MapperRegister mapperRegister) {
        this.mapperRegister = mapperRegister;
    }
}
