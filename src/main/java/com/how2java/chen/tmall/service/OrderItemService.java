package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.pojo.OrderItem;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:06:15
 */

public interface OrderItemService {

    void fill(List<Order> orders);


    void fill(Order order);


    List<OrderItem> listByOrder(Order order);

}
