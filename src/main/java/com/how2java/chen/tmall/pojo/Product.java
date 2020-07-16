package com.how2java.chen.tmall.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-16 21:47:59
 */

@Table(name = "product")
@Entity
@Data
public class Product {

    private int id;


    /// TODO 待修改
    @Column(name = "cid")
    private int cid;

    @Transient
    private Category category;

    private String name;

    @Column(name = "subTitle")
    private String subTitle;

    @Column(name = "originalPrice")
    private float originalPrice;

    @Column(name = "promotePrice")
    private float promotePrice;

    private int stock;

    @Column(name = "createDate")
    private Date createDate;


}
