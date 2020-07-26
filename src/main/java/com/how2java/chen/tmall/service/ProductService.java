package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.util.Page4Navigator;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-16 21:58:00
 */

public interface ProductService {

    void add(Product product);


    void delete(int id);

    Product get(int id);

    void update(Product product);

    Page4Navigator<Product> list(int cid, int start, int size, int navigatePages);


    /**
     * 为多个分类填充集合产品
     *
     * @param categories
     */
    void fill(List<Category> categories);

    /**
     * 为分类填充产品集合
     *
     * @param category
     */
    void fill(Category category);


    /**
     * 为多个分类填充【推荐产品集合】
     * 即把分类下的集合产品，按照【8】个为一行，柴成多行，以利于后续页面上展示
     *
     * @param categories
     */
    void fillByRow(List<Category> categories);


    /**
     * 查询某个分类下的所有产品
     *
     * @param category
     * @return
     */
    List<Product> listByCategory(Category category);

}
