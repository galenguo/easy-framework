package com.efun.core.web.config;

import com.efun.core.config.Configuration;
import com.efun.core.utils.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * CORSInterceptor
 *
 * @author Galen
 * @since 2017/5/24
 */
public class CORSInterceptor implements HandlerInterceptor {

    private final String innerHeaders = "advertiser,appplatform,content-type,devicetype,encoding,osversion,packagename,sdkversion,timezone,versioncode";

    private final String crossDomain = StringUtils.isNotBlank(Configuration.getProperty("cross.domains")) ? Configuration.getProperty("cross.domains") : "*";

    private final String headers = StringUtils.isNoneBlank(Configuration.getProperty("cross.headers")) ? Configuration.getProperty("cross.headers") + "," + innerHeaders : innerHeaders;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", crossDomain);
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", headers);
        httpServletResponse.addHeader("Access-Control-Max-Age", "1728000");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
