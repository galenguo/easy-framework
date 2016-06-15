package com.efun.archetype.service;

import com.efun.archetype.domain.User;
import com.efun.archetype.mapper.UserMapper;
import com.efun.core.context.ApplicationContext;
import com.efun.core.exception.EfunException;
import com.efun.core.mapper.support.DataSource;
import com.efun.core.utils.AssertUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UseServiceImpl
 *
 * @author Galen
 * @since 2016/6/1
 */
@Service
public class UseServiceImpl implements UserService {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    UserMapper mapper;

    @Override
    public User findById(String id) {
        return mapper.findById(id);
    }

    @Override
    @DataSource("read")
    public User findById1(String id) {
        return mapper.findById(id);
    }
    @Override
    @DataSource("write")
    public User findById2(String id) {
        return mapper.findById(id);
    }

    @Override
    public User getUserByName(String name) {
        return mapper.getUser(name);
    }

    @Override
    @Transactional
    @DataSource("write")
    public void inserUsers() {
        //测试回滚
        /*User user1 = new User();
        user1.setId("2");
        user1.setName("2");
        mapper.insertUser(user1);
        User user2 = new User();
        user2.setId("1");
        user2.setName("1");
        mapper.insertUser(user2);*/

        //测试夸库事务叠加。
        /*User readUser = mapper.findById("1");
        logger.debug("######" + readUser.getName());*/
        User user = new User();
        user.setId("2");
        user.setName("galen");
        mapper.insertUser(user);
    }
}
