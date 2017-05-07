package com.efun.core.db.annotation;

/**
 * DSType
 *
 * @author Galen
 * @since 2016/9/2
 */
public enum DSType {

    //写库
    WRITE,

    //读库
    READ,

    //db1 备用标识，一般支持一读一写，额外支持多读多写。
    DB_1,

    //db2
    DB_2,

    //db3
    DB_3,

    //默认
    DEFAULT
}
