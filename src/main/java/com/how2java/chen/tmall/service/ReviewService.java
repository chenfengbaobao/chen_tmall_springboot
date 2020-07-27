package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.Review;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-27 22:19:17
 */

public interface ReviewService {


    void add(Review review);


    List<Review> list(Product product);


    int getCount(Product product);

}
