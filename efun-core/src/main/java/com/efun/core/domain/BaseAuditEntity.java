package com.efun.core.domain;

import com.efun.core.mapper.annotation.Column;
import com.efun.core.mapper.annotation.CreatedDate;
import com.efun.core.mapper.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseAuditEntity
 *  审计基础entity
 * @author Galen
 * @since 2016/7/7
 */
public class BaseAuditEntity<ID extends Serializable> extends BaseEntity<ID> {

    @CreatedDate
    @Column(value = "createdTime")
    protected Date createdTime;

    @LastModifiedDate
    @Column(value = "modifiedTime")
    protected Date modifiedTime;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
