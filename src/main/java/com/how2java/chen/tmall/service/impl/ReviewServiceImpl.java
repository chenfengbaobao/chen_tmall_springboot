package com.how2java.chen.tmall.service.impl;

import com.how2java.chen.tmall.dao.ReviewMapper;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.Review;
import com.how2java.chen.tmall.service.ReviewService;
import com.how2java.chen.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-27 22:21:11
 */

@Service
@CacheConfig(cacheNames = "reviews")
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private UserService userService;


    @CacheEvict(allEntries = true)
    @Override
    public void add(Review review) {

        reviewMapper.insertUseGeneratedKeys(review);
    }


    @Cacheable(key = "'reviews-pid-'+#p0.id")
    @Override
    public List<Review> list(Product product) {

        Example example = new Example(Review.class);
        example.createCriteria()
                .andEqualTo("pid");
        example.setOrderByClause("id desc");


        List<Review> reviews = reviewMapper.selectByExample(example);

        setUser(reviews);

        return reviews;
    }


    @Cacheable(key = "'reviews-count-pid'+#p0.id")
    @Override
    public int getCount(Product product) {

        Example example = new Example(Review.class);
        example.createCriteria()
                .andEqualTo("pid");

        int count = reviewMapper.selectCountByExample(example);

        return count;
    }

    private void setUser(List<Review> reviews) {
        for (Review review : reviews) {


            review.setUser(userService.get(review.getUid()));
        }
    }
}
