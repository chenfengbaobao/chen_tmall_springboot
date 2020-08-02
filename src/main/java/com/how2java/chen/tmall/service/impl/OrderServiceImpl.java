package com.how2java.chen.tmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.chen.tmall.dao.OrderMapper;
import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.OrderItemService;
import com.how2java.chen.tmall.service.OrderService;
import com.how2java.chen.tmall.service.UserService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private OrderItemService orderItemService;


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
    public void removeOrderFromOrderItem(List<Order> orders) {
        for (Order order : orders) {
            removeOrderFromOrderItem(order);
        }
    }

    @Override
    public void removeOrderFromOrderItem(Order order) {
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


    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public float add(Order order, List<OrderItem> ois) {
        float total = 0;

        add(order);

        if (false) {
            throw new RuntimeException();
        }

        for (OrderItem item : ois) {
            item.setOid(order.getId());

            orderItemService.update(item);

            total += item.getProduct().getPromotePrice() * item.getNumber();

        }

        return total;

    }

    @Override
    public void add(Order order) {
        orderMapper.insertUseGeneratedKeys(order);
    }

    @Override
    public List<Order> listByUserWithoutDelete(User user) {
        List<Order> orders = listByUserAndNotDelete(user);

        orderItemService.fill(orders);

        return orders;
    }

    @Override
    public List<Order> listByUserAndNotDelete(User user) {
        Example example = new Example(Order.class);

        example.createCriteria()
                .andEqualTo("uid", user.getId())
                .andIsNotNull("status");

        example.setOrderByClause("id desc");

        List<Order> orders = orderMapper.selectByExample(example);


        return orders;
    }

    @Override
    public void cacl(Order order) {

        List<OrderItem> orderItems = order.getOrderItems();
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }

        order.setTotal(total);
    }


    private void setUser(List<Order> orders) {

        for (Order order : orders) {
            User user = userService.get(order.getUid());
            order.setUser(user);
        }

    }


}
