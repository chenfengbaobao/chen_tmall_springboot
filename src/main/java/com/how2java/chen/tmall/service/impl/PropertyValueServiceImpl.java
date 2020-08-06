package com.how2java.chen.tmall.service.impl;

import com.how2java.chen.tmall.dao.PropertyValueMapper;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.Property;
import com.how2java.chen.tmall.pojo.PropertyValue;
import com.how2java.chen.tmall.service.ProductService;
import com.how2java.chen.tmall.service.PropertyService;
import com.how2java.chen.tmall.service.PropertyValueService;
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
 * @Date : 2020-07-25 19:26:38
 */

@CacheConfig(cacheNames = "propertyValues")
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ProductService productService;


    @CacheEvict(allEntries = true)
    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKey(propertyValue);
    }

    @Override
    public void init(Product product) {

        List<Property> properties = propertyService.listByCategory(product.getCategory());

        for (Property property : properties) {

            PropertyValue propertyValue = getByProductAndProduct(product, property);


            if (Objects.isNull(propertyValue)) {

                propertyValue = new PropertyValue();


                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());

                propertyValueMapper.insertSelective(propertyValue);

            }
        }

    }

    @Cacheable(key = "'propertyValues-one-pid'+#p0.id+'-ptid-'+#p1.id")
    @Override
    public PropertyValue getByProductAndProduct(Product product, Property property) {
        Example example = new Example(PropertyValue.class);
        example.createCriteria()
                .andEqualTo("pid", product.getId())
                .andEqualTo("ptid", property.getId());

        example.setOrderByClause("id desc");


        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);

        if (!CollectionUtils.isEmpty(propertyValues)) {
            return propertyValues.get(0);
        }

        return null;
    }


    @Cacheable(key = "'propertyValues-pid-'+#p0.id")
    @Override
    public List<PropertyValue> list(Product product) {

        Example example = new Example(PropertyValue.class);
        example.createCriteria()
                .andEqualTo("pid", product.getId());

        example.setOrderByClause("id desc");


        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);

        for (PropertyValue propertyValue : propertyValues) {
            setCategory(propertyValue);
            setProperty(propertyValue);
        }


        return propertyValues;
    }


    private void setCategory(PropertyValue propertyValue) {
        Product product = productService.get(propertyValue.getPid());
        propertyValue.setProduct(product);
    }

    private void setProperty(PropertyValue propertyValue) {

        Property property = propertyService.get(propertyValue.getPtid());

        propertyValue.setProperty(property);
    }


}
