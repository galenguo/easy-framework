package com.efun.core.domain;

import com.efun.core.mapper.annotation.Column;
import com.efun.core.mapper.annotation.CreatedDate;
import com.efun.core.mapper.annotation.Id;
import com.efun.core.mapper.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 * 基础entity
 *
 * @author Galen
 * @since 2016/5/30
 */
public abstract class BaseEntity<ID extends Serializable> implements EntityInterface {

    @Id("id")
    protected ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}
