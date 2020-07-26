package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.Property;
import com.how2java.chen.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 19:19:16
 */

public interface PropertyValueService {

    void update(PropertyValue propertyValue);

    void init(Product product);

    PropertyValue getByProductAndProduct(Product product, Property property);

    List<PropertyValue> list(Product product);


}
