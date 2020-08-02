package com.how2java.chen.tmall.service;

import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.User;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:06:15
 */

public interface OrderItemService {

    void fill(List<Order> orders);


    void fill(Order order);

    void update(OrderItem orderItem);

    Integer add(OrderItem orderItem);

    void delete(int id);

    OrderItem get(int id);

    int getSaleCount(Product product);


    List<OrderItem> listByOrder(Order order);


    List<OrderItem> listByProduct(Product product);


    List<OrderItem> listByUser(User user);


}
