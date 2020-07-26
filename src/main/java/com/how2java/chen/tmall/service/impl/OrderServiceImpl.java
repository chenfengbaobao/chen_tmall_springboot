package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.chen.tmall.dao.OrderMapper;
import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.OrderService;
import com.how2java.chen.tmall.service.UserService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:09:05
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserService userService;


    @Override
    public Page4Navigator<Order> list(int start, int size, int navigatePages) {

        Example example = new Example(Order.class);
        example.setOrderByClause("id desc");

        PageHelper.startPage(start, size);

        List<Order> orders = orderMapper.selectByExample(example);

        setUser(orders);

        PageInfo<Order> pageInfo = new PageInfo<>(orders);


        return new Page4Navigator<>(pageInfo, navigatePages);
    }

    @Override
    public void removeFromOrderItem(List<Order> orders) {
        for (Order order : orders) {
            removeFromOrderItem(order);
        }
    }

    @Override
    public void removeFromOrderItem(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem item : orderItems) {
            item.setOrder(null);
        }
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    private void setUser(List<Order> orders){

        for (Order order : orders) {
            User user = userService.get(order.getUid());
            order.setUser(user);
        }

    }
}
