package com.efun.core.web.resolver;

import com.efun.core.domain.page.Pageable;
import com.efun.core.utils.StringUtils;
import org.springframework.beans.PropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import java.lang.annotation.Annotation;

/**
 * RequestBeanArgumentResolver
 *
 * @author Galen
 * @since 2016/6/6
 */
public class RequestBeanArgumentResolver implements HandlerMethodArgumentResolver {

    private WebBindingInitializer webBindingInitializer;

    public RequestBeanArgumentResolver(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        RequestBean requestBeanAnnotation = this.getRequestBeanAnnotation(methodParameter);
        if (requestBeanAnnotation != null) {
            String prefix = requestBeanAnnotation.value();
            return StringUtils.isNotBlank(prefix) && !Pageable.class.isAssignableFrom(methodParameter.getParameterType());
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {

        ServletRequest servletRequest = (ServletRequest) nativeWebRequest.getNativeRequest();
        PropertyValues pvs = null;

        return null;
    }

    private RequestBean getRequestBeanAnnotation(MethodParameter methodParameter) {
        for (Annotation annotation : methodParameter.getParameterAnnotations()) {
            if (annotation instanceof  RequestBean) {
                return (RequestBean) annotation;
            }
        }
        return null;
    }

}
