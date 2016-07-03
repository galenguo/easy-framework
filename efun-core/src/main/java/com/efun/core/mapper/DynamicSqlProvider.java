package com.efun.core.mapper;

import com.efun.core.context.Constants;
import com.efun.core.mapper.query.Query;
import com.efun.core.mapper.support.SqlSessionFactoryBean;
import com.efun.core.utils.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * DynamicSqlProvider
 * 动态sql构建器
 *
 * @author Galen
 * @since 2016/6/22
 */
public class DynamicSqlProvider {

    protected final static String FINDBYID_METHOD = "findById";

    protected final static String INSERT_METHOD = "insert";

    protected final static String UPDATE_METHOD = "update";

    protected final static String DELETE_METHOD = "delete";

    /**
     * sql缓存
     */
    protected final static Map<String, String> sqlMap = new HashMap<String, String>();

    public String findById(@Param("entityClass")Class<?> entityClass) {

        String sql = sqlMap.get(entityClass.getName() + Constants.SEPARATOR_DOT + FINDBYID_METHOD);
        if (StringUtils.isBlank(sql)) {
            String tableName = SqlSessionFactoryBean.getTableNameFromEntity(entityClass);
            String idField = getId(SqlSessionFactoryBean.getResultMapFromEntity(entityClass));
            sql = new SQL(){{
                SELECT("*");
                FROM(tableName);
                WHERE(idField + "= #{id}");
            }}.toString();
        }
        return sql;
    }

    public String insert(@Param("entityClass")Class<?> entityClass) {
        String sql = sqlMap.get(entityClass.getName() + Constants.SEPARATOR_DOT + INSERT_METHOD);
        if (StringUtils.isBlank(sql)) {
            String tableName = SqlSessionFactoryBean.getTableNameFromEntity(entityClass);
            String columns = getColumns(SqlSessionFactoryBean.getResultMapFromEntity(entityClass));
            String values = getValues(SqlSessionFactoryBean.getResultMapFromEntity(entityClass));
            sql = new SQL(){{
                INSERT_INTO(tableName);
                VALUES(columns, values);
            }}.toString();
        }
        return sql;
    }

    public String update(@Param("entityClass")Class<?> entityClass) {
        String sql = sqlMap.get(entityClass.getName() + Constants.SEPARATOR_DOT + UPDATE_METHOD);
        if (StringUtils.isBlank(sql)) {
            String tableName = SqlSessionFactoryBean.getTableNameFromEntity(entityClass);
            String sets = getSets(SqlSessionFactoryBean.getResultMapFromEntity(entityClass));
            String idField = getId(SqlSessionFactoryBean.getResultMapFromEntity(entityClass));
            sql = new SQL(){{
                UPDATE(tableName);
                SET(sets);
                WHERE(idField + "= #{id}");
            }}.toString();
        }
        return sql;
    }

    public String delete(@Param("entityClass")Class<?> entityClass) {
        String sql = sqlMap.get(entityClass.getName() + Constants.SEPARATOR_DOT + DELETE_METHOD);
        if (StringUtils.isBlank(sql)) {
            String tableName = SqlSessionFactoryBean.getTableNameFromEntity(entityClass);
            String idField = getId(SqlSessionFactoryBean.getResultMapFromEntity(entityClass));
            sql = new SQL(){{
                DELETE_FROM(tableName);
                WHERE(idField + "= #{id}");
            }}.toString();
        }
        return sql;
    }

    public String query(@Param("query")Query query, @Param("entityClass")Class<?> entityClass) {
        String tableName = SqlSessionFactoryBean.getTableNameFromEntity(entityClass);
        return new SQL(){{
            SELECT("*");
            FROM(tableName);
            WHERE("");
        }}.toString();
    }

    private String getId(ResultMap resultMap) {
        return resultMap.getIdResultMappings().get(0).getColumn();
    }

    private String getColumns(ResultMap resultMap) {
        String column = null;
        for (ResultMapping mapping : resultMap.getResultMappings()) {
            column += mapping.getColumn() + Constants.SEPARATOR_COMMA;
        }
        return column.substring(0, column.length() -1);
    }

    private String getValues(ResultMap resultMap) {
        String values = null;
        for (ResultMapping mapping : resultMap.getResultMappings()) {
            values += "#{" + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA;
        }
        return values.substring(0, values.length() -1);
    }

    private String getSets(ResultMap resultMap) {
        String sets = null;
        for (ResultMapping mapping : resultMap.getResultMappings()) {
            sets += mapping.getColumn() + " = #{" + mapping.getProperty() + "}" + Constants.SEPARATOR_COMMA;
        }
        return sets.substring(0, sets.length() -1);
    }
}
