package com.efun.core.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * BaseSqlProvider
 * 支持baseMapper接口扩展的sqlProvider；
 *
 * @author Galen
 * @since 2016/6/22
 */
public class BaseSqlProvider extends AbstractSqlProvider {

    public BaseSqlProvider() {

    }

    public BaseSqlProvider(Class<?> mapperClass) {
        super(mapperClass);
    }

    public String findById(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        String idField = getId(entityClass);
        String sql = new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE(idField + "= #{id}");
        }}.toString();
        return sql;
    }

    public String insert(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        String columns = getColumns(entityClass);
        String values = getValues(entityClass);
        String sql = new SQL() {{
            INSERT_INTO(tableName);
            VALUES(columns, values);
        }}.toString();
        return sql;
    }

    public String update(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        String sets = getSets(entityClass);
        String idField = getId(entityClass);
        String sql = new SQL() {{
            UPDATE(tableName);
            SET(sets);
            WHERE(idField + "= #{id}");
        }}.toString();
        return sql;
    }

    public String delete(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        String idField = getId(entityClass);
        String sql = new SQL() {{
            DELETE_FROM(tableName);
            WHERE(idField + "= #{id}");
        }}.toString();
        return sql;
    }

    public String query(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        return new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE("");
        }}.toString();
    }

}
