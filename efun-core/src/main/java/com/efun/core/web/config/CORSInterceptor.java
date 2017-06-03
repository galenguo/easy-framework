package com.efun.core.web.config;

import com.efun.core.config.Configuration;
import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * CORSInterceptor
 *
 * @author Galen
 * @since 2017/5/24
 */
public class CORSInterceptor implements HandlerInterceptor {

    protected final Logger logger = LogManager.getLogger(getClass());

    private final String innerHeaders = "advertiser,appplatform,content-type,devicetype,encoding,osversion,packagename,sdkversion,timezone,versioncode";

    private final String crossDomain = StringUtils.isNotBlank(Configuration.getProperty("cross.domains")) ? Configuration.getProperty("cross.domains") : "*";

    private final String headers = StringUtils.isNoneBlank(Configuration.getProperty("cross.headers")) ? Configuration.getProperty("cross.headers") + "," + innerHeaders : innerHeaders;

    private Map<String, String> domainMap = new HashMap<String, String>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", getOrigin(httpServletRequest));
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", headers);
        httpServletResponse.addHeader("Access-Control-Max-Age", "1728000");
        return true;
    }

    private String getOrigin(HttpServletRequest httpServletRequest) {
        if ("*".equals(crossDomain)) {
            return crossDomain;
        } else {
            //优先获取origin
            String origin = httpServletRequest.getHeader("Origin");
            String domain = null;
            if (StringUtils.isNotBlank(origin)) {
                domain = origin.substring(origin.indexOf("//") + 2);
            } else {
                //次优先获取referer
                origin = httpServletRequest.getHeader("Referer");
                if (StringUtils.isNotBlank(origin)) {
                    domain = origin.substring(origin.indexOf("//") + 2);
                    domain = domain.substring(0, domain.indexOf("/"));
                    origin = httpServletRequest.getScheme() + "://" + domain;
                }
            }
            if (StringUtils.contains(crossDomain, domain)) {
                return origin;
            }
            return crossDomain;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
