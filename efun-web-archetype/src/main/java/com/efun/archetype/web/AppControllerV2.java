package com.efun.archetype.web;

import com.efun.core.web.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * AppControllerV2
 *
 * @author Galen
 * @since 2016/6/1
 */
@RequestMapping("/app")
@RestController
public class AppControllerV2 extends BaseController {

    @RequestMapping("helloworld2")
    public List<String> helloworld() {
        logger.debug("helloworld");
        List<String> list = new ArrayList<String>();
        list.add("hello world!");
        return list;
    }
}
