package com.efun.core.mapper;

import com.efun.core.domain.BaseEntity;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.query.Criteria;
import com.efun.core.mapper.support.SqlSessionFactoryBean;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * BaseMapperImpl
 *
 * @author Galen
 * @since 2016/6/21
 */
public class BaseMapperImpl<E extends BaseEntity<ID>, ID extends Serializable> extends SqlSessionDaoSupport implements BaseMapper<E, ID> {

    @Override
    public E findById(ID id) {
        return getSqlSession().selectOne("", id);
    }

    @Override
    public void insert(E entity) {

    }

    @Override
    public void insertBatch(Collection<E> collection) {

    }

    @Override
    public void save(E entity) {

    }

    @Override
    public void saveAll(Collection<E> collection) {

    }

    @Override
    public void delete(E entity) {

    }

    @Override
    public void delete(ID id) {

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
    public List<E> queryList(Criteria criteria, Pageable pageable) {
        return null;
    }

    @Override
    public Page<E> queryPage(Criteria criteria, Pageable pageable) {
        return null;
    }
}
