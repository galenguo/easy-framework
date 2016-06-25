package com.efun.archetype.service;

import com.efun.archetype.domain.User;
import org.springframework.stereotype.Service;

/**
 * UserService
 *
 * @author Galen
 * @since 2016/6/1
 */
public interface UserService {

    User findById(String id);

    User getUserByName(String name);
}
