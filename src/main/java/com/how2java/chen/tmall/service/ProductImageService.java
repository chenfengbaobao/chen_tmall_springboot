package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.ProductImage;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 17:13:33
 */

public interface ProductImageService {

    Integer add(ProductImage productImage);

    void delete(int id);

    ProductImage get(int id);


    /**
     * 根据产品ID和图片类型查询list
     *
     * @param product
     * @return
     */
    List<ProductImage> listSingleProductImages(Product product);


    /**
     * 据产品ID和图片类型查询list
     *
     * @param product
     * @return
     */
    List<ProductImage> listDetailProductImages(Product product);

    void setFirstProductImage(Product product);


    void setFirstProductImage(List<Product> product);


    void setFirstProductImagesOnOrderItems(List<OrderItem> items);


}
