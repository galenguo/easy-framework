package com.efun.core.web;

/**
 * ResultBean
 * 接口返回数据bean
 *
 * @author Galen
 * @since 2017/5/7
 */
public class ResultBean {

    protected String code;

    protected String message;

    protected Object data;

    public ResultBean() {

    }

    public ResultBean(String code, String message) {
        this(code, message, null);
    }

    public ResultBean(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
