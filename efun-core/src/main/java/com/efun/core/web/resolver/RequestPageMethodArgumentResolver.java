package com.efun.core.web.resolver;

import com.efun.core.domain.page.PageRequest;
import com.efun.core.domain.page.Pageable;
import com.efun.core.domain.page.Sort;
import com.efun.core.utils.StringUtils;
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
        return Pageable.class.isAssignableFrom(paramType);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        return createPageRequest(nativeWebRequest);
    }

    private Pageable createPageRequest(NativeWebRequest nativeWebRequest) {
        int pageNumber = getNumber(nativeWebRequest, PAGE_NUMBER, 0);
        int pageSize = getNumber(nativeWebRequest, PAGE_SIZE, DEFAULT_PAGE_SIZE);
        if (pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > DEFAULT_MAX_PAGE_SIEZ) {
            pageSize = DEFAULT_MAX_PAGE_SIEZ;
        }
        Sort sort = getSort(nativeWebRequest, PAGE_ORDER, PAGE_DIRECTION);
        return new PageRequest(pageNumber, pageSize, sort);
    }

    private int getNumber(NativeWebRequest nativeWebRequest, String parameterName, int defaultValue) {
        try {
            String parameterValue = nativeWebRequest.getParameter(parameterName);
            return Integer.valueOf(parameterValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private Sort getSort(NativeWebRequest nativeWebRequest, String paramOrderName, String paramDirectionName) {
        String paramOrderVal = nativeWebRequest.getParameter(paramOrderName);
        if (StringUtils.isNotBlank(paramOrderVal)) {
            String paramDirectionVal = nativeWebRequest.getParameter(paramDirectionName);
            if (StringUtils.isNotBlank(paramDirectionVal)) {
                return new Sort (Sort.Direction.fromStringOrNull(paramDirectionVal), paramOrderVal);
            }
        }
        return null;
    }
}
