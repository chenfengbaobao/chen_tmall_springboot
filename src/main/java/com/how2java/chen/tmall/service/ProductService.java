package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.util.Page4Navigator;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-16 21:58:00
 */

public interface ProductService {

    void add(Product product);


    void delete(int id);

    Product get(int id);

   void update(Product product);

   Page4Navigator<Product> list(int cid,int start,int size,int navigatePages);


}
