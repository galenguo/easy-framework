package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.query.Criteria;
import com.efun.core.mapper.query.Query;
import com.efun.core.mapper.support.SqlSessionFactoryBean;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

/**
 * BaseMapperImpl
 *
 * @author Galen
 * @since 2016/6/21
 */
public class BaseMapperImpl<E extends BaseEntity<ID>, ID extends Serializable> extends SqlSessionDaoSupport implements BaseMapper<E, ID> {

    protected Class<E> entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Autowired
    CommonMapper commonMapper;

    @Override
    public E findById(ID id) {
        return commonMapper.findById(id, entityClass);
    }

    @Override
    public void insert(E entity) {
        commonMapper.insert(entity, entityClass);
    }

    @Override
    public void insertBatch(Collection<E> collection) {
        commonMapper.insertBatch(collection, entityClass);
    }

    @Override
    public void save(E entity) {
        E e = commonMapper.findById(entity.getId(), entityClass);
        if (e != null) {
            commonMapper.update(entity, entityClass);
        } else {
            commonMapper.insert(entity, entityClass);
        }

    }

    @Override
    public void saveAll(Collection<E> collection) {
        for(E entity : collection) {
            save(entity);
        }
    }

    @Override
    public void delete(E entity) {
        commonMapper.delete(entity.getId(), entityClass);
    }

    @Override
    public void delete(ID id) {
        commonMapper.delete(id, entityClass);
    }

    @Override
    public List<E> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<E> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public List<E> queryList(Query query) {
        return null;
    }

    @Override
    public Page<E> queryPage(Query query) {
        return null;
    }
}
