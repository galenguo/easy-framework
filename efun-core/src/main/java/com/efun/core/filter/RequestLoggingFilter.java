package com.efun.core.filter;

import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

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
        this.logger.info(httpServletRequest.getServletPath() + "-" + paramMapToString(httpServletRequest.getParameterMap()));
    }

    @Override
    protected void afterRequest(HttpServletRequest httpServletRequest, String s) {

    }

    private String paramMapToString(Map<String, ? extends Object> paramMap) {
        Iterator entries = paramMap.entrySet().iterator();
        Map.Entry entry = null;
        String name = null;
        String value = null;
        StringBuffer stringBuffer = new StringBuffer();
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            stringBuffer.append(", " + name + "=" + value);
        }
        if (stringBuffer.length() == 0) {
            return "{}";
        } else {
            return "{" + stringBuffer.toString().substring(2, stringBuffer.length())  +"}";
        }
    }
}
