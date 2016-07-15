package com.efun.core.service;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.BaseMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
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
        return null;
    }

    @Override
    public List<E> findList(Pageable pageable) {
        return null;
    }

    @Override
    public Page<E> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public void inerst(E entity) {
        this.mapper.insert(entity);
    }

    @Override
    public void update(E entity) {
        this.update(entity);
    }

    @Override
    public void save(E entity) {
        try {
            this.update(entity);
        } catch (Throwable throwable) {
            this.inerst(entity);
        }
    }

    @Override
    public void delete(E entity) {
        this.mapper.delete(entity.getId());
    }

    @Override
    public void delete(ID id) {
        this.mapper.delete(id);
    }

    @Override
    public long count() {
        return this.mapper.count();
    }
}
