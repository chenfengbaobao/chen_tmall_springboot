package com.how2java.chen.tmall.comparator;

import com.how2java.chen.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 销量比较器
 * <p>
 * 把销量高的放在前面
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-29 22:23:01
 */

public class ProductSaleCountComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() - o1.getSaleCount();
    }
}
