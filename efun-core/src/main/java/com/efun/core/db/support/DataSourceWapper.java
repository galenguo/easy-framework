package com.efun.core.db.support;

import org.apache.logging.log4j.LogManager;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * DataSourceWapper
 * 多数据源外壳
 * <pre>
 *     存放多个数据源，轮询获取一个数据源
 * </pre>
 *
 * @author Galen
 * @since 2016/9/8
 */
public class DataSourceWapper implements DataSource {

    protected final org.apache.logging.log4j.Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 数据源列表
     */
    private DataSource[] dataSources;

    /**
     * 数据源数量
     */
    private int dataSourcesCount;

    /**
     * 计数器
     */
    private AtomicInteger counter = new AtomicInteger(1);

    public void setDataSources(Collection<DataSource> dataSources) {
        if (dataSources == null || dataSources.size() == 0) {
            throw new RuntimeException("DataSourceWapper can not be empty");
        }
        this.dataSourcesCount = dataSources.size();
        this.dataSources = dataSources.toArray(new DataSource[dataSourcesCount]);
    }

    public DataSourceWapper() {
    }

    private DataSource determineDataSource() {
        int index = counter.incrementAndGet() % dataSourcesCount;
        if (index < 0) {
            index = -index;
        }
        return dataSources[index];
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.determineDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.determineDataSource().getConnection(username, password);
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public void setLoginTimeout(int timeout) throws SQLException {
        throw new UnsupportedOperationException("setLoginTimeout");
    }

    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException("getLogWriter");
    }

    public void setLogWriter(PrintWriter pw) throws SQLException {
        throw new UnsupportedOperationException("setLogWriter");
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        } else {
            throw new SQLException("DataSource of type [" + this.getClass().getName() + "] cannot be unwrapped as [" + iface.getName() + "]");
        }
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this);
    }

    public Logger getParentLogger() {
        return Logger.getLogger("global");
    }

}
