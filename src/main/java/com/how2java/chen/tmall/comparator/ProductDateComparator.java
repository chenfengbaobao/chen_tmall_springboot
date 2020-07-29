package com.how2java.chen.tmall.comparator;

import com.how2java.chen.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 新品比较器
 * <p>
 * 把最新创建日期放在前面
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-29 22:22:23
 */

public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getCreateDate().compareTo(o1.getCreateDate());
    }
}
