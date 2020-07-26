package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.pojo.Property;
import com.how2java.chen.tmall.util.Page4Navigator;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 16:01:43
 */

public interface PropertyService {

    Integer add(Property property);

    void delete(int id);

    Property get(int id);

    void update(Property property);

    Page4Navigator<Property> list(int cid, int start, int size, int navigatePages);


    /**
     * 根据分类获取所有集合
     *
     * @param category
     * @return
     */
    List<Property> listByCategory(Category category);


}
