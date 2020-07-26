package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import com.how2java.chen.tmall.configure.OrderStatusEnum;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 15:26:02
 */
@Table(name = "order_")
@Entity
@Data
public class Order extends BaseEntity {


    @Column(name = "uid")
    private Integer uid;

    @Column(name = "orderCode")
    private String orderCode;

    @Column(name = "address")
    private String address;

    @Column(name = "post")
    private String post;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "userMessage")
    private String userMessage;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "payDate")
    private Date payDate;

    @Column(name = "deliveryDate")
    private Date deliveryDate;

    @Column(name = "confirmDate")
    private Date confirmDate;

    @Column(name = "status")
    private String status;

    @Transient
    private User user;

    @Transient
    private List<OrderItem> orderItems;


    @Transient
    private float total;


    @Transient
    private int totalNumber;


    @Transient
    private String statusDesc;


    public String getStatusDesc() {


        return OrderStatusEnum.getOrderStatusDesc(status);
    }


}
