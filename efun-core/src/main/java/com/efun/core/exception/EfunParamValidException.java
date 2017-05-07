package com.efun.core.exception;

/**
 * EfunParamValidException
 *
 * @author Galen
 * @since 2017/5/7
 */
public class EfunParamValidException extends EfunException {

    public EfunParamValidException(Throwable cause) {
        super(cause);
    }

    public EfunParamValidException(String message) {
        super(message);
    }

    public EfunParamValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EfunParamValidException(String message, Object[] args) {
        super(message, args);
    }

    public EfunParamValidException(String message, Object[] args, Throwable cause) {
        super(message, args, cause);
    }
}
