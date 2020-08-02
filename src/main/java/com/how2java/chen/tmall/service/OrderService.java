package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.util.Page4Navigator;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:06:29
 */

public interface OrderService {


    /**
     * Order list
     *
     * @param start
     * @param size
     * @param navigatePages
     * @return
     */
    Page4Navigator<Order> list(int start, int size, int navigatePages);

    void removeOrderFromOrderItem(List<Order> orders);

    void removeOrderFromOrderItem(Order order);

    Order get(int id);

    void update(Order order);


    float add(Order order, List<OrderItem> ois);

    void add(Order order);


    List<Order> listByUserWithoutDelete(User user);

    List<Order> listByUserAndNotDelete(User user);

    void cacl(Order order);

}
