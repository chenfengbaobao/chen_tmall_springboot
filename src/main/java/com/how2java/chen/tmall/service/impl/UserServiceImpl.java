package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.chen.tmall.dao.UserMapper;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.UserService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 15:04:41
 */

@CacheConfig(cacheNames = "users")
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Cacheable(key = "'users-page-'+#p0+ '-' + #p1")
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

    @Override
    public boolean isExist(String name) {

        User user = getByName(name);

        return Objects.nonNull(user);
    }


    @Cacheable(key = "'users-one-name-'+#p0")
    @Override
    public User getByName(String name) {

        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("name", name);

        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }

        return users.get(0);
    }


    @CacheEvict(allEntries = true)
    @Override
    public void add(User user) {
        userMapper.insertUseGeneratedKeys(user);
    }

    @Override
    public User getByNameAndPassword(String name, String password) {

        Example example = new Example(User.class);
        example.createCriteria()
                .andEqualTo("name", name)
                .andEqualTo("password", password);

        List<User> users = userMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(users)) {
            return users.get(0);
        }
        return null;
    }
}
