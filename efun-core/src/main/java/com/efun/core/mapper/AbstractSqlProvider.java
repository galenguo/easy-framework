package com.efun.core.mapper;

import com.efun.core.context.Constants;
import com.efun.core.domain.BaseEntity;
import com.efun.core.mapper.support.MapperRegistry;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * AbstractSqlProvider
 * 支持mapper接口扩展的抽象sqlprovider
 *
 * @author Galen
 * @since 2016/6/24
 */
public abstract class AbstractSqlProvider {

    protected static final XMLLanguageDriver languageDriver = new XMLLanguageDriver();

    protected MapperRegistry register;

    /**
     * 方法缓存
     * key:methodName value:Method
     */
    protected Map<String, Method> methodMap = new LinkedMap<String, Method>();

    /**
     * sqlProvider对应的mapper接口
     */
    protected Class<?> mapperClass;

    public AbstractSqlProvider() {

    }

    public AbstractSqlProvider(Class<?> mapperClass) {
        this.mapperClass = mapperClass;
    }

    /**
     * 获取方法
     *
     * @param methodName
     * @return
     */
    public Method getMethod(String methodName) {
        return methodMap.get(methodName);
    }

    /**
     * 增加方法
     *
     * @param methodName
     * @param method
     */
    public void addMethod(String methodName, Method method) {
        methodMap.put(methodName, method);
    }

    /**
     * provider是否提供mappedStatementId所指定的方法
     *
     * @param mappedStatementId
     * @return
     */
    public boolean supportMethod(String mappedStatementId) {
        Class<?> mapperClass = getMapperClass(mappedStatementId);
        if (mapperClass != null && this.mapperClass.isAssignableFrom(mapperClass)) {
            String methodName = getMethodName(mappedStatementId);
            return methodMap.get(methodName) != null;
        }
        return false;
    }

