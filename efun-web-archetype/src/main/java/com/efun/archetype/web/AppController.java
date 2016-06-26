package com.efun.archetype.web;

import com.efun.archetype.domain.User;
import com.efun.archetype.service.UserService;
import com.efun.core.context.ApplicationContext;
import com.efun.core.web.binding.MapWapper;
import com.efun.core.web.binding.ModelParam;
import com.efun.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    //http://localhost:8000/app/helloworld?user.id=1&user.name=echo
    @RequestMapping("helloworld")
    public List<String> helloworld(@ModelParam("user")User user) {
        logger.debug("helloworld");
        logger.debug(user.getName());
        List<String> list = new ArrayList<String>();
        list.add("hello world!");
        list.add(userService.getUserByName("galen").getName());
        list.add(userService.findById("1").getName());
        list.add(userService.findById("1").getPhoneNumber());
        list.add(userService.findById1("1").getName());
        list.add(null);
        return list;
    }

    //http://localhost:8000/app/helloworld2?list[0].id=0&list[0].name=galen&list[1].id=1&list[1].name=echo
    @RequestMapping("helloworld2")
    public List<String> helloworld2(@ModelParam("list")List<User> users) {
        logger.debug("helloworld2");
        List<String> list = new ArrayList<String>();
        list.add("hello world again!");
        for (User user : users) {
            list.add(user.getName());
        }
        return list;
    }

    //http://localhost:8000/app/helloworld3?map['0'].id=0&map['0'].name=galen&map['1'].id=1&map['1'].name=echo
    @RequestMapping("helloworld3")
    public List<String> helloworld3(@ModelParam("map")MapWapper<String,User> userMap) {
        logger.debug("helloworld3");
        List<String> list = new ArrayList<String>();
        list.add("hello world again again!");
        for (User user : userMap.values()) {
            list.add(user.getName());
        }
        return list;
    }

    //http://localhost:8000/app/helloworld4?date=1464689100000
    @RequestMapping("helloworld4")
    public List<String> helloworld4(@ModelAttribute("date")Date date) {
        logger.debug("helloworld4");
        List<String> list = new ArrayList<String>();
        list.add("hello world again again again!");
        list.add(date.toString());
        return list;
    }

    //国际化测试
    //http://localhost:8000/app/getMessage
    @RequestMapping("getMessage")
    public List<String> testMessage() {
        List<String> list = new ArrayList<String>();
        list.add(ApplicationContext.getMessage("message"));
        return list;
    }

    //测试事务
    //http://localhost:8000/app/insertUsers
    @RequestMapping("insertUsers")
    public List<String> insertUsers() {
        List<String> list = new ArrayList<String>();
        userService.inserUsers();
        list.add("success");
        return list;
    }

    //http://localhost:8000/app/findById
    @RequestMapping("findById")
    public String findById() {
        return userService.findById("1").getName();
    }
}
