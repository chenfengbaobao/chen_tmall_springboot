package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 17:09:47
 */


@Table(name = "productImage")
@Entity
@Data
public class ProductImage extends BaseEntity {

    @Column(name = "type")
    private String type;

    @Column(name = "pid")
    private Integer pid;


    @Transient
    private Product product;

}
