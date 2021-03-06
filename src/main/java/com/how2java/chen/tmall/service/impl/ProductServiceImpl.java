package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.how2java.chen.tmall.dao.ProductMapper;
import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.service.*;
import com.how2java.chen.tmall.util.Page4Navigator;
import com.how2java.chen.tmall.util.SpringContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-16 22:00:40
 */

@Service
@CacheConfig(cacheNames = "products")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ReviewService reviewService;


    @CacheEvict(allEntries = true)
    @Override
    public void add(Product product) {
        productMapper.insertSelective(product);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }


    @Cacheable(key = "'products-one-'+ #p0")
    @Override
    public Product get(int id) {

        Product product = productMapper.selectByPrimaryKey(id);

        setCategory(product);

        return product;
    }

    @CacheEvict(allEntries = true)
    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }


    @Cacheable(key = "'products-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
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

        ProductService productService = SpringContextUtil.getBean(ProductService.class);
        List<Product> products = productService.listByCategory(category);
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


    @Cacheable(key = "'products-cid-'+ #p0.id")
    @Override
    public List<Product> listByCategory(Category category) {

        Example example = new Example(Product.class);
        example.createCriteria()
                .andEqualTo("cid", category.getId());

        example.setOrderByClause("id desc");

        return productMapper.selectByExample(example);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> products) {

        for (Product product : products) {
            setSaleAndReviewNumber(product);
        }
    }

    @Override
    public void setSaleAndReviewNumber(Product product) {


        // 销量
        int saleCount = orderItemService.getSaleCount(product);


        // 评论
        int count = reviewService.getCount(product);

        product.setSaleCount(saleCount);

        product.setReviewCount(count);


    }

    @Override
    public List<Product> search(String keyWord, int start, int size) {

        Example example = new Example(Product.class);

        if (StringUtils.isNotBlank(keyWord) && !Objects.equals("null", keyWord)) {
            example.createCriteria()
                    .andLike("name", "%" + keyWord + "%");

        }

        example.setOrderByClause("id desc");

        PageHelper.startPage(start, size);

        List<Product> products = productMapper.selectByExample(example);

        return products;
    }


    private void setCategory(Product product) {
        Category category = categoryService.get(product.getCid());

        product.setCategory(category);
    }


}
