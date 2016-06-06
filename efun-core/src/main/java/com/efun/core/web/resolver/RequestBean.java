package com.efun.core.web.resolver;

import java.lang.annotation.*;

/**
 * RequestBean
 *
 * @author Galen
 * @since 2016/6/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
@Documented
public @interface RequestBean {

    String value() default "";

}
