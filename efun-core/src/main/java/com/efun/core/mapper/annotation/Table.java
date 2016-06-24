package com.efun.core.mapper.annotation;

import java.lang.annotation.*;

/**
 * Table
 *
 * @author Galen
 * @since 2016/6/20
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
    String name();
}
