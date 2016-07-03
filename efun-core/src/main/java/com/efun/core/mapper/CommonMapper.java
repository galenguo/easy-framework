package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.query.Query;
import org.apache.ibatis.annotations.*;

import java.util.Collection;
import java.util.List;

/**
 * CommonMapper
 * 通用化Mapper
 *
 * @author Galen
 * @since 2016/6/22
 */
public interface CommonMapper {

    /**
     * 根据id查询
     * @param id
     * @param entityClass
     * @param <E>
     * @return
     */
    @SelectProvider(type = DynamicSqlProvider.class, method = "findById")
    <E extends BaseEntity> E findById(@Param("id")Object id, @Param("entityClass")Class<E> entityClass);

    /**
     * 插入一条记录
     * @param entity
     * @param entityClass
     * @param <E>
     */
    @InsertProvider(type = DynamicSqlProvider.class, method = "insert")
    <E extends BaseEntity> void insert(@Param("entity")E entity, @Param("entityClass")Class<E> entityClass);

    /**
     * 批量插入，未开发
     * @param collection
     * @param entityClass
     * @param <E>
     */
    <E extends BaseEntity> void insertBatch(@Param("collection")Collection<E> collection, @Param("entityClass")Class<E> entityClass);

    /**
     * 更新一条记录
     * @param entity
     * @param entityClass
     * @param <E>
     */
    @UpdateProvider(type = DynamicSqlProvider.class, method = "update")
    <E extends BaseEntity> void update(@Param("entity")E entity, @Param("entityClass")Class<E> entityClass);

    /**
     * 根据id删除一条记录
     * @param id
     * @param entityClass
     * @param <E>
     */
    @DeleteProvider(type = DynamicSqlProvider.class, method = "delete")
    <E extends BaseEntity> void delete(@Param("id")Object id, @Param("entityClass")Class<E> entityClass);

    /**
     * 根据条件查询列表(未完成)
     * @param query
     * @param entityClass
     * @param <E>
     * @return
     */
    /*@SelectProvider(type = DynamicSqlProvider.class, method = "")*/
    <E extends BaseEntity> List<E> queryList(@Param("query")Query query, @Param("entityClass")Class<E> entityClass);
}
