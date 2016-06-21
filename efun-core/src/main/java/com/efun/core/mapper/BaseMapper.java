package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.query.Criteria;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * BaseMapper
 * 基础DAO接口
 *
 * @author Galen
 * @since 2016/6/1
 */
public interface BaseMapper<E extends BaseEntity<ID>, ID extends Serializable> {

    E findById(ID id);

    void insert(E entity);

    void insertBatch(Collection<E> collection);

    void save(E entity);

    void saveAll(Collection<E> collection);

    void delete(E entity);

    void delete(ID id);

    List<E> findAll(Pageable pageable);

    Page<E> findPage(Pageable pageable);

    List<E> queryList(Criteria criteria, Pageable pageable);

    Page<E> queryPage(Criteria criteria, Pageable pageable);
}
