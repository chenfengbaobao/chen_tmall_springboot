package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.chen.tmall.dao.PropertyMapper;
import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.pojo.Property;
import com.how2java.chen.tmall.service.CategoryService;
import com.how2java.chen.tmall.service.PropertyService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 16:01:50
 */

@Service
@CacheConfig(cacheNames = "properties")
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private CategoryService categoryService;


    @CacheEvict(allEntries = true)
    @Override
    public Integer add(Property property) {
        property.setCid(property.getCategory().getId());
        int generatedKeys = propertyMapper.insertUseGeneratedKeys(property);
        return generatedKeys;
    }


    @CacheEvict(allEntries = true)
    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }


    @Cacheable(key = "'properties-one-'+#p0")
    @Override
    public Property get(int id) {
        Property property = propertyMapper.selectByPrimaryKey(id);

        setCategory(property);

        return property;
    }


    @CacheEvict(allEntries = true)
    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Cacheable(key = "'properties-cid-'+#p0+'-page-'+#p1 +'-' + #p2")
    @Override
    public Page4Navigator<Property> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);


        Example example = new Example(Property.class);
        example.createCriteria()
                .andEqualTo("cid", cid);
        example.setOrderByClause("id desc");

        PageHelper.startPage(start, size);
        List<Property> properties = propertyMapper.selectByExample(example);
        for (Property property : properties) {
            property.setCategory(category);
        }

        PageInfo<Property> pageInfo = new PageInfo<>(properties);

        return new Page4Navigator<>(pageInfo, navigatePages);
    }

    @Cacheable(key = "'properties-cid-'+#p0.id")
    @Override
    public List<Property> listByCategory(Category category) {

        Example example = new Example(Property.class);
        example.createCriteria()
                .andEqualTo("cid", category.getId());

        List<Property> properties = propertyMapper.selectByExample(example);

//        for (Property property : properties) {
//            setCategory(property);
//        }

        return properties;
    }


    private void setCategory(Property property) {

        Category category = categoryService.get(property.getCid());

        property.setCategory(category);

    }
}
