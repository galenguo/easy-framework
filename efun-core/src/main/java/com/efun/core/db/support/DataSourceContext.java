package com.efun.core.db.support;

import com.efun.core.exception.EfunException;
import com.efun.core.db.annotation.DSType;
import com.efun.core.utils.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final ThreadLocal<LinkedList<DSType>> dataSourceHolder = new ThreadLocal<LinkedList<DSType>>();

    public static void setDataSourceKey(DSType dataSourceKey) {
        LinkedList<DSType> stack = dataSourceHolder.get();
        if (stack == null) {
            stack = new LinkedList<DSType>();
            dataSourceHolder.set(stack);
        }
        stack.addFirst(dataSourceKey);
    }

    public static DSType getDataSourceKey() {
        LinkedList<DSType> stack = dataSourceHolder.get();
        if (CollectionUtils.isEmpty(stack)) {
            return null;
        }
        return stack.getFirst();
    }

    public static void cleanDataSourceKey(DSType dataSourceKey) {
        LinkedList<DSType> stack = dataSourceHolder.get();
        DSType key = stack.getFirst();
        if (key.equals(dataSourceKey)) {
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
