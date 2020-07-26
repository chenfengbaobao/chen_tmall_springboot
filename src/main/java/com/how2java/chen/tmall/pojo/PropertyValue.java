package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 19:13:35
 */

@Table(name = "propertyValue")
@Entity
@Data
public class PropertyValue extends BaseEntity {


    /**
     * product id
     */
    @Column(name = "pid")
    private Integer pid;


    /**
     * property id
     */
    @Column(name = "ptid")
    private Integer ptid;


    @Column(name = "value")
    private String value;


    @Transient
    private Product product;

    @Transient
    private Property property;


}
