package com.efun.core.db.support;

import com.efun.core.context.Constants;
import com.efun.core.db.annotation.DSType;
import com.efun.core.db.annotation.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.PatternMatchUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DataSourceInterceptor
 * 数据源选择的拦截，应用于service和mapper层。
 *
 * @author Galen
 * @since 2016/6/15
 */
public class DataSourceInterceptor {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * xml配置方式，连接读库的方法的缓存
     */
    private Map<String, Boolean> readMethodMap = new HashMap<>();

    private Map<String, DSType> methodDSTypeMap = new ConcurrentHashMap<String, DSType>();

    public void readMethodMapPut(String methodName, Boolean isForceChoiceRead) {
        readMethodMap.put(methodName, isForceChoiceRead);
    }

    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        Object object = null;
        DSType dsType = null;
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String globalName = methodSignature.getDeclaringTypeName() + Constants.SEPARATOR_DOT + methodSignature.getName();
            dsType = methodDSTypeMap.get(globalName);
            if (dsType == null) {
                //方法注解
                DataSource dataSource = methodSignature.getMethod().getAnnotation(DataSource.class);
                if (dataSource == null) {
                    //类注解
                    Class<?> clz = methodSignature.getDeclaringType();
                    dataSource = clz.getAnnotation(DataSource.class);
                }
                //数据源注解方式优先
                if (dataSource != null) {
                    dsType = dataSource.value();
                }
                //xml配置方式
                if (dataSource == null) {
                    String methodName = methodSignature.getName();
                    dsType = getDataSourceType(methodName);

                }
                methodDSTypeMap.put(globalName, dsType);
            }
            logger.debug("rounting to dataSource: {}", dsType);
            DataSourceContext.setDataSourceKey(dsType);
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            DataSourceContext.cleanDataSourceKey(dsType);
        }
        return object;
    }

    /**
     * 根据方法名匹配选择读写库
     * @param methodName
     * @return
     */
    private DSType getDataSourceType(String methodName) {
        Boolean isForceChoiceRead = null;
        if (this.readMethodMap.size() > 0) {
            String bestNameMatch = null;
            for (String mappedName : this.readMethodMap.keySet()) {
                if (PatternMatchUtils.simpleMatch(methodName, mappedName)) {
                    bestNameMatch = mappedName;
                    break;
                }
            }
            isForceChoiceRead = readMethodMap.get(bestNameMatch);
        }


        //表示强制选择读库
        if(isForceChoiceRead == Boolean.TRUE) {
            return DSType.READ;
        }
        //如果之前选择了写库 现在还选择写库
        if(DataSourceContext.getDataSourceKey() == DSType.WRITE) {
            return DSType.WRITE;
        }

        //表示应该选择读库
        if(isForceChoiceRead == Boolean.FALSE) {
            return DSType.READ;
        }
        //默认选择默认库
        return DSType.DEFAULT;
    }

}
