package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.chen.tmall.dao.CategoryMapper;
import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.service.CategoryService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-14 22:48:12
 */


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {


        Example example = new Example(Category.class);
        example.setOrderByClause("id desc");

        PageHelper.startPage(start, size);
        List<Category> categories = categoryMapper.selectByExample(example);

        PageInfo<Category> pageInfo = new PageInfo<>(categories);
        return new Page4Navigator<>(pageInfo, navigatePages);
    }

    @Override
    public List<Category> list() {
        Example example = new Example(Category.class);
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }


    @Override
    public void add(Category bean) {
        categoryMapper.insertSelective(bean);
    }


    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }


    @Override
    public Category get(int id) {
        Category c = categoryMapper.selectByPrimaryKey(id);
        return c;
    }

    @Override
    public void update(Category bean) {
        categoryMapper.updateByPrimaryKeySelective(bean);
    }
}
