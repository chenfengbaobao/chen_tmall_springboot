package com.how2java.chen.tmall.comparator;

import com.how2java.chen.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 综合比较器
 * 把销量 * 评价 高的放在前面
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-29 22:22:02
 */

public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() * o2.getReviewCount() - o1.getSaleCount() * o1.getReviewCount();
    }
}
