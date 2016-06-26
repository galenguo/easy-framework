package com.efun.core.mapper.support;

import com.efun.core.context.Constants;
import com.efun.core.exception.EfunException;
import com.efun.core.mapper.AbstractSqlProvider;
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
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * MapperRegister
 * 通用mapper注册器
 *
 * @author Galen
 * @since 2016/6/26
 */
public class MapperRegister {

    private static final String BASE_MAPPER = "com.efun.core.mapper.BaseMapper";

    /**
     * 基础mapper扩展接口
     */
    private Map<Class<?>, AbstractSqlProvider> registerMapperMap = new LinkedHashMap<Class<?>, AbstractSqlProvider>();

    /**
     * 所有注册过的通用mapper接口，包括通用mapper接口父mapper的接口
     */
    private List<Class<?>> registerMpperList = new LinkedList<Class<?>>();

    private List<String> genericMappers = new ArrayList<String>();

    private Map<Class<?>, ResultMap> resultMapCache = new HashMap<Class<?>, ResultMap>();

    private Map<Class<?>, String> tableNameCache = new HashMap<Class<?>, String>();

    private Map<String, Class<?>> mapperEntityCache = new HashMap<String, Class<?>>();

    /**
     * <key>statementId
     * <value>sqlprovider
     */
    private Map<String, AbstractSqlProvider> statementCache = new LinkedHashMap<>();

    public boolean isExtendGenericMapper(Class<?> mapperInterface) {
        for (Class<?> mapperClass : registerMpperList) {
            if (mapperClass.isAssignableFrom(mapperInterface) && !mapperClass.getCanonicalName().equals(mapperInterface.getCanonicalName())) {
                return true;
            }
        }
        return false;
    }

    public void addGenericMapper(List<String> genericMappers) {
        if (!CollectionUtils.isEmpty(genericMappers)) {
            this.genericMappers.addAll(genericMappers);
        }
    }

    public void registerMapper() {
        if (genericMappers.size() == 0) {
            genericMappers.add(BASE_MAPPER);
        }
        for (String mapperClass : genericMappers) {
            try {
                registerMapper(Class.forName(mapperClass));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("register generic mapper[" + mapperClass + "]fail，can not find this mapper!");
            }
        }
    }

    public void registerMapper(Class<?> mapperClass) {
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
                throw new RuntimeException("a generic mapper should use one subclass of AbstractSqlProvider !");
            }
        }
        if (providerClass == null || !AbstractSqlProvider.class.isAssignableFrom(providerClass)) {
            providerClass = EmptyProvider.class;
        }
        AbstractSqlProvider sqlProvider = null;
        try {
            sqlProvider = (AbstractSqlProvider) providerClass.getConstructor(Class.class).newInstance(mapperClass);
        } catch (Exception e) {
            throw new RuntimeException("sqlProvider new instance error: " + e.getMessage());
        }
        //注册方法
        for (String methodName : methodSet) {
            try {
                sqlProvider.addMethod(methodName, providerClass.getMethod(methodName, MappedStatement.class));
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(providerClass.getCanonicalName() + "lack method:" + methodName + "!");
            }
        }
        sqlProvider.setRegister(this);
        return sqlProvider;
    }

    public void processConfiguration(Configuration configuration, Class<?> mapperInterface) {
        injectResultMap(configuration, mapperInterface);
        injectStatement(configuration, mapperInterface);
    }

    private void injectResultMap(Configuration configuration, Class<?> mapperInterface) {
        Type[] types = mapperInterface.getGenericInterfaces();
        Class<?> entityClass = (Class<?>) ((ParameterizedType)types[0]).getActualTypeArguments()[0];;
        String id = mapperInterface.getCanonicalName() + Constants.SEPARATOR_DOT + entityClass.getSimpleName();
        Table tableAnnotation = entityClass.getAnnotation(Table.class);
        if (tableAnnotation == null) {
            return;
        }
        String tableName = tableAnnotation.name();
        tableNameCache.put(entityClass, tableName);
        mapperEntityCache.put(mapperInterface.getCanonicalName(), entityClass);
        List<ResultMapping> resultMappingList = new ArrayList<ResultMapping>();
        String className = entityClass.getName();

        for (Field field : getDeclaredFields(entityClass)) {
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
                    javaType = (Class<?>) ((ParameterizedType) entityClass.getGenericSuperclass()).getActualTypeArguments()[0];
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
                columnName = field.getName();
            }
            JdbcType jdbcTypeEnum = resolveJdbcType(jdbcType);

            ResultMapping mapping = new ResultMapping.Builder(configuration, propertyName, columnName, javaType).jdbcType(jdbcTypeEnum).flags(flags).lazy(lazy).build();
            resultMappingList.add(mapping);
        }

        ResultMap resultMap = new ResultMap.Builder(configuration, id, entityClass, resultMappingList).build();
        if (!configuration.getResultMaps().contains(resultMap.getId())) {
            configuration.addResultMap(resultMap);
            resultMapCache.put(entityClass, configuration.getResultMap(resultMap.getId()));
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

    private void injectStatement(Configuration configuration, Class<?> mapperInterface) {
        String prefix;
        if (mapperInterface != null) {
            prefix = mapperInterface.getCanonicalName();
        } else {
            prefix = "";
        }
        for (Object object : new ArrayList<Object>(configuration.getMappedStatements())) {
            if (object instanceof MappedStatement) {
                MappedStatement mappedStatement = (MappedStatement) object;
                if (mappedStatement.getId().startsWith(prefix) && isMapperMethod(mappedStatement.getId())) {
                    if (mappedStatement.getSqlSource() instanceof ProviderSqlSource) {
                        setSqlSource(mappedStatement);
                    }
                }
            }
        }
    }

    private boolean isMapperMethod(String mappedStatementId) {
        AbstractSqlProvider provider = statementCache.get(mappedStatementId);
        if (provider instanceof EmptyProvider) {
            return false;
        } else if (provider != null){
            return true;
        } else {
            for (Map.Entry<Class<?>, AbstractSqlProvider> entry : registerMapperMap.entrySet()) {
                if (entry.getValue().supportMethod(mappedStatementId)) {
                    statementCache.put(mappedStatementId, entry.getValue());
                    return true;
                }
            }
            statementCache.put(mappedStatementId, new EmptyProvider(null));
            return false;
        }
    }

    private void setSqlSource(MappedStatement mappedStatement) {
        AbstractSqlProvider sqlProvider = statementCache.get(mappedStatement.getId());
        try {
            if (sqlProvider != null) {
                sqlProvider.setSqlSource(mappedStatement);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ResultMap getResultMapFromEntity(Class<?> entityClass) {
        return resultMapCache.get(entityClass);
    }

    public String getTableNameFromEntity(Class<?> entityClass) {
        return tableNameCache.get(entityClass);
    }

    public Class<?> getEntityClass(String mapperName) {
        return mapperEntityCache.get(mapperName);
    }
}
