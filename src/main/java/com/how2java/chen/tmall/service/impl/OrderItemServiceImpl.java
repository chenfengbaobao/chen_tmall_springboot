package com.how2java.chen.tmall.service.impl;

import com.how2java.chen.tmall.dao.OrderItemMapper;
import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.OrderItemService;
import com.how2java.chen.tmall.service.ProductImageService;
import com.how2java.chen.tmall.service.ProductService;
import com.how2java.chen.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:15:32
 */


@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders) {
            fill(order);
        }
    }

    @Override
    public void fill(Order order) {

        List<OrderItem> orderItems = listByOrder(order);

        setProduct(orderItems);
        setUser(orderItems);

        float total = 0;
        int totalNumber = 0;

        for (OrderItem item : orderItems) {
            total += item.getNumber() * item.getProduct().getPromotePrice();
            totalNumber += item.getNumber();


            productImageService.setFirstProductImage(item.getProduct());
        }


        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(orderItems);

    }


    @Override
    public void update(OrderItem orderItem) {

        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public void add(OrderItem orderItem) {

        orderItemMapper.insertUseGeneratedKeys(orderItem);
    }

    @Override
    public void delete(int id) {

        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public OrderItem get(int id) {
        return orderItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int getSaleCount(Product product) {
        int result = 0;
        List<OrderItem> orderItems = listByProduct(product);



        for (OrderItem orderItem : orderItems) {

            if (null != orderItem.getOrder() && null != orderItem.getOrder().getPayDate()) {

                result += orderItem.getNumber();

            }
        }


        return result;
    }

    @Override
    public List<OrderItem> listByOrder(Order order) {

        Example example = new Example(OrderItem.class);
        example.createCriteria()
                .andEqualTo("oid", order.getId());

        example.setOrderByClause("id desc");

        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);


        return orderItems;
    }

    @Override
    public List<OrderItem> listByProduct(Product product) {

        Example example = new Example(OrderItem.class);
        example.createCriteria()
                .andEqualTo("pid", product.getId());

        example.setOrderByClause("id desc");

        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);

        setProduct(orderItems);

        return orderItems;
    }


    private void setProduct(List<OrderItem> orderItems) {

        for (OrderItem orderItem : orderItems) {
            Product product = productService.get(orderItem.getPid());
            orderItem.setProduct(product);
        }

    }

    private void setUser(List<OrderItem> orderItems) {

        for (OrderItem item : orderItems) {
            User user = userService.get(item.getUid());
            item.setUser(user);
        }

    }
}
