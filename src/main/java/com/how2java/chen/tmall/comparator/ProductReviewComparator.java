package com.how2java.chen.tmall.comparator;

import com.how2java.chen.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 人气比较器
 * <p>
 * 把评价数量多的放在前面
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-29 22:22:45
 */

public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() - o1.getReviewCount();
    }
}
