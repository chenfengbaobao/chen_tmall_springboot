package com.how2java.chen.tmall.web;

import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.query.PageQuery;
import com.how2java.chen.tmall.service.UserService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 15:10:09
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public Page4Navigator<User> list(PageQuery pageQuery) {


        return userService.list(pageQuery.getStart(), pageQuery.getSize(), 5);
    }


}
