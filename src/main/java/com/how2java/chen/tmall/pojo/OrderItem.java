package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 15:24:12
 */

@Table(name = "orderItem")
@Entity
@Data
public class OrderItem extends BaseEntity {


    private Integer pid;


    private Integer oid;


    private Integer uid;


    private Integer number;

    @Transient
    private Product product;


    @Transient
    private Order order;


    @Transient
    private User user;


}
