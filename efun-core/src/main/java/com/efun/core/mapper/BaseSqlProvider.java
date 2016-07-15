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
        return new SQL() {{
            SELECT("count(*)");
            FROM(tableName);
            WHERE("");
        }}.toString();
    }

    public String query(MappedStatement mappedStatement) {
        Class<?> entityClass = getEntityClass(mappedStatement);
        setResultType(mappedStatement, entityClass);
        String tableName = getTableName(entityClass);
        StringBuilder sql = new StringBuilder();
        sql.append(select());
        sql.append(from(tableName));
        sql.append(where());
        sql.append(groupBy());
        sql.append(orderBy());
        sql.append(limit());
        return sql.toString();
    }

    private String select() {
        return "select" +
                "<if test=\"_parameter != null\">" +
                " ${query.fields} " +
                "</if>" +
                "<if test=\"_parameter == null\">" +
                " * " +
                "</if>";
    }

    private String from(String table) {
        return "from " + table + " ";
    }

    /**
     * 只能支持两层嵌套查询
     * @return
     */
    private String where() {
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

    private String groupBy() {
        return "<if test=\"_parameter != null\">" +
                "<if test=\"query.groupByClause != null\">" +
                "   group by ${query.groupByClause} " +
                "</if>" +
                "</if>";
    }

    private String orderBy() {
        return "<if test=\"_parameter != null\">" +
                "<if test=\"query.oderByClause != null\">" +
                "   order by ${query.oderByClause} " +
                "</if>" +
                "</if>";
    }

    private String limit() {
        return "<if test=\"_parameter != null\">" +
                "<if test=\"query.limit != null\">" +
                "   limit #{query.limit}, #{query.skip} " +
                "</if>" +
                "</if>";
    }

}
