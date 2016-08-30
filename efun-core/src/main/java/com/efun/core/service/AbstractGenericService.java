package com.efun.core.service;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.PageImpl;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.BaseMapper;
import com.efun.core.mapper.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * AbstractGenericService
 * 泛型service抽象类
 *
 * @author Galen
 * @since 2016/6/1
 */
public class AbstractGenericService<M extends BaseMapper<E, ID>, E extends BaseEntity<ID>, ID extends Serializable>
        extends AbstractBaseService implements GenericService<M, E, ID> {

    @Autowired
    M mapper;

    @Override
    public E findById(ID id) {
        return mapper.findById(id);
    }

    @Override
    public List<E> findAll() {
        return this.mapper.queryList(null);
    }

    @Override
    public List<E> findList(Pageable pageable) {
        return this.mapper.queryList(new Query().with(pageable));
    }

    @Override
    public Page<E> findPage(Pageable pageable) {
        Page<E> page = new PageImpl<E>(this.mapper.queryList(new Query().with(pageable)), pageable, 0);
        return page;
    }

    @Override
    public Page<E> findPage(Pageable pageable, Boolean hasTotal) {
        long count = 0;
        if (hasTotal) {
            count = this.count();
        }
        Page<E> page = new PageImpl<E>(this.mapper.queryList(new Query().with(pageable)), pageable, count);
        return page;
    }

    @Override
    public int insert(E entity) {
        return this.mapper.insert(entity);
    }

    @Override
    public int insertBatch(Collection<E> entities) {
        return this.mapper.insertBatch(entities);
    }

    @Override
    public int update(E entity) {
        return this.mapper.update(entity, null);
    }

    @Override
    public int save(E entity) {
        int result = 0;
        if (entity.getId() != null && !entity.getId().toString().equals("")) {
            if ((result = this.mapper.update(entity, null)) == 0) {
                result = this.mapper.insert(entity);
            }
            return result;
        } else {
            result = this.mapper.insert(entity);
        }
        return result;
    }

    @Override
    public int delete(E entity) {
        return this.mapper.delete(entity.getId());
    }

    @Override
    public int delete(ID id) {
        return this.mapper.delete(id);
    }

    @Override
    public long count() {
        return this.mapper.count();
    }
}
