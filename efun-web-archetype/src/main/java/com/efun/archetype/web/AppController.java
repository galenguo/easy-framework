package com.efun.archetype.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.efun.archetype.domain.User;
import com.efun.archetype.mapper.UserMapper;
import com.efun.archetype.service.UserService;
import com.efun.core.asyn.EventPublisher;
import com.efun.core.context.ApplicationContext;
import com.efun.core.domain.page.Page;
import com.efun.core.domain.page.PageImpl;
import com.efun.core.domain.page.Pageable;
import com.efun.core.mapper.query.Criteria;
import com.efun.core.mapper.query.Query;
import com.efun.core.utils.StringUtils;
import com.efun.core.web.ParamValidator;
import com.efun.core.web.ResultBean;
import com.efun.core.web.ResultCode;
import com.efun.core.web.binding.MapWapper;
import com.efun.core.web.binding.ModelParam;
import com.efun.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

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

    @Autowired
    UserMapper userMapper;

    @Autowired
    EventPublisher eventPublisher;

    //http://localhost:8000/app/helloworld?user.id=1&user.name=echo
    @RequestMapping("helloworld")
    public List<String> helloworld(@ModelParam("user")User user) {
        logger.debug("helloworld");
        logger.debug(user.getName());
        List<String> list = new ArrayList<String>();
        list.add("hello world!");
        /*list.add(userService.getUserByName("galen").getName());*/
        list.add(userService.findById2("1").getPhoneNumber());
        list.add(userService.findById1("1").getPhoneNumber());
        list.add(userService.getUserByName("galen").getName());
        list.add(userService.getUserByName("galen").getPhoneNumber());
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

    //http://localhost:8000/app/helloworld4?createDate=1464689100000
    @RequestMapping("helloworld4")
    public List<String> helloworld4(@ModelAttribute("createDate")Date date) {
        logger.debug("helloworld4");
        List<String> list = new ArrayList<String>();
        list.add("hello world again again again!");
        list.add(date.toString());
        return list;
    }

    //国际化测试
    //http://localhost:8000/app/getMessage
    @RequestMapping("getMessage")
    public List<String> testMessage(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("####:   " + request.getQueryString());
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
    public User findById() {
        return userService.findById("3");
    }

    //http://localhost:8000/app/queryUser
    @RequestMapping("queryUser")
    public List<User> queryUser() {
        Query query = new Query(Criteria.where("created_date").lte(new Date()));
        return userMapper.queryList(query);
    }

    //http://localhost:8000/app/queryUserMap
    @RequestMapping("queryUserMap")
    public List<Map<String, Object>> queryUserMap() {
        Query query = new Query(Criteria.where("created_date").lte(new Date())).fields("id, name, last_modified_date as lastModifiedDate, phone_number as phoneNumber");
        List<Map<String, Object>> list = userMapper.queryMapList(query);
        return list;
    }

    //http://localhost:8000/app/queryUserPage?pageNumber=0&pageSize=5
    @RequestMapping("queryUserPage")
    public Page<User> queryUserPage(Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        List<User> users = userMapper.queryList(query);
        return new PageImpl<User>(users, pageable, 10);
    }

    //http://localhost:8000/app/insert
    @RequestMapping("insert")
    public String insert() {
        User user = new User();
        user.setId(100L);
        user.setName("test");
        user.setPhoneNumber("12345678");
        int id = userMapper.insert(user);

        //userMapper.queryList(new Query(Criteria.where("creation_time").lte("NOW()").and("name").is("galen")));
        //userMapper.update(user,null);
        return id + user.getId().toString();
    }

    //http://localhost:8000/app/insertBatch
    @RequestMapping("insertBatch")
    public String insertBatch() {
        User user = new User();
        user.setName("galenecho");
        user.setPhoneNumber("12345678");
        user.setGender(User.Gender.WOMAN);

        User user1 = new User();
        user1.setName("galenecho");
        user1.setPhoneNumber("12345678");
        user1.setGender(User.Gender.WOMAN);
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user1);
        int rows = userMapper.insertBatch(list);
        return "success";
    }

    //http://localhost:8000/app/update
    @RequestMapping("update")
    public String update() {
        User user = new User();
        user.setId(4L);
        userMapper.update(user,true);
        return "success";
    }

    //http://localhost:8000/app/getDate?importDate=1467547667441
    @RequestMapping("getDate")
    public Date getDate(@ModelAttribute("importDate")Date date) {
        return date;
    }

    //http://localhost:8000/app/validUser?user.id=1&user.name=echo
    @RequestMapping("validUser")
    public User validUser(@Valid @ModelParam("user")User user) {
        return user;
    }

    @RequestMapping("validUser1")
    public Object validUser1(@ModelParam("user") User user) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(user.getPhoneNumber())) {
            result.put("exception", Boolean.TRUE);
            result.put("message", "user: phoneNumber [" + ApplicationContext.getMessage("phone.not.null") + "]");
            result.put("code", "-1000");
            return result;
        }
        return user;
    }

    @RequestMapping("validUser2")
    public Object validUser2(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isBlank(request.getParameter("phoneNumber"))) {
            result.put("exception", Boolean.TRUE);
            result.put("message", "user: phoneNumber [" + ApplicationContext.getMessage("phone.not.null") + "]");
            result.put("code", "-1000");
            return result;
        }
        return null;
    }

    //http://localhost:8000/app/validUserNext?id=1&name=echo
    @RequestMapping("validUserNext")
    public User validUserNext(@Valid User user) {
        return user;
    }

    //http://localhost:8000/app/eventPublish
    @RequestMapping("eventPublish")
    public String eventPublish() {
        long start = System.currentTimeMillis();
        User user = new User();
        user.setName("galenecho");
        user.setPhoneNumber("12345678");
        user.setGender(User.Gender.WOMAN);
        eventPublisher.publish(user);
        eventPublisher.publish(user);
        eventPublisher.publish(user);
        logger.info(System.currentTimeMillis() - start);
        return "success";

    }

    @RequestMapping("upload")
    public String upload(@RequestParam("upload")MultipartFile file) {
        logger.info(file.getOriginalFilename());
        return file.getOriginalFilename();
    }

    @RequestMapping("fastJson")
    public ResultBean upload(@RequestBody JSONObject params) {
        ParamValidator validator = validator(params);
        Integer a = validator.isInteger("a");
        Double b = validator.isDouble("b");
        logger.info("###: " + (a + b));
        return returnResult(ResultCode.SUCCESS, params);
    }
}
