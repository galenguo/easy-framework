package com.efun.core.web.binding;

import com.efun.core.domain.BaseEntity;
import com.efun.core.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * RequestBeanArgumentResolver
 *
 * @author Galen
 * @since 2016/6/6
 */
public class ModelParamArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String BINDING_RESULT_LIST_NAME = "_bindingResultList_";

    private static final String DEFAULT_SEPARATOR = ".";

    private String separator = DEFAULT_SEPARATOR;

    public void setSeparator(String separator) {
        this.separator = null == separator ? DEFAULT_SEPARATOR : separator;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        ModelParam annotation = methodParameter.getMethodAnnotation(ModelParam.class);
        return annotation != null && StringUtils.isNoneBlank(annotation.value());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        ServletRequest servletRequest = (ServletRequest) nativeWebRequest.getNativeRequest();
        PropertyValues pvs = null;
        Object bindObject = null;
        String prefix = getPrefix(methodParameter);
        Class<?> paramType = methodParameter.getParameterType();

        if (Collection.class.isAssignableFrom(paramType) || paramType.isArray()) {
            Class<?> genericClass = null;
            if (paramType.isArray()) {
                genericClass = paramType.getComponentType();
            } else {
                Type type = methodParameter.getGenericParameterType();
                if (type instanceof ParameterizedType) {
                    genericClass = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
                }
            }

            if (genericClass != null) {
                Map<String, Object> mappedValues = createMappedValues(genericClass, nativeWebRequest, methodParameter, webDataBinderFactory, prefix);
                if (!mappedValues.isEmpty()) {
                    List<Object> targetObject = new ArrayList<Object>(mappedValues.values());
                    WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, targetObject, prefix);
                    bindObject = binder.convertIfNecessary(targetObject, paramType);
                }
            }
        } else if (Map.class.isAssignableFrom(paramType)) {
            Class<?> genericClass = null;
            Type type = methodParameter.getGenericParameterType();
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                genericClass = (Class<?>) pt.getActualTypeArguments()[1];
            }

            if (genericClass != null) {
                Map<String, Object> mappedValues = createMappedValues(genericClass, nativeWebRequest, methodParameter, webDataBinderFactory, prefix);
                if (!mappedValues.isEmpty()) {
                    Map<String, Object> targetObject = new HashMap<String, Object>();
                    for (Map.Entry<String, Object> entry : mappedValues.entrySet()) {
                        String key = entry.getKey();
                        key = key.substring(key.indexOf("['") + 2, key.indexOf("']"));
                        targetObject.put(key, entry.getValue());
                    }
                    WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, targetObject, prefix);
                    bindObject = binder.convertIfNecessary(targetObject, paramType);
                }
            }

        } else {
            pvs = new ServletRequestParameterPropertyValues(servletRequest, prefix, separator);

            bindObject = convertIfDomainClass(nativeWebRequest, pvs, paramType, webDataBinderFactory, prefix);

            if (null == bindObject) {
                bindObject = BeanUtils.instantiateClass(paramType);
            }

            WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, bindObject, prefix);

            // binder.initDirectFieldAccess();
            binder.bind(pvs);

            // 如果有校验注解@Valid，则校验绑定，通过Request传递校验结果
            if (isValid(methodParameter)) {
                binder.validate();
                BindingResult bindingResult = binder.getBindingResult();

                List<BindingResult> list = (List<BindingResult>) nativeWebRequest.getAttribute(BINDING_RESULT_LIST_NAME, 0);
                if (null == list) {
                    list = new ArrayList<BindingResult>();
                    nativeWebRequest.setAttribute(BINDING_RESULT_LIST_NAME, list, 0);
                }
                list.add(bindingResult);
            }
        }

        return bindObject;
    }

    private Map<String, Object> createMappedValues(Class<?> genericClass, NativeWebRequest nativeWebRequest,
                                                   MethodParameter methodParameter, WebDataBinderFactory webDataBinderFactory, String prefix) throws Exception {
        ServletRequest servletRequest = (ServletRequest) nativeWebRequest.getNativeRequest();
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        WebDataBinder binderHelper = webDataBinderFactory.createBinder(nativeWebRequest, null, null);

        // 将数组提取为一个一个的KEY，这里是集合必须要有prefix + '['
        Set<String> keySet = getSortedKeySet(servletRequest, prefix + '[');
        for (String key : keySet) {
            Object genericObj = null;
            if (key.endsWith(separator)) {
                ServletRequestParameterPropertyValues pvs = new ServletRequestParameterPropertyValues(servletRequest,
                        key, StringUtils.EMPTY);

                String realKey = key.substring(0, key.length() - 1);
                genericObj = convertIfDomainClass(nativeWebRequest, pvs, genericClass, webDataBinderFactory, realKey);
                if (null == genericObj) {
                    genericObj = BeanUtils.instantiateClass(genericClass);
                }
                WebDataBinder objectBinder = webDataBinderFactory.createBinder(nativeWebRequest, genericObj, realKey);
                objectBinder.bind(pvs);

                // 如果有校验注解@Valid，则校验绑定，通过Request传递校验结果
                if (isValid(methodParameter)) {
                    objectBinder.validate();
                    BindingResult bindingResult = objectBinder.getBindingResult();

                    List<BindingResult> list = (List<BindingResult>) nativeWebRequest.getAttribute(BINDING_RESULT_LIST_NAME,
                            0);
                    if (null == list) {
                        list = new ArrayList<BindingResult>();
                        nativeWebRequest.setAttribute(BINDING_RESULT_LIST_NAME, list, 0);
                    }
                    list.add(bindingResult);
                }
            } else {
                Map<String, Object> paramValues = WebUtils.getParametersStartingWith(servletRequest, key);
                if (!paramValues.isEmpty()) {
                    if (Collection.class.isAssignableFrom(genericClass)) {
                        genericObj = binderHelper.convertIfNecessary(paramValues.values(), genericClass);
                    } else {
                        genericObj = binderHelper.convertIfNecessary(paramValues.values().iterator().next(),
                                genericClass);
                    }
                }
            }
            if (genericObj != null) {
                resultMap.put(key, genericObj);
            }
        }
        return resultMap;
    }

    private Set<String> getSortedKeySet(ServletRequest request, String prefix) {
        Assert.notNull(request, "Request must not be null");
        Assert.notNull(prefix, "Prefix must not be null");
        Enumeration<String> paramNames = request.getParameterNames();
        Set<String> keySet = new TreeSet<String>(ComparatorImpl.INSTANCE);
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.startsWith(prefix)) {
                String key = paramName;
                int lastScopeIndex = paramName.indexOf(']');
                int firstSeparator = paramName.indexOf(separator);
                if (firstSeparator > lastScopeIndex) {
                    // 这里把separator也加上，用来判断是简单数据类型还是复杂类型
                    key = paramName.substring(0, firstSeparator + 1);
                }
                if (!keySet.contains(key)) {
                    keySet.add(key);
                }
            }
        }
        return keySet;
    }

    private static final class ComparatorImpl implements Comparator<String> {
        public static final ComparatorImpl INSTANCE = new ComparatorImpl();

        @Override
        public int compare(String left, String right) {
            int lengthCompare = left.length() - right.length();
            return lengthCompare != 0 ? lengthCompare : left.compareTo(right);
        }
    }

    //预留根据id和类型读取缓存或者读取数据库
    private Object convertIfDomainClass(NativeWebRequest nativeWebRequest, PropertyValues pvs, Class<?> paramType, WebDataBinderFactory webDataBinderFactory, String prefix) throws Exception  {
        // 如果参数是Domain Class，则看看是否有ID，有就根据ID读取数据
        if (BaseEntity.class.isAssignableFrom(paramType)) {
            PropertyValue idValue = pvs.getPropertyValue("id");
            PropertyValue versionNumberValue = pvs.getPropertyValue("versionNumber");
            if (null != idValue) {
                String idString = (String) idValue.getValue();
                if (StringUtils.isNotEmpty(idString)) {
                    WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, null, prefix + separator + "id");
                    Object obj =  binder.convertIfNecessary(idString, paramType);

                    //乐观锁，防止多次提交
                    /*if (null != versionNumberValue) {
                        if (obj instanceof VersionLocked) {
                            String versionNumberString = (String) versionNumberValue.getValue();
                            int versionNumber = new Integer(versionNumberString);
                            VersionLocked entity = (VersionLocked) obj;
                            if(versionNumber != entity.getVersionNumber()){
                                throw new ObjectOptimisticLockingFailureException(entity.getClass(), entity);
                            }
                        }
                    }*/

                    return obj;
                }
            }
        }
        return null;
    }

    private String getPrefix(MethodParameter methodParameter) {
        return methodParameter.getMethodAnnotation(ModelParam.class).value();
    }

    private boolean isValid(MethodParameter parameter) {
        for (Annotation annotation : parameter.getParameterAnnotations()) {
            if (annotation instanceof Valid) {
                return true;
            }
        }
        return false;
    }

}
