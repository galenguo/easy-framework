package com.efun.core.mapper.support;

import com.efun.core.exception.EfunException;
import com.efun.core.mapper.AbstractSqlProvider;
import com.efun.core.mapper.BaseMapper;
import com.efun.core.mapper.EmptyProvider;
import com.efun.core.mapper.annotation.Column;
import com.efun.core.mapper.annotation.Id;
import com.efun.core.mapper.annotation.Table;
import com.efun.core.utils.CollectionUtils;
import com.efun.core.utils.StringUtils;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultFlag;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
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

    private static final String BASE_MAPPER = "com.efun.core.mapper.BaseMapper";

    /**
     * class类， value表名
     */
    private static final Map<Class<?>, String> tableNameMap = new HashMap<Class<?>, String>();

    /**
     * mybatis的configuration，全局单例。
     */
    private static Configuration configuration;

    /**
     * 基础mapper扩展接口
     */
    private Map<Class<?>, AbstractSqlProvider> registerMapperMap = new LinkedHashMap<Class<?>, AbstractSqlProvider>();

    private List<Class<?>> registerMpperList = new LinkedList<Class<?>>();

    private List<String> genericMappers;

    public void setGenericMappers(List<String> genericMappers) {
        this.genericMappers = genericMappers;
    }

    /**
     * 根据实体类获取表名。
     *
     * @param entityClass
     * @return
     */
    public static String getTableNameFromEntity(Class<?> entityClass) {
        return tableNameMap.get(entityClass);
    }

    /**
     * 根据实体类获取ResultMap映射关系。
     *
     * @param entityClass
     * @return
     */
    public static ResultMap getResultMapFromEntity(Class<?> entityClass) {
        return configuration.getResultMap(entityClass.getName());
    }

    @Override
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException {
        SqlSessionFactory sqlSessionFactory = super.buildSqlSessionFactory();
        configuration = sqlSessionFactory.getConfiguration();
        //扫描Entity生成ResultMap
        scanResultMaps(configuration);
        //注入通用mapper，支持通用mapper接口可继承扩展
        injectMapper(configuration);
        return sqlSessionFactory;
    }

    /**
     * 扫描注解，增加默认ResultMap
     *
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
     *
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
        String className = clazz.getName();
        tableNameMap.put(clazz, tableName);

        for (Field field : getDeclaredFields(clazz)) {
            ArrayList<ResultFlag> flags = new ArrayList<ResultFlag>();
            String propertyName = field.getName();
            String columnName = null;
            String jdbcType = null;
            Class javaType = null;
            boolean lazy = configuration.isLazyLoadingEnabled();
            if (field.getAnnotation(Id.class) != null) {
                Id idAnnotation = field.getAnnotation(Id.class);
                columnName = idAnnotation.value();
                if (StringUtils.isBlank(columnName)) {
                    columnName = field.getName();
                }
                jdbcType = idAnnotation.jdbcType();
                javaType = field.getType();
                if (javaType.equals(Serializable.class)) {
                    javaType = (Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
                }
                flags.add(ResultFlag.ID);
            } else if (field.getAnnotation(Column.class) != null) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                columnName = columnAnnotation.value();
                if (StringUtils.isBlank(columnName)) {
                    columnName = field.getName();
                }
                jdbcType = columnAnnotation.jdbcType();
                javaType = field.getType();
            } else {
                continue;
            }
            if (StringUtils.isBlank(columnName)) {
                className = field.getName();
            }
            JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);

            ResultMapping mapping = new ResultMapping.Builder(configuration, propertyName, columnName, javaType).jdbcType(jdbcTypeEnum).flags(flags).lazy(lazy).build();
            resultMappingList.add(mapping);
        }

        ResultMap resultMap = new ResultMap.Builder(configuration, className, clazz, resultMappingList).build();
        if (!configuration.getResultMaps().contains(resultMap.getId())) {
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
        if (StringUtils.isBlank(alias)) {
            return null;
        } else {
            try {
                return JdbcType.valueOf(alias);
            } catch (IllegalArgumentException ex) {
                throw new EfunException("Error resolving JdbcType. Cause: " + ex, ex);
            }
        }
    }

    /**
     * 注入Mapper
     * @param configuration
     */
    private void injectMapper(Configuration configuration) {
        registerMapper();
        injectMappersToConfiguration(configuration);
    }

    /**
     * 注册通用Mapper接口
     */
    private void registerMapper() {
        if (genericMappers == null) {
            genericMappers = new ArrayList<String>();
        }
        genericMappers.add(BASE_MAPPER);
        for (String mapperClass : genericMappers) {
            try {
                registerMapper(Class.forName(mapperClass));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("注册通用Mapper[" + mapperClass + "]失败，找不到该通用Mapper!");
            }
        }
    }

    private void registerMapper(Class<?> mapperClass) {
        if (!registerMapperMap.containsKey(mapperClass)) {
            registerMpperList.add(mapperClass);
            registerMapperMap.put(mapperClass, getSqlProvider(mapperClass));
        }
        //递归注册Mapper接口
        Class<?>[] interfaces = mapperClass.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> anInterface : interfaces) {
                registerMapper(anInterface);
            }
        }
    }

    /**
     * 获取mapper对应的sqlprovider
     * @param mapperClass
     * @return
     */
    private AbstractSqlProvider getSqlProvider(Class<?> mapperClass) {
        Method[] methods = mapperClass.getDeclaredMethods();
        Class<?> providerClass = null;
        Class<?> tempClass = null;
        Set<String> methodSet = new HashSet<String>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SelectProvider.class)) {
                SelectProvider provider = method.getAnnotation(SelectProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            } else if (method.isAnnotationPresent(InsertProvider.class)) {
                InsertProvider provider = method.getAnnotation(InsertProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            } else if (method.isAnnotationPresent(DeleteProvider.class)) {
                DeleteProvider provider = method.getAnnotation(DeleteProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            } else if (method.isAnnotationPresent(UpdateProvider.class)) {
                UpdateProvider provider = method.getAnnotation(UpdateProvider.class);
                tempClass = provider.type();
                methodSet.add(method.getName());
            }
            if (providerClass == null) {
                providerClass = tempClass;
            } else if (providerClass != tempClass) {
                throw new RuntimeException("一个通用Mapper中只允许存在一个AbstractSqlProvider子类!");
            }
        }
        if (providerClass == null || !AbstractSqlProvider.class.isAssignableFrom(providerClass)) {
            providerClass = EmptyProvider.class;
        }
        AbstractSqlProvider sqlProvider = null;
        try {
            sqlProvider = (AbstractSqlProvider) providerClass.getConstructor(Class.class).newInstance(mapperClass);
        } catch (Exception e) {
            throw new RuntimeException("实例化sqlProvider对象失败:" + e.getMessage());
        }
        //注册方法
        for (String methodName : methodSet) {
            try {
                sqlProvider.addMethod(methodName, providerClass.getMethod(methodName, MappedStatement.class));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(providerClass.getCanonicalName() + "中缺少" + methodName + "方法!");
            }
        }
        return sqlProvider;
    }

    /**
     * 将通用mapper的方法注入到configuration
     * @param configuration
     */
    private void injectMappersToConfiguration(Configuration configuration) {
        for (Object object : new ArrayList<Object>(configuration.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement ms = (MappedStatement) object;
                if (isMapperMethod(ms.getId()) && ms.getSqlSource() instanceof ProviderSqlSource) {
                    setSqlSource(ms);
                }
            }
        }
    }

    public boolean isMapperMethod(String msId) {
        if (msIdSkip.get(msId) != null) {
            return msIdSkip.get(msId);
        }
        for (Map.Entry<Class<?>, AbstractSqlProvider> entry : registerMapperMap.entrySet()) {
            if (entry.getValue().supportMethod(msId)) {
                msIdSkip.put(msId, true);
                msIdCache.put(msId, entry.getValue());
                return true;
            }
        }
        msIdSkip.put(msId, false);
        return false;
    }

    public void setSqlSource(MappedStatement ms) {
        AbstractSqlProvider sqlProvider = msIdCache.get(ms.getId());
        try {
            if (mapperTemplate != null) {
                mapperTemplate.setSqlSource(ms);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
