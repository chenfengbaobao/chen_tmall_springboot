package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.util.Page4Navigator;

import java.util.List;

public interface CategoryService {


    Page4Navigator<Category> list(int start, int size, int navigatePages);

    List<Category> list();

    void add(Category bean);

    void delete(int id);

    Category get(int id);

    void update(Category bean);

    void removeCategoryFromProduct(List<Category> categories);


    void removeCategoryFromProduct(Category category);


}
