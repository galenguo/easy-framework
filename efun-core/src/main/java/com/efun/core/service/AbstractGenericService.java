package com.efun.core.service;

import com.efun.core.domain.BaseEntity;
import com.efun.core.mapper.BaseMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * AbstractGenericService
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
        return null;
    }

    @Override
    public List<E> findAll() {
        return null;
    }
}
