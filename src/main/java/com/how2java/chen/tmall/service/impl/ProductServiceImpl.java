package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.how2java.chen.tmall.dao.ProductMapper;
import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.service.CategoryService;
import com.how2java.chen.tmall.service.ProductImageService;
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

    @Autowired
    private ProductImageService productImageService;


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

        Product product = productMapper.selectByPrimaryKey(id);

        setCategory(product);

        return product;
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
        for (Product product : products) {
            setCategory(product);
        }
        PageInfo<Product> pageInfo = new PageInfo<>(products);

        return new Page4Navigator<>(pageInfo, navigatePages);
    }

    @Override
    public void fill(List<Category> categories) {

        for (Category category : categories) {
            fill(category);
        }

    }

    @Override
    public void fill(Category category) {

        List<Product> products = listByCategory(category);
        productImageService.setFirstProductImage(products);

        category.setProducts(products);
    }

    @Override
    public void fillByRow(List<Category> categories) {

        int productNumberEachRow = 8;

        for (Category category : categories) {

            List<List<Product>> productsByRow = Lists.newArrayList();
            List<Product> products = category.getProducts();

            for (int i = 0; i < products.size(); i += productNumberEachRow) {

                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;

                List<Product> productsOfEachRow = products.subList(i, size);

                productsByRow.add(productsOfEachRow);

            }

            category.setProductsByRow(productsByRow);
        }


    }

    @Override
    public List<Product> listByCategory(Category category) {

        Example example = new Example(Product.class);
        example.createCriteria()
                .andEqualTo("cid", category.getId());

        example.setOrderByClause("id desc");

        return productMapper.selectByExample(example);
    }


    private void setCategory(Product product) {
        Category category = categoryService.get(product.getCid());

        product.setCategory(category);
    }
}