    /**
     * 设置statement的sql
     *
     * @param mappedStatement
     * @throws Exception
     */
    public void setSqlSource(MappedStatement mappedStatement) throws Exception {
        if (this.mapperClass == getMapperClass(mappedStatement.getId())) {
            throw new RuntimeException("please do not scan Generic Mapper interface：" + this.mapperClass);
        }
        Method method = methodMap.get(getMethodName(mappedStatement.getId()));
        try {
            //第一种，直接操作ms，不需要返回值
            if (method.getReturnType() == Void.TYPE) {
                method.invoke(this, mappedStatement);
            }
            //第二种，返回SqlNode
            else if (SqlNode.class.isAssignableFrom(method.getReturnType())) {
                SqlNode sqlNode = (SqlNode) method.invoke(this, mappedStatement);
                DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(mappedStatement.getConfiguration(), sqlNode);
                setSqlSource(mappedStatement, dynamicSqlSource);
            }
            //第三种，返回xml形式的sql字符串
            else if (String.class.equals(method.getReturnType())) {
                String xmlSql = (String) method.invoke(this, mappedStatement);
                SqlSource sqlSource = createSqlSource(mappedStatement, xmlSql);
                //替换原有的SqlSource
                setSqlSource(mappedStatement, sqlSource);
            } else {
                throw new RuntimeException("custom mapper method funcation return type error, return type just can be 'void' or 'SqlNode' or 'String' !");
            }
            //检查cache
            checkCache(mappedStatement);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException() != null ? e.getTargetException() : e);
        }
    }

    protected Class<?> getMapperClass(String mappedStatementId) {
        if (mappedStatementId.indexOf(".") == -1) {
            throw new RuntimeException("the mappedStatement id : " + mappedStatementId + " is not fit in with the rule of MappedStatement!");
        }
        String mapperClassStr = mappedStatementId.substring(0, mappedStatementId.lastIndexOf("."));
        try {
            return Class.forName(mapperClassStr);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    protected String getMethodName(String mappedStatementId) {
        return mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
    }

    /**
     * 重新设置SqlSource
     *
     * @param mappedStatement
     * @param sqlSource
     */
    protected void setSqlSource(MappedStatement mappedStatement, SqlSource sqlSource) {
        MetaObject metaObject = SystemMetaObject.forObject(mappedStatement);
        metaObject.setValue("sqlSource", sqlSource);
    }

    /**
     * 设置返回类型
     * @param mappedStatement
     * @param returnClass
     */
    protected void setResultType(MappedStatement mappedStatement, Class<?> returnClass) {
        MetaObject metaObject = SystemMetaObject.forObject(mappedStatement);
        if (BaseEntity.class.isAssignableFrom(returnClass)) {
            List<ResultMap> resultMaps = new ArrayList<ResultMap>();
            resultMaps.add(register.getResultMapFromEntity(returnClass));
            metaObject.setValue("resultMaps", Collections.unmodifiableList(resultMaps));
            metaObject.setValue("resultSetType", null);
        } else if (Map.class.isAssignableFrom(returnClass)) {
            metaObject.setValue("resultSetType", null);
        }
    }

    /**
     * 通过xmlSql创建sqlSource
     *
     * @param mappedStatement
     * @param xmlSql
     * @return
     */
    private SqlSource createSqlSource(MappedStatement mappedStatement, String xmlSql) {
        return languageDriver.createSqlSource(mappedStatement.getConfiguration(), "<script>\n\t" + xmlSql + "</script>", null);
    }

    /**
     * 检查是否配置过缓存
     *
     * @param mappedStatement
     * @throws Exception
     */
    private void checkCache(MappedStatement mappedStatement) throws Exception {
        if (mappedStatement.getCache() == null) {
            String nameSpace = mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf("."));
            Cache cache;
            try {
                //不存在的时候会抛出异常
                cache = mappedStatement.getConfiguration().getCache(nameSpace);
            } catch (IllegalArgumentException e) {
                return;
            }
            if (cache != null) {
                MetaObject metaObject = SystemMetaObject.forObject(mappedStatement);
                metaObject.setValue("cache", cache);
            }
        }
    }

    public void setRegister(MapperRegistry register) {
        this.register = register;
    }

    public Class<?> getEntityClass(MappedStatement mappedStatement) {
        return register.getEntityClass(mappedStatement.getId().substring(0, mappedStatement.getId().lastIndexOf(Constants.SEPARATOR_DOT)));
    }

    private ResultMap getEntityResultMap(Class<?> entityClass) {
        return register.getResultMapFromEntity(entityClass);
    }

    /**
     * 获取表名
     *
     * @param entityClass
     * @return
     */
    protected String getTableName(Class<?> entityClass) {
        return register.getTableNameFromEntity(entityClass);
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
        String column = "";
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            column += mapping.getColumn() + Constants.SEPARATOR_COMMA;
        }
        return column.substring(0, column.length() - 1);
    }

    /**
     * 获取insert语句的字段占位符语句
     * <p>#{column0}, #{column1}, #{column2}, ...</p>
     *
     * @param entityClass
     * @return
     */
    protected String getValues(Class<?> entityClass) {
        String values = "";
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            values += "#{" + "entity." + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA;
        }
        return values.substring(0, values.length() - 1);
    }

    /**
     * 获取insertBatch语句的字段占位符语句
     * <p>(#{column0}, #{column1}, #{column2}, ...), ...</p>
     * @param entityClass
     * @return
     */
    protected String getBatchValues(Class<?> entityClass) {
        String values = "";
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            values += "#{" + "item." + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA;
        }
        values = "<foreach collection=\"collection\" item=\"item\" index=\"index\" separator=\",\" >" +
                "(" + values.substring(0, values.length() - 1) + ")" +
                "</foreach>";
        return values;
    }

    /**
     * 获取update语句的字段占位符语句
     * <p>column0 = #{column0}, column1 = #{column1}, column2 = #{column2}, ...</p>
     *
     * @param entityClass
     * @return
     */
    protected String getSets(Class<?> entityClass) {
        String sets = "<trim prefix=\"SET\" suffixOverrides=\",\"><choose>";
        sets += "<when test=\"ignoreNull == false\">";
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            sets += mapping.getColumn() + " = #{" + "entity." + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA;
        }
        sets.substring(0, sets.length() - 1);
        sets += "</when>";
        sets += "<otherwise>";
        for (ResultMapping mapping : getEntityResultMap(entityClass).getResultMappings()) {
            if (mapping.getProperty() == "id") {
                continue;
            }
            sets += "<if test=\"entity." + mapping.getProperty() + " != null\">";
            sets += mapping.getColumn() + " = #{" + "entity." + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA + " ";
            sets += "</if>";
        }
        sets += "</otherwise>";
        sets += "</choose></trim>";
        return sets;
    }

    protected String selectByQuery() {
        return "select" +
                "<if test=\"_parameter != null\">" +
                " ${query.fields} " +
                "</if>" +
                "<if test=\"_parameter == null\">" +
                " * " +
                "</if>";
    }

    protected String from(String table) {
        return "from " + table + " ";
    }

    /**
     * 只能支持两层嵌套查询
     * @return
     */
    protected String whereByQuery() {
        return "<if test=\"_parameter != null\">" +
                "<if test=\"query.whereClause != null\">" +
                "   <where>" +
                "       <foreach collection=\"query.whereClause.criteriaChain\" item=\"criteria\">" +
                "       <choose>" +
                "           <when test=\"criteria.closed == false\">" +
                "               ${criteria.operator} ${criteria.key} " +
                "               <foreach collection=\"criteria.criteria\" index=\"condition\" item=\"value\">" +
                "                   <choose>" +
                "                       <when test='condition == \"in\"'>" +
                "                            ${condition} " +
                "                           <foreach collection=\"value\" item=\"valueItem\" open=\"(\" close=\")\" separator=\",\">" +
                "                               #{valueItem}" +
                "                           </foreach>" +
                "                       </when>" +
                "                       <otherwise> ${condition} #{value} </otherwise>" +
                "                   </choose>" +
                "               </foreach>" +
                "           </when>" +
                "           <otherwise>" +
                "               ${criteria.operator} " +
                "               <foreach collection=\"criteria.criteriaChain\" index=\"index\" item=\"subCriteria\" open=\"(\" close=\")\">" +
                "                   <if test=\"index != 0\">${subCriteria.operator}</if> ${subCriteria.key}" +
                "                   <foreach collection=\"subCriteria.criteria\" index=\"subCondition\" item=\"subValue\">" +
                "                       <choose>" +
                "                           <when test='subCondition == \"in\"'>" +
                "                                ${subCondition} " +
                "                               <foreach collection=\"subValue\" item=\"valueItem\" open=\"(\" close=\")\" separator=\",\">" +
                "                                   #{valueItem}" +
                "                               </foreach>" +
                "                           </when>" +
                "                           <otherwise> ${subCondition} #{subValue} </otherwise>" +
                "                       </choose>" +
                "                   </foreach>" +
                "               </foreach>" +
                "           </otherwise>" +
                "       </choose>" +
                "       </foreach>" +
                "   </where>" +
                "</if>" +
                "</if>";
    }

    protected String groupByQuery() {
        return "<if test=\"_parameter != null\">" +
                "<if test=\"query.groupByClause != null\">" +
                "   group by ${query.groupByClause} " +
                "</if>" +
                "</if>";
    }

    protected String orderByQuery() {
        return "<if test=\"_parameter != null\">" +
                "<if test=\"query.oderByClause != null\">" +
                "   order by ${query.oderByClause} " +
                "</if>" +
                "</if>";
    }

    protected String limitByQuery() {
        return "<if test=\"_parameter != null\">" +
                "<if test=\"query.limit != null\">" +
                "   limit #{query.limit}, #{query.skip} " +
                "</if>" +
                "</if>";
    }
}
