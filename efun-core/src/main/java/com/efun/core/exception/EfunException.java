package com.efun.core.exception;

import com.efun.core.context.ApplicationContext;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Locale;

/**
 * EfunException
 *
 * @author Galen
 * @since 2016/5/30
 */
public class EfunException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Object[] args = null;


    public EfunException(Throwable cause) {
        super(cause);
    }

    public EfunException(String message) {
        super(message);
    }

    public EfunException(String message, Throwable cause) {
        super(message, cause);
    }

    public EfunException(String message, Object[] args) {
        super(message);
        this.args = args == null ? null : args.clone();
    }

    public EfunException(String message, Object[] args, Throwable cause) {
        super(message, cause);
        this.args = args == null ? null : args.clone();
    }

    public String getFullStackMessage() {
        return ExceptionUtils.getStackTrace(this);
    }

    @Override
    public String getLocalizedMessage() {
        return ApplicationContext.getMessage(getMessage(), args);
    }

    public String getLocalizedMessage(Locale locale) {
        return ApplicationContext.getMessage(getMessage(), args, getMessage(),
                locale);
    }
}
