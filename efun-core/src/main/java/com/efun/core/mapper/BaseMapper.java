package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * BaseMapper
 *
 * @author Galen
 * @since 2016/6/1
 */
public interface BaseMapper<E extends BaseEntity<ID>, ID extends Serializable> {
}
