package com.how2java.chen.tmall.comparator;

import com.how2java.chen.tmall.pojo.Product;

import java.util.Comparator;

/**
 * 价格比较器
 * <p>
 * 把价格低的放在前面
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-29 22:22:35
 */

public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return (int) o1.getPromotePrice() - (int) o2.getPromotePrice();
    }
}
