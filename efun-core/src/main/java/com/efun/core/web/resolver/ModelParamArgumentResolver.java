package com.efun.core.web.resolver;

import com.efun.core.domain.page.Pageable;
import com.efun.core.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * RequestBeanArgumentResolver
 *
 * @author Galen
 * @since 2016/6/6
 */
public class ModelParamArgumentResolver extends BaseMethodArgumentResolver {

    private WebBindingInitializer webBindingInitializer;

    public ModelParamArgumentResolver(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ModelParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String name = methodParameter.getParameterAnnotation(ModelParam.class).value();
        Object target = modelAndViewContainer.containsAttribute(name) ? modelAndViewContainer.getModel().get(name) : createAttribute(name, methodParameter, webDataBinderFactory, nativeWebRequest);
        return null;
    }

    private ModelParam getRequestBeanAnnotation(MethodParameter methodParameter) {
        for (Annotation annotation : methodParameter.getParameterAnnotations()) {
            if (annotation instanceof ModelParam) {
                return (ModelParam) annotation;
            }
        }
        return null;
    }

    protected Object createAttribute(String attributeName, MethodParameter parameter,
                                     WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

        String value = getRequestValueForAttribute(attributeName, request);

        if (value != null) {
            Object attribute = createAttributeFromRequestValue(value, attributeName, parameter, binderFactory, request);
            if (attribute != null) {
                return attribute;
            }
        }
        Class<?> parameterType = parameter.getParameterType();
        if (parameterType.isArray() || List.class.isAssignableFrom(parameterType)) {
            return ArrayList.class.newInstance();
        }
        if (Set.class.isAssignableFrom(parameterType)) {
            return HashSet.class.newInstance();
        }

        if (MapWapper.class.isAssignableFrom(parameterType)) {
            return MapWapper.class.newInstance();
        }

        return BeanUtils.instantiateClass(parameter.getParameterType());
    }

    protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
        Map<String, String> variables = getUriTemplateVariables(request);
        if (org.springframework.util.StringUtils.hasText(variables.get(attributeName))) {
            return variables.get(attributeName);
        } else if (org.springframework.util.StringUtils.hasText(request.getParameter(attributeName))) {
            return request.getParameter(attributeName);
        } else {
            return null;
        }
    }

    protected Object createAttributeFromRequestValue(String sourceValue,
                                                     String attributeName,
                                                     MethodParameter parameter,
                                                     WebDataBinderFactory binderFactory,
                                                     NativeWebRequest request) throws Exception {
        DataBinder binder = binderFactory.createBinder(request, null, attributeName);
        ConversionService conversionService = binder.getConversionService();
        if (conversionService != null) {
            TypeDescriptor source = TypeDescriptor.valueOf(String.class);
            TypeDescriptor target = new TypeDescriptor(parameter);
            if (conversionService.canConvert(source, target)) {
                return binder.convertIfNecessary(sourceValue, parameter.getParameterType(), parameter);
            }
        }
        return null;
    }

}
