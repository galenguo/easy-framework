package com.efun.archetype.service;

import com.efun.archetype.domain.User;
import com.efun.archetype.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UseServiceImpl
 *
 * @author Galen
 * @since 2016/6/1
 */
@Service
public class UseServiceImpl implements UserService {

    @Autowired
    UserMapper mapper;

    @Override
    public User findById(String id) {
        return mapper.findById(id);
    }

    @Override
    public User getUserByName(String name) {
        return mapper.getUser(name);
    }
}
