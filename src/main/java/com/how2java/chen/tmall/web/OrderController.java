package com.how2java.chen.tmall.web;

import com.how2java.chen.tmall.configure.OrderStatusEnum;
import com.how2java.chen.tmall.pojo.Order;
import com.how2java.chen.tmall.query.PageQuery;
import com.how2java.chen.tmall.service.OrderItemService;
import com.how2java.chen.tmall.service.OrderService;
import com.how2java.chen.tmall.util.Page4Navigator;
import com.how2java.chen.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 16:27:51
 */

@RestController
public class OrderController {


    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/orders")
    public Page4Navigator<Order> list(PageQuery pageQuery) {

        Page4Navigator<Order> page = orderService.list(pageQuery.getStart(), pageQuery.getSize(), 5);

        orderItemService.fill(page.getContent());

        orderService.removeFromOrderItem(page.getContent());

        return page;
    }


    @PutMapping("/deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable("oid") int oid) {

        Order order = orderService.get(oid);
        order.setDeliveryDate(new Date());
        order.setStatus(OrderStatusEnum.WAIT_CONFIRM.getOrderStatus());

        orderService.update(order);

        return Result.success();
    }


}
