package com.efun.core.mapper.support;

import com.efun.core.exception.EfunException;
import com.efun.core.utils.CollectionUtils;
import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.LinkedList;

/**
 * DataSourceContext
 * 数据源上下文，用于设定和选择不同数据源的。
 *
 * @author Galen
 * @since 2016/6/15
 */
public class DataSourceContext {

    protected static final Logger logger = LogManager.getLogger(DataSourceContext.class);

    /**
     * 使用threadLocal保证线程安全，使用栈保证数据源嵌套情况下的查询。
     */
    private static final ThreadLocal<LinkedList<String>> dataSourceHolder = new ThreadLocal<LinkedList<String>>();

    public static void setDataSourceKey(String dataSourceKey) {
        LinkedList<String> stack = dataSourceHolder.get();
        if (stack == null) {
            stack = new LinkedList<String>();
            dataSourceHolder.set(stack);
        }
        stack.addFirst(dataSourceKey);
    }

    public static String getDataSourceKey() {
        LinkedList<String> stack = dataSourceHolder.get();
        if (CollectionUtils.isEmpty(stack)) {
            return null;
        }
        return stack.getFirst();
    }

    public static void cleanDataSourceKey(String dataSourceKey) {
        LinkedList<String> stack = dataSourceHolder.get();
        String key = stack.getFirst();
        if (StringUtils.equals(key, dataSourceKey)) {
            stack.removeFirst();
            if (stack.size() == 0) {
                dataSourceHolder.remove();
                logger.debug("#### delete stack from dataSource Context!");
            }
        } else {
            throw new EfunException("Error occurred at rounting dataSource!");
        }
    }
}
