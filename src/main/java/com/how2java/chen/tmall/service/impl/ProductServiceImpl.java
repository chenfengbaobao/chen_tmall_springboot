package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.chen.tmall.dao.ProductMapper;
import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.service.CategoryService;
import com.how2java.chen.tmall.service.ProductService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-16 22:00:40
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void add(Product product) {
        productMapper.insertSelective(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product get(int id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {

        Category category = categoryService.get(cid);


        Example example = new Example(Product.class);

        example.createCriteria()
                .andEqualTo("cid", category.getId());

        example.setOrderByClause("id desc");

        PageHelper.startPage(start, size);
        List<Product> products = productMapper.selectByExample(example);
        PageInfo<Product> pageInfo = new PageInfo<>(products);

        return new Page4Navigator<>(pageInfo, navigatePages);
    }
}
