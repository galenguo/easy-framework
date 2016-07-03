package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.query.Criteria;
import com.efun.core.mapper.query.Query;

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

    /**
     * 根据id查询一条数据
     * @param id
     * @return
     */
    E findById(ID id);

    /**
     * 插入一条记录
     * @param entity
     */
    void insert(E entity);

    /**
     * 批量插入
     * @param collection
     */
    void insertBatch(Collection<E> collection);

    /**
     * 更新或插入（保存）
     * @param entity
     */
    void save(E entity);

    /**
     * 批量更新或插入（保存）
     * @param collection
     */
    void saveAll(Collection<E> collection);

    /**
     * 删除一条记录
     * @param entity
     */
    void delete(E entity);

    /**
     * 根据id删除一条记录
     * @param id
     */
    void delete(ID id);

    /**
     * 列表查询
     * @param pageable
     * @return
     */
    List<E> findAll(Pageable pageable);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<E> findPage(Pageable pageable);

    /**
     * 条件列表查询
     * @param query
     * @return
     */
    List<E> queryList(Query query);

    /**
     * 条件分页查询
     * @param query
     * @return
     */
    Page<E> queryPage(Query query);
}
