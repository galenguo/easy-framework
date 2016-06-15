package com.efun.core.mapper.support;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * RoutingDataSource
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
