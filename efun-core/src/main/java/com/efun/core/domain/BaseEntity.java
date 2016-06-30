package com.efun.core.domain;

import com.efun.core.mapper.annotation.Column;
import com.efun.core.mapper.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 * 基础entity
 *
 * @author Galen
 * @since 2016/5/30
 */
public abstract class BaseEntity<ID extends Serializable> {

    @Id("id")
    protected ID id;

    @Column(value = "creation_time")
    protected Date creationTime;

    @Column(value = "modification_time")
    protected Date modificationTime;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }
}
