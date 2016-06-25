package com.efun.core.domain;

import java.io.Serializable;

/**
 * BaseEntity
 *
 * @author Galen
 * @since 2016/5/30
 */
public abstract class BaseEntity<ID extends Serializable> {

    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
