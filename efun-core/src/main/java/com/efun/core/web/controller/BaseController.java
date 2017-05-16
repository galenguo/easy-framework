package com.efun.core.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efun.core.context.ApplicationContext;
import com.efun.core.web.ParamValidator;
import com.efun.core.web.ResultBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * BaseController
 * 基础controller
 *
 * @author Galen
 * @since 2016/5/30
 */
public abstract class BaseController {

    protected final Logger logger = LogManager.getLogger(getClass().getName());

    protected ParamValidator validator(JSONObject params) {
        logger.info(ApplicationContext.getHttpRequest().getServletPath() + "-params-" + JSON.toJSONString(params));
        return ParamValidator.creatValidator(params);
    }

    protected ResultBean returnResult(String code, String message) {
        message = ApplicationContext.getMessage(message);
        return returnResult(code, message, null);
    }

    protected ResultBean returnResult(String code, Object data) {
        return returnResult(code, null, data);
    }

    protected ResultBean returnResult(String code, String message, String... args) {
        message = ApplicationContext.getMessage(message, args);
        return returnResult(code, message, null);
    }

    protected ResultBean returnResult(String code, String message, Object data) {
        ResultBean resultBean = new ResultBean(code, message, data);
        logger.info(ApplicationContext.getHttpRequest().getServletPath() + "-result-" + JSON.toJSONString(resultBean));
        return resultBean;
    }


}
