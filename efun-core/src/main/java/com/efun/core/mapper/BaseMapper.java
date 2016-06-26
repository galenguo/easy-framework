package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;
import com.efun.core.mapper.query.Query;
import org.apache.ibatis.annotations.*;

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
     * 根据id查询
     * @param id
     * @param <E>
     * @return
     */
    @SelectProvider(type = BaseSqlProvider.class, method = "findById")
    <E extends BaseEntity> E findById(@Param("id")Object id);

    /**
     * 插入一条记录
     * @param entity
     * @param <E>
     */
    @InsertProvider(type = BaseSqlProvider.class, method = "insert")
    <E extends BaseEntity> void insert(@Param("entity")E entity);

    /**
     * 批量插入，未开发
     * @param collection
     * @param <E>
     */
    <E extends BaseEntity> void insertBatch(@Param("collection")Collection<E> collection);

    /**
     * 更新一条记录
     * @param entity
     * @param <E>
     */
    @UpdateProvider(type = BaseSqlProvider.class, method = "update")
    <E extends BaseEntity> void update(@Param("entity")E entity);

    /**
     * 根据id删除一条记录
     * @param id
     * @param <E>
     */
    @DeleteProvider(type = BaseSqlProvider.class, method = "delete")
    <E extends BaseEntity> void delete(@Param("id")Object id);

    /**
     * 根据条件查询列表(未完成)
     * @param query
     * @param <E>
     * @return
     */
    /*@SelectProvider(type = BaseSqlProvider.class, method = "")*/
    <E extends BaseEntity> List<E> queryList(@Param("query")Query query);

}
