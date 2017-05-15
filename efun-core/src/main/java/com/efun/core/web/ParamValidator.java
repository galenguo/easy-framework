package com.efun.core.web;

import com.alibaba.fastjson.JSONObject;
import com.efun.core.context.ApplicationContext;
import com.efun.core.exception.EfunParamValidException;
import com.efun.core.utils.MD5Utils;
import com.efun.core.utils.StringUtils;

import java.util.*;

/**
 * ParamValidator
 * 参数验证
 *
 * @author Galen
 * @since 2017/5/6
 */
public final class ParamValidator {

    private JSONObject params;

    private ParamValidator(JSONObject params) {
        this.params = params;
    }

    public static ParamValidator creatValidator(JSONObject params) {
        return new ParamValidator(params);
    }

    /**
     * 参数为指定对象类型
     *
     * @param paramName
     * @param <T>
     * @return
     */
    public <T> T isObjectType(String paramName, Class<T> clazz) {
        try {
            return this.params.getObject(paramName, clazz);
        } catch (Exception e) {
            throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.AssertType.message","", paramName, clazz.getSimpleName()));
        }
    }

    /**
     * 参数为指定对象类型而且带有默认值
     * @param paramName
     * @param clazz
     * @param defaultValue
     * @param <T>
     * @return
     */
    public <T> T isObjectTypeWithDefaultValue(String paramName, Class<T> clazz, T defaultValue) {
        T result = isObjectType(paramName, clazz);
        if (String.class.equals(clazz) && StringUtils.isBlank((String) result)) {
            return defaultValue;
        } else if (result == null) {
            return defaultValue;
        }
        return result;
    }

    /**
     * 参数不为空字符串
     *
     * @param paramName
     * @return
     */
    public String isNotBlank(String paramName) {
        String result = isObjectType(paramName, String.class);
        if (StringUtils.isBlank(result)) {
            throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.NotBlank.message","", paramName));
        }
        return result;
    }

    /**
     * 参数长度范围
     * @param min
     * @param max
     * @return
     */
    public String length(String paramName, int min, int max) {
        String result = isObjectType(paramName, String.class);
        if (StringUtils.isBlank(result) && result.length() > min && result.length() <= max) {
            throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.length.message","", paramName, min, max));
        }
        return result;
    }

    /**
     * 参数不为null
     * @param paramName
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T isNotNull(String paramName, Class<T> clazz) {
        T result = isObjectType(paramName, clazz);
        if (result == null) {
            throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.NotNull.message","", paramName, clazz.getSimpleName()));
        }
        return result;
    }

    /**
     * 参数不为空对象，包括，字符串，集合，map
     *
     * @param paramName
     * @param <T>
     * @return
     */
    public <T> T isNotEmpty(String paramName, Class<T> clazz) {
        T result = isObjectType(paramName, clazz);
        if (result instanceof Collection) {
            if (((Collection) result).size() == 0) {
                throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.NotEmpty.message","", paramName));
            }
        } else if (result instanceof Map) {
            if (((Map) result).size() == 0) {
                throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.NotEmpty.message","", paramName));
            }
        } else if (result instanceof String) {
            if (StringUtils.isEmpty((String) result)) {
                throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.NotEmpty.message","", paramName));
            }
        }
        throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.NotSupport.message","", paramName, clazz.getSimpleName()));
    }

    /**
     * 参数为时间类型
     *
     * @param paramName
     * @return
     */
    public Date isDate(String paramName) {
        return isObjectType(paramName, Date.class);
    }

    /**
     * 参数为数字类型
     *
     * @param paramName
     * @return
     */
    public Number isNumber(String paramName) {
        return isObjectType(paramName, Number.class);
    }

    /**
     * 参数为整形
     *
     * @param paramName
     * @return
     */
    public Integer isInteger(String paramName) {
        return isObjectType(paramName, Integer.class);
    }

    /**
     * 参数为float
     *
     * @param paramName
     * @return
     */
    public Float isFloat(String paramName) {
        return isObjectType(paramName, Float.class);
    }

    /**
     * 参数为double
     *
     * @param paramName
     * @return
     */
    public Double isDouble(String paramName) {
        return isObjectType(paramName, Double.class);
    }

    public void validSignature() {
        String signature = (String)params.remove("signature");
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>(params);
        Iterator<Map.Entry<String, Object>> iterator = treeMap.entrySet().iterator();
        Map.Entry<String, Object> entry = null;
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            entry = iterator.next();
            builder.append(entry.getKey()).append(entry.getValue());
        }
        if (MD5Utils.MD5(builder.toString()).equals(signature)) {
            throw new EfunParamValidException(ApplicationContext.getMessage("validation.constraints.signature.error","", "signature"));
        }
    }

    /**
     * 检查表达式
     *
     * @param expression
     * @param message
     * @param MessageObject
     */
    public void checkState(boolean expression, String message, Object... MessageObject) {
        if (!expression) {
            new EfunParamValidException(ApplicationContext.getMessage(message,"", MessageObject));
        }
    }

}
