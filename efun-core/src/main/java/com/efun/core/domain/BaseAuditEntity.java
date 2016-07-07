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
    @Column(value = "created_date")
    protected Date createdDate;

    @LastModifiedDate
    @Column(value = "last_modified_date")
    protected Date lastModifiedDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
