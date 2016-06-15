package com.efun.core.mapper.support;

/**
 * DataSourceContext
 * 数据源上下文，用于设定和选择不同数据源的。
 *
 * @author Galen
 * @since 2016/6/15
 */
public class DataSourceContext {

    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();

    public static void setDataSourceKey(String dataSourceKey) {
        dataSourceHolder.set(dataSourceKey);
    }

    public static String getDataSourceKey() {
        return dataSourceHolder.get();
    }

    public static void cleanDataSourceKey() {
        dataSourceHolder.remove();
    }
}
