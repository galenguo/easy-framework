package com.efun.archetype.web;

import com.efun.archetype.domain.User;
import com.efun.archetype.service.UserService;
import com.efun.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * AppController
 *
 * @author Galen
 * @since 2016/5/30
 */
@RequestMapping("/app")
@RestController
public class AppController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping("helloworld")
    public List<String> helloworld(@ModelAttribute("user")User user) {
        logger.debug("helloworld");
        List<String> list = new ArrayList<String>();
        list.add("hello world!");
        list.add(userService.getUserByName("galen").getName());
        list.add(userService.findById("1").getName());
        return list;
    }
}
