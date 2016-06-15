package com.efun.core.filter;

import com.efun.core.config.Configuration;
import com.efun.core.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import sun.java2d.pipe.AAShapePipe;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AntiSamyFilter
 * 安全过滤filter，暂不使用
 *
 * @author Galen
 * @since 2016/6/13
 */
public class AntiSamyFilter implements Filter {

    protected static final Logger logger = LogManager.getLogger(AntiSamyFilter.class);

    public static final String antisamy_config = "antisamy-anythinggoes.xml";//antisamy-ebay.xml

    /**
     * AntiSamy is unfortunately not immutable, but is threadsafe if we only call
     * {@link AntiSamy#scan(String taintedHTML, int scanType)}
     */
    private final AntiSamy antiSamy;

    private static Boolean isRefererValidateEnabled;

    private static List<String> refererList = new ArrayList<String>();

    private static String RefererValidate_Enabled = "refererValidate.enabled";

    private static String RefererValidate_List = "refererValidate.items";

    private String mode = "DENY";

    public AntiSamyFilter() {

        System.out.println("----AntiSamyFilter-----");
        String refererValidate_Enabled  = Configuration.getProperty(RefererValidate_Enabled);
        if(StringUtils.equalsIgnoreCase("true", refererValidate_Enabled)){
            isRefererValidateEnabled = true;
            String refererItems =   Configuration.getProperty(RefererValidate_List);
            if(StringUtils.isNotBlank(refererItems)){
                String[] refererArray = refererItems.split(",");
                for (String refererItem : refererArray) {
                    if(StringUtils.isNotBlank(refererItem)){
                        refererList.add(refererItem);
                    }
                }
            }
        }
        else{
            isRefererValidateEnabled = false;
        }

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream(antisamy_config);
            Policy policy = Policy.getInstance(inputStream);
            antiSamy = new AntiSamy(policy);
            inputStream.close();
        } catch (PolicyException e) {
            throw new IllegalStateException(e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String referer = httpServletRequest.getHeader("Referer");
            if (referer != null) {
                if(isRefererValidateEnabled){

                    ((HttpServletResponse)response).addHeader("X-FRAME-OPTIONS", mode);

                    if(refererList.size()>0){
                        boolean isValidReferer = false;
                        // 从 HTTP 头中取得 Referer 值
                        System.out.println("referer:" + referer);
                        for (String refererItem : refererList)  {
                            // 判断 Referer 是否以配置的开头
                            if( (referer.trim().startsWith(refererItem))){
                                isValidReferer = true;
                            }
                        }
                        if(!isValidReferer)
                            throw new ServletException();
                    }
                }
            }

            CleanServletRequest cleanRequest = new CleanServletRequest(httpServletRequest, antiSamy);
            chain.doFilter(cleanRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    /**
     * Wrapper for a {@link HttpServletRequest} that returns 'safe' parameter values by
     * passing the raw request parameters through the anti-samy filter. Should be private
     */
    public static class CleanServletRequest extends HttpServletRequestWrapper {

        private final AntiSamy antiSamy;

        private CleanServletRequest(HttpServletRequest request, AntiSamy antiSamy) {
            super(request);
            this.antiSamy = antiSamy;
        }

        /**
         * overriding getParameter functions in {@link ServletRequestWrapper}
         */
        @Override
        public String[] getParameterValues(String name) {
            String[] originalValues = super.getParameterValues(name);
            if (originalValues == null) {
                return null;
            }
            List<String> newValues = new ArrayList<String>(originalValues.length);
            for (String value : originalValues) {
                newValues.add(filterString(value));
            }
            return newValues.toArray(new String[newValues.size()]);
        }

        @Override
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public Map getParameterMap() {
            Map<String, String[]> originalMap = super.getParameterMap();
            Map<String, String[]> filteredMap = new ConcurrentHashMap<String, String[]>(originalMap.size());
            for (String name : originalMap.keySet()) {
                filteredMap.put(name, getParameterValues(name));
            }
            return Collections.unmodifiableMap(filteredMap);
        }

        @Override
        public String getParameter(String name) {
            String potentiallyDirtyParameter = super.getParameter(name);
            return filterString(potentiallyDirtyParameter);
        }

        /**
         * This is only here so we can see what the original parameters were, you should delete this method!
         *
         * @return original unwrapped request
         */
        @Deprecated
        public HttpServletRequest getOriginalRequest() {
            return (HttpServletRequest) super.getRequest();
        }

        /**
         * @param potentiallyDirtyParameter string to be cleaned
         * @return a clean version of the same string
         */
        private String filterString(String potentiallyDirtyParameter) {
            if (potentiallyDirtyParameter == null) {
                return null;
            }

            try {
                CleanResults cr = antiSamy.scan(potentiallyDirtyParameter, AntiSamy.DOM);
                if (cr.getNumberOfErrors() > 0) {
                    logger.debug("antisamy encountered problem with input: " + cr.getErrorMessages());
                }
                return cr.getCleanHTML();
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
    }
}
