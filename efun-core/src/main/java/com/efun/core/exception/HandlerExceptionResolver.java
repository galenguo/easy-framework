package com.efun.core.exception;

import com.alibaba.fastjson.JSON;
import com.efun.core.utils.CollectionUtils;
import com.efun.core.utils.StringUtils;
import com.efun.core.web.ResultCode;
import com.efun.core.web.ResultBean;
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
import java.util.List;

/**
 * HandlerExceptionResolver
 * 异常统一处理
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

    @Override
    protected ModelAndView doResolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String callback = null;
        for (int i = 0; i< jsonpParameterNames.length; i++){
            callback = httpServletRequest.getParameter(jsonpParameterNames[i]);
            if(callback != null) {
                break;
            }
        }
        ResultBean result = new ResultBean();
        if (e instanceof EfunParamValidException) {
            result.setMessage(e.getMessage());
            result.setCode(ResultCode.PARAM_EXCEPTION);
            logger.warn("param valid error: [" + httpServletRequest.getServletPath() + "] " + e.getMessage());
        }
        else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errorList = bindException.getAllErrors();
            if (!CollectionUtils.isEmpty(errorList)) {
                StringBuilder message = new StringBuilder();
                for (ObjectError error : errorList) {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        message.append(fieldError.getField()).append(" [").append(fieldError.getDefaultMessage()).append("], ");
                    } else {
                        message.append(error.toString()).append("; ");
                    }
                }
                result.setMessage(e.getMessage());
                result.setCode(ResultCode.PARAM_EXCEPTION);
                logger.warn("bean valid error: [" + httpServletRequest.getServletPath() + "] " + message);
            }
        } else if (tryCacheException(httpServletRequest, e, result)) {

        } else {
            result.setCode(ResultCode.SYS_EXCEPTION);
            result.setMessage("[" + httpServletRequest.getServletPath() + "] " + e.toString());
            result.setData(ExceptionUtils.getStackTrace(e));
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
    public boolean tryCacheException(HttpServletRequest httpServletRequest, Exception e, ResultBean result) {
        return false;
    }
}
