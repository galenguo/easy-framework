package com.efun.core.mapper.support;

import com.efun.core.exception.EfunException;
import com.efun.core.utils.StringUtils;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * SqlSessionFactoryBean
 *
 * @author Galen
 * @since 2016/6/20
 */
public class SqlSessionFactoryBean extends org.mybatis.spring.SqlSessionFactoryBean {

    /**
     * mybatis默认的类型
     */
    private final String[] default_type = {"string", "byte", "long", "short", "int", "integer", "double", "float",
            "boolean", "byte[]", "long[]", "short[]", "int[]", "integer[]", "double[]", "float[]", "boolean[]",
            "_byte", "_long", "_short", "_int", "_integer", "_double", "_float", "_boolean", "_byte[]", "_long[]",
            "_short[]", "_int[]", "_integer[]", "_double[]", "_float[]", "_boolean[]", "date", "decimal", "bigdecimal",
            "biginteger", "object", "date[]", "decimal[]", "bigdecimal[]", "biginteger[]", "object[]", "map", "hashmap",
            "list", "arraylist", "collection", "iterator", "ResultSet"};

    /**
     * key类名， value表名
     */
    private final Map<String, String> tableNameMap = new HashMap<String, String>();

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
        Configuration configuration = sqlSessionFactory.getConfiguration();
        scanResultMaps(configuration);
        return sqlSessionFactory;
    }

    /**
     * 扫描注解，增加默认ResultMap
     * @param configuration
     */
    private void scanResultMaps(Configuration configuration) {
        Map<String, Class<?>> typeAliasesMap = configuration.getTypeAliasRegistry().getTypeAliases();
        for (Map.Entry<String, Class<?>> item : typeAliasesMap.entrySet()) {
            if (!isDefaultType(item.getKey())) {
                resolveResultMapFromClass(configuration, item.getValue());
            }
        }
    }

    /**
     * 扫描一个类生成一个ResultMap注入到configuration
     * @param configuration
     * @param clazz
     */
    private void resolveResultMapFromClass(Configuration configuration, Class<?> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            return;
        }
        String tableName = tableAnnotation.name();
        List<ResultMapping> resultMappingList = new ArrayList<ResultMapping>();
        String className = clazz.getSimpleName().toLowerCase();
        tableNameMap.put(className, tableName);

        for (Field field : getDeclaredFields(clazz)) {
            ArrayList<ResultFlag> flags = new ArrayList<ResultFlag>();
            String propertyName = field.getName();
            String columnName = null;
            String jdbcType = null;
            boolean lazy = configuration.isLazyLoadingEnabled();
            if (field.getAnnotation(Id.class) != null) {
                Id idAnnotation = field.getAnnotation(Id.class);
                columnName = idAnnotation.value();
                jdbcType = idAnnotation.jdbcType();
                flags.add(ResultFlag.ID);
            } else if (field.getAnnotation(Column.class) != null) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                columnName = columnAnnotation.value();
                jdbcType = columnAnnotation.jdbcType();
            } else {
                continue;
            }
            if (StringUtils.isBlank(columnName)) {
                className = field.getName();
            }
            JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);

            ResultMapping mapping = new ResultMapping.Builder(configuration, propertyName, columnName, field.getType()).jdbcType(jdbcTypeEnum).flags(flags).lazy(lazy).build();
            resultMappingList.add(mapping);
        }

        ResultMap resultMap = new ResultMap.Builder(configuration, className, clazz, resultMappingList).build();
        if (configuration.getResultMaps().contains(resultMap.getId())) {
            configuration.addResultMap(resultMap);
        }
    }

    private List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<Field>();
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            fields.addAll(getDeclaredFields(superclass));
        }
        Collections.addAll(fields, clazz.getDeclaredFields());
        return fields;
    }

    private boolean isDefaultType(String key) {
        for (String item : default_type) {
            if (StringUtils.equals(key, item)) {
                return true;
            }
        }
        return false;
    }

    private JdbcType resolveJdbcType(String alias) {
        if(StringUtils.isBlank(alias)) {
            return null;
        } else {
            try {
                return JdbcType.valueOf(alias);
            } catch (IllegalArgumentException ex) {
                throw new EfunException("Error resolving JdbcType. Cause: " + ex, ex);
            }
        }
    }

}
