package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.chen.tmall.dao.UserMapper;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.UserService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 15:04:41
 */


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Page4Navigator<User> list(int start, int size, int navigatePages) {

        Example example = new Example(User.class);
        example.setOrderByClause("id desc");

        PageHelper.startPage(start, size);
        List<User> users = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new Page4Navigator(pageInfo, navigatePages);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
