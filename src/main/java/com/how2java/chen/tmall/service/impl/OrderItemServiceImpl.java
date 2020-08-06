package com.how2java.chen.tmall.service.impl;

import com.how2java.chen.tmall.dao.OrderItemMapper;
import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:15:32
 */



@CacheConfig(cacheNames = "orderItems")
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

    @Autowired
    private OrderService orderService;

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


    @CacheEvict(allEntries = true)
    @Override
    public void update(OrderItem orderItem) {

        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }


    @CacheEvict(allEntries = true)
    @Override
    public Integer add(OrderItem orderItem) {

        return orderItemMapper.insertUseGeneratedKeys(orderItem);
    }


    @CacheEvict(allEntries = true)
    @Override
    public void delete(int id) {

        orderItemMapper.deleteByPrimaryKey(id);
    }



    @Cacheable(key = "'orderItems-one-' + #p0")
    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        Product product = productService.get(orderItem.getPid());

        orderItem.setProduct(product);

        return orderItem;
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


    @Cacheable(key = "'orderItems-oid'+#p0.id")
    @Override
    public List<OrderItem> listByOrder(Order order) {

        Example example = new Example(OrderItem.class);
        example.createCriteria()
                .andEqualTo("oid", order.getId());

        example.setOrderByClause("id desc");

        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);


        return orderItems;
    }



    @Cacheable(key = "'orderItems-pid'+#p0.id")
    @Override
    public List<OrderItem> listByProduct(Product product) {

        Example example = new Example(OrderItem.class);
        example.createCriteria()
                .andEqualTo("pid", product.getId());

        example.setOrderByClause("id desc");

        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);

        setProduct(orderItems);
        setOrder(orderItems);


        return orderItems;
    }


    @Cacheable(key = "'orderItems-uid'+#p0.id")
    @Override
    public List<OrderItem> listByUser(User user) {

        Example example = new Example(OrderItem.class);

        example.createCriteria()
                .andEqualTo("uid", user.getId())
                .andIsNull("oid");

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

    private void setOrder(List<OrderItem> orderItems) {

        for (OrderItem orderItem : orderItems) {
            if (Objects.nonNull(orderItem.getOid())) {
                Order order = orderService.get(orderItem.getOid());
                orderItem.setOrder(order);
            }

        }

    }

    private void setUser(List<OrderItem> orderItems) {

        for (OrderItem item : orderItems) {
            User user = userService.get(item.getUid());
            item.setUser(user);
        }

    }
}
