package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.util.Page4Navigator;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 15:01:55
 */

public interface UserService {


    /**
     * 查询用户列表
     *
     * @param start
     * @param size
     * @param navigatePages
     * @return
     */
    Page4Navigator<User> list(int start, int size, int navigatePages);

    User get(int id);

    boolean isExist(String name);

    User getByName(String name);

    void add(User user);


    User getByNameAndPassword(String name, String password);


}
