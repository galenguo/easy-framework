package com.efun.archetype.asyn;

import com.alibaba.fastjson.JSON;
import com.efun.archetype.domain.User;
import com.efun.core.asyn.EventHandler;

/**
 * UserEventHandler
 *
 * @author Galen
 * @since 2016/9/28
 */
public class UserEventHandler extends EventHandler<User> {

    @Override
    protected void onEvent(User event) {
        System.out.println(JSON.toJSON(event));
    }
}
