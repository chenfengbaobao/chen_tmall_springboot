package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-16 21:47:59
 */

@Table(name = "product")
@Entity
@Data
public class Product extends BaseEntity {


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


    /**
     *
     */
    @Transient
    private ProductImage firstProductImage;


    /**
     * 单个产品集合
     */
    @Transient
    private List<ProductImage> productSingleImages;

    /**
     * 详情产品图片集合
     */
    @Transient
    private List<ProductImage> productDetailImages;

    /**
     * 累计销量
     */
    @Transient
    private int saleCount;

    /**
     * 累计评价
     */
    @Transient
    private int reviewCount;

}
