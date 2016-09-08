package com.efun.core.db.support;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * RoutingDataSource
 * 路由数据源组件
 *
 * @author Galen
 * @since 2016/6/15
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDataSourceKey();
    }

}
