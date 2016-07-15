package com.efun.core.service;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.Pageable;
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

    List<E> findList(Pageable pageable);

    Page<E> findPage(Pageable pageable);

    void inerst(E entity);

    void update(E entity);

    void save(E entity);

    void delete(E entity);

    void delete(ID id);

    long count();

}
