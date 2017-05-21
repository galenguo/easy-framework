package com.efun.core.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efun.core.context.ApplicationContext;
import com.efun.core.web.ParamValidator;
import com.efun.core.web.ResultBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseController
 * 基础controller
 *
 * @author Galen
 * @since 2016/5/30
 */
public abstract class BaseController {

    protected final Logger logger = LogManager.getLogger(getClass());

    protected ParamValidator validator(JSONObject params) {
        StringBuilder builder = new StringBuilder(256);
        builder.append(ApplicationContext.getHttpRequest().getServletPath()).append("-ip:").append(ApplicationContext.getRequestIp())
                .append("-params-").append(JSON.toJSONString(params));
        logger.info(builder);
        return ParamValidator.creatValidator(params);
    }

    protected ResultBean returnResult(String code, String message) {
        return returnResult(code, message, null);
    }

    protected ResultBean returnResult(String code, Object data) {
        return returnResult(code, data, null);
    }

    protected ResultBean returnResult(String code, String message, String... args) {
        return returnResult(code, null, message, args);
    }

    protected ResultBean returnResult(String code, Object data, String message, String... args) {
        message = ApplicationContext.getMessage(message, args);
        ResultBean resultBean = new ResultBean(code, message, data);
        logger.info(ApplicationContext.getHttpRequest().getServletPath() + "-result-" + JSON.toJSONString(resultBean));
        return resultBean;
    }


}
