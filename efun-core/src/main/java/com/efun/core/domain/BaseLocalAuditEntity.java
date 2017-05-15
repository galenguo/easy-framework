package com.efun.core.domain;

import com.efun.core.mapper.annotation.Column;
import com.efun.core.mapper.annotation.LocalDate;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseLocalAuditEntity
 * 修改时间本地化接口
 *
 * @author Galen
 * @since 2017/5/14
 */
public class BaseLocalAuditEntity<ID extends Serializable> extends BaseAuditEntity<ID> {

    @LocalDate
    @Column(value = "localTime")
    protected Date localTime;

    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }
}
