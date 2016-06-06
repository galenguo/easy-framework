package com.efun.core.web.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * RequestPageMethodArgumentResolver
 * 分页解析组件
 *
 * @author Galen
 * @since 2016/6/6
 */
public class RequestPageMethodArgumentResolver implements HandlerMethodArgumentResolver {

    //页码
    private static final String PAGE_NUMBER = "pageNumber";

    //分页大小
    private static final String PAGE_SIZE = "pageSize";

    //排序
    private static final String PAGE_ORDER = "pageOder";

    //升降序
    private static final String PAGE_DIRECTION = "pageDirection";

    //默认分页大小
    private static final int DEFAULT_PAGE_SIZE = 10;

    //默认分页最大size
    private static final int DEFAULT_MAX_PAGE_SIEZ = 1000;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> paramType = methodParameter.getParameterType();
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return null;
    }
}
