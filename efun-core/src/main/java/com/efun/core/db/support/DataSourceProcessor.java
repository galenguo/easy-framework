package com.efun.core.db.support;

import com.efun.core.exception.EfunException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * DataSourceProcessor
 * <pre>
 * 支持xml配置数据库读写分离决策处理器
 * 在xml配置aop事务时使用
 * </pre>
 * @author Galen
 * @since 2016/9/4
 */
public class DataSourceProcessor implements BeanPostProcessor {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    private boolean forceChoiceReadWhenWrite = false;

    private DataSourceInterceptor dataSourceInterceptor;

    public void setForceChoiceReadWhenWrite(boolean forceChoiceReadWhenWrite) {
        this.forceChoiceReadWhenWrite = forceChoiceReadWhenWrite;
    }

    public void setDataSourceInterceptor(DataSourceInterceptor dataSourceInterceptor) {
        this.dataSourceInterceptor = dataSourceInterceptor;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof NameMatchTransactionAttributeSource)) {
            return bean;
        }

        try {
            NameMatchTransactionAttributeSource transactionAttributeSource = (NameMatchTransactionAttributeSource)bean;
            Field nameMapField = ReflectionUtils.findField(NameMatchTransactionAttributeSource.class, "nameMap");
            nameMapField.setAccessible(true);
            Map<String, TransactionAttribute> nameMap = (Map<String, TransactionAttribute>) nameMapField.get(transactionAttributeSource);

            for(Map.Entry<String, TransactionAttribute> entry : nameMap.entrySet()) {
                RuleBasedTransactionAttribute attr = (RuleBasedTransactionAttribute)entry.getValue();

                //仅对read-only的处理
                if(!attr.isReadOnly()) {
                    continue;
                }

                String methodName = entry.getKey();
                Boolean isForceChoiceRead = Boolean.FALSE;
                if(forceChoiceReadWhenWrite) {
                    //不管之前操作是写，默认强制从读库读 （设置为NOT_SUPPORTED即可）
                    //NOT_SUPPORTED会挂起之前的事务
                    attr.setPropagationBehavior(Propagation.NOT_SUPPORTED.value());
                    isForceChoiceRead = Boolean.TRUE;
                } else {
                    //否则 设置为SUPPORTS（这样可以参与到写事务）
                    attr.setPropagationBehavior(Propagation.SUPPORTS.value());
                }
                logger.debug("read/write transaction process  method:{} force read:{}", methodName, isForceChoiceRead);
                dataSourceInterceptor.readMethodMapPut(methodName, isForceChoiceRead);
            }

        } catch (Exception ex) {
            throw new EfunException("process read/write transaction error", ex);
        }

        return bean;
    }
}
