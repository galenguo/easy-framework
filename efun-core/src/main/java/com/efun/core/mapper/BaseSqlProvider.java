package com.efun.core.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

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

    public String insertBatch(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        String columns = getColumns(entityClass);
        String values = getBatchValues(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("insert " + tableName);
        sql.append(" (" + columns + ")");
        sql.append(" values " + values);
        return sql.toString();
    }

    public String update(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        String idField = getId(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("update " + tableName);
        sql.append(getSets(entityClass));
        sql.append(" where " + idField + " = #{entity.id}");
        return sql.toString();
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

    public String count(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        String sql = new SQL() {{
            SELECT("count(*)");
            FROM(tableName);
        }}.toString();
        return sql;
    }

    public String countByQuery(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) ");
        sql.append(from(tableName));
        sql.append(whereByQuery());
        return sql.toString();
    }

    public String queryList(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectByQuery());
        sql.append(from(tableName));
        sql.append(whereByQuery());
        sql.append(groupByQuery());
        sql.append(orderByQuery());
        sql.append(limitByQuery());
        return sql.toString();
    }

    public String queryMapList(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, Map.class);
        String tableName = getTableName(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(selectByQuery());
        sql.append(from(tableName));
        sql.append(whereByQuery());
        sql.append(groupByQuery());
        sql.append(orderByQuery());
        sql.append(limitByQuery());
        return sql.toString();
    }

}
