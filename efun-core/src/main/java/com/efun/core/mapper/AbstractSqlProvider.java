package com.efun.core.mapper;

import com.efun.core.context.Constants;
import com.efun.core.mapper.support.SqlSessionFactoryBean;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * AbstractSqlProvider
 * 支持mapper接口扩展的抽象sqlprovider
 *
 * @author Galen
 * @since 2016/6/24
 */
public abstract class AbstractSqlProvider {

    protected static final Map<String, Class<?>> entityClassMap = new HashedMap<String, Class<?>>();

    protected Map<String, Method> methodMap = new LinkedMap<String, Method>();

    protected Class<?> mapperClass;

    public AbstractSqlProvider(Class<?> mapperClass) {
        this.mapperClass = mapperClass;
    }

    public Method getMethod(String methodName) {
        return methodMap.get(methodName);
    }

    public void addMethod(String methodName, Method method) {
        methodMap.put(methodName, method);
    }

    /**
     * 获取表名
     *
     * @param entityClass
     * @return
     */
    protected String getTableName(Class<?> entityClass) {
        return SqlSessionFactoryBean.getTableNameFromEntity(entityClass);
    }

    /**
     * 获取表id
     *
     * @param entityClass
     * @return
     */
    protected String getId(Class<?> entityClass) {
        return getEntityResultMap(entityClass).getIdResultMappings().get(0).getColumn();
    }

    /**
     * 获取表字段
     * <p>column0, column1, column2, ...</p>
     *
     * @param entityClass
     * @return
     */
    protected String getColumns(Class<?> entityClass) {
        String column = null;
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            column += mapping.getColumn() + Constants.SEPARATOR_COMMA;
        }
        return column.substring(0, column.length() - 1);
    }

    /**
     * 获取inser语句的字段占位符语句
     * <p>#{column0}, #{column1}, #{column2}, ...</p>
     *
     * @param entityClass
     * @return
     */
    protected String getValues(Class<?> entityClass) {
        String values = null;
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            values += "#{" + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA;
        }
        return values.substring(0, values.length() - 1);
    }

    /**
     * 获取update语句的字段占位符语句
     * <p>column0 = #{column0}, column1 = #{column1}, column2 = #{column2}, ...</p>
     *
     * @param entityClass
     * @return
     */
    protected String getSets(Class<?> entityClass) {
        String sets = null;
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            sets += mapping.getColumn() + " = #{" + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA;
        }
        return sets.substring(0, sets.length() - 1);
    }

    private ResultMap getEntityResultMap(Class<?> entityClass) {
        return SqlSessionFactoryBean.getResultMapFromEntity(entityClass);
    }

    public boolean supportMethod(String msId) {
        Class<?> mapperClass = getMapperClass(msId);
        if (mapperClass != null && this.mapperClass.isAssignableFrom(mapperClass)) {
            String methodName = getMethodName(msId);
            return methodMap.get(methodName) != null;
        }
        return false;
    }

    public void setSqlSource(MappedStatement ms) throws Exception {
        if (this.mapperClass == getMapperClass(ms.getId())) {
            throw new RuntimeException("请不要配置或扫描通用Mapper接口类：" + this.mapperClass);
        }
        Method method = methodMap.get(getMethodName(ms));
        try {
            //第一种，直接操作ms，不需要返回值
            if (method.getReturnType() == Void.TYPE) {
                method.invoke(this, ms);
            }
            //第二种，返回SqlNode
            else if (SqlNode.class.isAssignableFrom(method.getReturnType())) {
                SqlNode sqlNode = (SqlNode) method.invoke(this, ms);
                DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(ms.getConfiguration(), sqlNode);
                setSqlSource(ms, dynamicSqlSource);
            }
            //第三种，返回xml形式的sql字符串
            else if (String.class.equals(method.getReturnType())) {
                String xmlSql = (String) method.invoke(this, ms);
                SqlSource sqlSource = createSqlSource(ms, xmlSql);
                //替换原有的SqlSource
                setSqlSource(ms, sqlSource);
            } else {
                throw new RuntimeException("自定义Mapper方法返回类型错误,可选的返回类型为void,SqlNode,String三种!");
            }
            //cache
            checkCache(ms);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException() != null ? e.getTargetException() : e);
        }
    }
}
