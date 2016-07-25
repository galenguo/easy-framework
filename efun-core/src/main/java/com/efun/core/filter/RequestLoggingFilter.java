package com.efun.core.filter;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestLoggingFilter
 * 请参数求日志输出
 *
 * @author Galen
 * @since 2016/7/25
 */
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected void beforeRequest(HttpServletRequest httpServletRequest, String s) {
        this.logger.info(httpServletRequest.getServletPath() + "-" + httpServletRequest.getParameterMap().toString());
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String s) {
    }
}
