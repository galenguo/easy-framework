package com.efun.core.db.support;

import com.efun.core.db.annotation.DSType;
import com.efun.core.db.annotation.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.PatternMatchUtils;

import java.util.HashMap;
import java.util.Map;

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

    public void readMethodMapPut(String methodName, Boolean isForceChoiceRead) {
        readMethodMap.put(methodName, isForceChoiceRead);
    }

    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {

        Object object = null;
        DataSource dataSource = null;
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            dataSource = methodSignature.getMethod().getAnnotation(DataSource.class);
            if (dataSource == null) {
                Class<?> clz = methodSignature.getDeclaringType();
                dataSource = clz.getAnnotation(DataSource.class);
            }
            // TODO: 2016/9/7 可以缓存优化（考虑并发问题）
            //数据源注解方式优先
            if (dataSource != null) {
                DataSourceContext.setDataSourceKey(dataSource.value());
                logger.debug("### rounting to dataSource: " + dataSource.value());
            }
            //xml配置方式
            if (dataSource == null) {
                String methodName = methodSignature.getName();
                // TODO: 2016/9/8 待优化，仅支持读库。
                DSType dsType = getDataSourceType(methodName);
                DataSourceContext.setDataSourceKey(dsType);
            }

            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            if (dataSource != null) {
                DataSourceContext.cleanDataSourceKey(dataSource.value());
            }
        }
        return object;
    }

    /**
     * 根据方法名匹配选择读写库
     * @param methodName
     * @return
     */
    private DSType getDataSourceType(String methodName) {
        String bestNameMatch = null;
        for (String mappedName : this.readMethodMap.keySet()) {
            if (PatternMatchUtils.simpleMatch(methodName, mappedName)) {
                bestNameMatch = mappedName;
                break;
            }
        }

        // TODO: 2016/9/8 逻辑优化
        Boolean isForceChoiceRead = readMethodMap.get(bestNameMatch);
        //表示强制选择 读 库
        if(isForceChoiceRead == Boolean.TRUE) {
            return DSType.READ;
        }
        //如果之前选择了写库 现在还选择 写库
        if(DataSourceContext.getDataSourceKey().equals(DSType.WRITE)) {
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
