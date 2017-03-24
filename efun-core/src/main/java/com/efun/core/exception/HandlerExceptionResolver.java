package com.efun.core.exception;

import com.alibaba.fastjson.JSON;
import com.efun.core.utils.CollectionUtils;
import com.efun.core.utils.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HandlerExceptionResolver
 * 一场统一处理
 *
 * @author Galen
 * @since 2016/7/2
 */
public class HandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    protected String[] jsonpParameterNames = new String[]{"jsoncallback", "jsonpcallback","callback"};

    private String contentType = "application/json;charset=UTF-8";

    private int order = 0;

    public HandlerExceptionResolver() {
        setOrder(order);
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    private String validExceptionCode = "0001";

    private String errorCode = "0000";

    public void setValidExceptionCode(String validExceptionCode) {
        this.validExceptionCode = validExceptionCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String callback = null;
        for (int i = 0; i< jsonpParameterNames.length; i++){
            callback = httpServletRequest.getParameter(jsonpParameterNames[i]);
            if(callback != null) {
                break;
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errorList = bindException.getAllErrors();
            if (!CollectionUtils.isEmpty(errorList)) {
                String message = "";
                for (ObjectError error : errorList) {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        message += fieldError.getField() + " [" + fieldError.getDefaultMessage() + "]" + ", ";
                    } else {
                        message += error.toString() + "; ";
                    }
                }
                result.put("message", message);
                result.put("code", validExceptionCode);
                logger.warn("bean valid error: [" + httpServletRequest.getServletPath() + "] " + message);
            }
        } else if (tryCacheException(httpServletRequest, e, result)) {

        } else {
            result.put("exception", Boolean.TRUE);
            result.put("code", errorCode);
            result.put("message", e.toString() + ExceptionUtils.getStackTrace(e));
            logger.error(e.getMessage(), e);
        }
        String jsonString = JSON.toJSONString(result);
        if (StringUtils.isNotBlank(callback)) {
            jsonString = new StringBuilder(callback).append("(").append(jsonString).append(")").toString();
        }

        try {
            httpServletResponse.setHeader("Content-type", contentType);
            OutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(jsonString.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e1) {
            logger.error(e1.getMessage(), e1);
        }
        return new ModelAndView();
    }

    /**
     * 子类覆盖，实现自定义异常拦截。
     * @param httpServletRequest
     * @param e
     * @param result
     * @return
     */
    public boolean tryCacheException(HttpServletRequest httpServletRequest, Exception e, Map<String, Object> result) {
        return false;
    }
}
