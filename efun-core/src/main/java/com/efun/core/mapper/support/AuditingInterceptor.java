package com.efun.core.mapper.support;

import com.efun.core.context.ApplicationContext;
import com.efun.core.domain.EntityInterface;
import com.efun.core.mapper.annotation.CreatedDate;
import com.efun.core.mapper.annotation.LastModifiedDate;
import com.efun.core.mapper.annotation.LocalDate;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.*;

/**
 * AuditingInterceptor
 * 审计拦截器
 *
 * @author Galen
 * @since 2016/7/7
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class AuditingInterceptor implements Interceptor {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    private static final Map<Class<?>, Object> createdDateFieldMap = new HashMap<Class<?>, Object>();
    private static final Map<Class<?>, Object> lastModifiedDateFieldMap = new HashMap<Class<?>, Object>();
    private static final Map<Class<?>, Object> localDateFieldMap = new HashMap<Class<?>, Object>();

    private static final Map<String, Class<?>> statementClassMap = new HashMap<String, Class<?>>();

    @Override

    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        Object parameter = invocation.getArgs()[1];
        Class<?> clazz = statementClassMap.get(mappedStatement.getId());
        if (clazz == null) {
            if (parameter instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap) parameter;
                Set<Map.Entry<String, Object>> entrySet = paramMap.entrySet();
                for (Map.Entry<String, Object> entry : entrySet) {
                    if (entry.getKey().equals("entity")) {
                        parameter = paramMap.get("entity");
                        clazz = parameter.getClass();
                        break;
                    } else if (entry.getKey().equals("collection")) {
                        parameter = paramMap.get("collection");
                        Iterator<Object> iterator = ((Collection) parameter).iterator();
                        if (iterator.hasNext()) {
                            clazz = iterator.next().getClass();
                        } else {
                            logger.error("parameter collection can not be null");
                        }
                    }
                }
            } else {
                clazz = parameter.getClass();
            }

            statementClassMap.put(mappedStatement.getId(), clazz);
        }

        //非基础entit参数类型语句不处理
        if (!EntityInterface.class.isAssignableFrom(clazz)) {
            return invocation.proceed();
        }

        if (parameter instanceof Collection) {
            updatePre(sqlCommandType, (Collection<EntityInterface>) parameter, clazz);
        } else if (parameter instanceof EntityInterface) {
            updatePre(sqlCommandType, (EntityInterface) parameter, clazz);
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private void updatePre(SqlCommandType sqlCommandType, Collection<EntityInterface> collection, Class<?> clazz)
            throws Throwable {
        for (EntityInterface item : collection) {
            updatePre(sqlCommandType, item, clazz);
        }
    }

    private void updatePre(SqlCommandType sqlCommandType, EntityInterface parameter, Class<?> clazz) throws Throwable {
        Date currentDate = new Date();
        if (SqlCommandType.UPDATE == sqlCommandType) {
            Object object = lastModifiedDateFieldMap.get(clazz);
            if (!(object instanceof NoField)) {
                injectLastModifiedDate(parameter, clazz, (Field) object, currentDate);
            }

            object = localDateFieldMap.get(clazz);
            if (!(object instanceof NoField)) {
                injectLocalDate(parameter, clazz, (Field) object, ApplicationContext.getClientTime());
            }

        } else if (SqlCommandType.INSERT == sqlCommandType) {
            Object object = createdDateFieldMap.get(clazz);
            if (!(object instanceof NoField)) {
                injectCreatedDate(parameter, clazz, (Field) object, currentDate);
            }
            object = lastModifiedDateFieldMap.get(clazz);
            if (!(object instanceof NoField)) {
                injectLastModifiedDate(parameter, clazz, (Field) object, currentDate);
            }

            object = localDateFieldMap.get(clazz);
            if (!(object instanceof NoField)) {
                injectLocalDate(parameter, clazz, (Field) object, ApplicationContext.getClientTime());
            }
        }
    }

    private void injectLastModifiedDate(EntityInterface parameter, Class<?> clazz, Field field, Date currentDate) throws Throwable {
        if (field == null) {
            List<Field> fields = getDeclaredFields(clazz);
            for (Field item : fields) {
                if (item.getAnnotation(LastModifiedDate.class) != null) {
                    field = item;
                    lastModifiedDateFieldMap.put(clazz, field);
                    break;
                }
            }
        }
        if (field != null) {
            field.setAccessible(true);
            field.set(parameter, currentDate);
            field.setAccessible(false);
        } else {
            lastModifiedDateFieldMap.put(clazz, new NoField());
        }
    }

    private void injectLocalDate(EntityInterface parameter, Class<?> clazz, Field field, Date currentDate) throws Throwable {
        if (field == null) {
            List<Field> fields = getDeclaredFields(clazz);
            for (Field item : fields) {
                if (item.getAnnotation(LocalDate.class) != null) {
                    field = item;
                    localDateFieldMap.put(clazz, field);
                    break;
                }
            }
        }
        if (field != null) {
            field.setAccessible(true);
            field.set(parameter, currentDate);
            field.setAccessible(false);
        } else {
            localDateFieldMap.put(clazz, new NoField());
        }
    }

    private void injectCreatedDate(EntityInterface parameter, Class<?> clazz, Field field, Date currentDate) throws Throwable {
        if (field == null) {
            List<Field> fields = getDeclaredFields(clazz);
            for (Field item : fields) {
                if (item.getAnnotation(CreatedDate.class) != null) {
                    field = item;
                    createdDateFieldMap.put(clazz, field);
                    break;
                }
            }
        }
        if (field != null) {
            field.setAccessible(true);
            field.set(parameter, currentDate);
            field.setAccessible(false);
        } else {
            createdDateFieldMap.put(clazz, new NoField());
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

    private static class NoField {
    }

}