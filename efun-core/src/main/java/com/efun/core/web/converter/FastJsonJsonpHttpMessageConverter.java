package com.efun.core.web.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.efun.core.utils.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * FastJsonJsonpHttpMessageConverter
 *
 * @author Galen
 * @since 2016/6/13
 */
public class FastJsonJsonpHttpMessageConverter extends FastJsonHttpMessageConverter {

    protected String[] jsonpParameterNames = new String[]{"jsonpcallback","callback"};

    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String callback = null;
        for (int i = 0; i< jsonpParameterNames.length; i++){
            callback = request.getParameter(jsonpParameterNames[i]);
            if(callback != null) {
                break;
            }
        }
        if (StringUtils.isNotBlank(callback)) {
            String jsonString = JSON.toJSONString(obj, getFastJsonConfig().getSerializeFilters());
            jsonString = new StringBuilder(callback).append("(").append(jsonString).append(")").toString();
            outputMessage.getBody().write(jsonString.getBytes(getFastJsonConfig().getCharset()));
        } else {
            super.writeInternal(obj, outputMessage);
        }

    }

    public void setJsonpParameterNames(String[] jsonpParameterNames) {
        this.jsonpParameterNames = jsonpParameterNames;
    }
}
