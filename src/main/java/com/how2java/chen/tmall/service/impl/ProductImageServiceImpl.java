package com.how2java.chen.tmall.service.impl;

import com.how2java.chen.tmall.configure.PictureEnum;
import com.how2java.chen.tmall.dao.ProductImageMapper;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.ProductImage;
import com.how2java.chen.tmall.service.ProductImageService;
import com.how2java.chen.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 17:13:43
 */


@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductService productService;


    @Override
    public Integer add(ProductImage productImage) {

        return productImageMapper.insertUseGeneratedKeys(productImage);
    }

    @Override
    public void delete(int id) {

        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProductImage get(int id) {

        ProductImage productImage = productImageMapper.selectByPrimaryKey(id);

        setProduct(productImage);

        return productImage;
    }

    @Override
    public List<ProductImage> listSingleProductImages(Product product) {

        Example example = new Example(ProductImage.class);
        example.createCriteria()
                .andEqualTo("pid", product.getId())
                .andEqualTo("type", PictureEnum.TYPE_SINGLE.getKey());
        example.setOrderByClause("id desc");

        List<ProductImage> images = productImageMapper.selectByExample(example);
        return images;
    }

    @Override
    public List<ProductImage> listDetailProductImages(Product product) {

        Example example = new Example(ProductImage.class);
        example.createCriteria()
                .andEqualTo("pid", product.getId())
                .andEqualTo("type", PictureEnum.TYPE_DETAIL.getKey());
        example.setOrderByClause("id desc");

        List<ProductImage> images = productImageMapper.selectByExample(example);

        return images;
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> singleImages = listSingleProductImages(product);
        if (!CollectionUtils.isEmpty(singleImages)) {
            product.setFirstProductImage(singleImages.get(0));
        } else {
            product.setFirstProductImage(new ProductImage());
        }
    }

    @Override
    public void setFirstProductImage(List<Product> product) {
        for (Product p : product) {
            setFirstProductImage(p);

        }
    }

    @Override
    public void setFirstProductImagesOnOrderItems(List<OrderItem> items) {
        for (OrderItem item : items) {
            setFirstProductImage(item.getProduct());
        }
    }


    private void setProduct(ProductImage productImage) {

        Product product = productService.get(productImage.getPid());
        productImage.setProduct(product);
    }
}
