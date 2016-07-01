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

    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    E findById(ID id);

    /**
     * 查询所有实体列表
     * @return
     */
    List<E> findAll();

    /**
     * 分页查询列表
     * @param pageable
     * @return
     */
    List<E> findList(Pageable pageable);

    /**
     * 分页查询,不包含数据总量
     * @param pageable
     * @return
     */
    Page<E> findPage(Pageable pageable);

    /**
     * 分页查询,可选择是否包含数据总量
     * @param pageable
     * @param hasTotal
     * @return
     */
    Page<E> findPage(Pageable pageable, Boolean hasTotal);

    /**
     * 插入一个实体记录
     * @param entity
     */
    void inerst(E entity);

    /**
     * 更新一个实体记录，不更新为null的属性
     * @param entity
     */
    void update(E entity);

    /**
     * 保存一个实体记录，如果记录id存在，则更新记录，否则当做新记录插入
     * @param entity
     */
    void save(E entity);

    /**
     * 删除一个实体记录
     * @param entity
     */
    void delete(E entity);

    /**
     * 根据id删除一个实体记录
     * @param id
     */
    void delete(ID id);

    /**
     * 计算记录数量
     * @return
     */
    long count();

}
