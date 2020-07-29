package com.how2java.chen.tmall.comparator;

import com.how2java.chen.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-29 22:43:17
 */

public class ProductComparator implements Comparator<Product> {


    private String sort;


    public ProductComparator() {
    }

    public ProductComparator(String sort) {
        this.sort = sort;
    }

    @Override
    public int compare(Product o1, Product o2) {

        int result = 0;

        switch (sort) {

            case "review":
                result = o2.getReviewCount() - o1.getReviewCount();
                break;


            case "date":
                result = o2.getCreateDate().compareTo(o1.getCreateDate());
                break;


            case "saleCount":
                result = o2.getSaleCount() - o1.getSaleCount();
                break;


            case "price":
                result = (int) o1.getPromotePrice() - (int) o2.getPromotePrice();
                break;


            case "all":
                result = o2.getReviewCount() * o2.getSaleCount() - o1.getReviewCount() * o1.getSaleCount();
                break;


            default:

                result = 0;
                break;

        }


        return result;
    }
}
