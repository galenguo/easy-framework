package com.efun.core.domain;

import com.efun.core.mapper.annotation.Id;

import java.io.Serializable;

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

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
