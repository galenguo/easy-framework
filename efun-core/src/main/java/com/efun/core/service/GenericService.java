package com.efun.core.service;

import com.efun.core.domain.BaseEntity;
import com.efun.core.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;

/**
 * GenericService
 * 泛型service接口
 *
 * @author Galen
 * @since 2016/6/1
 */
public interface GenericService<M extends BaseMapper<E, ID>, E extends BaseEntity<ID>, ID extends Serializable> extends BaseService {

    E findById(ID id);

    List<E> findAll();

}
