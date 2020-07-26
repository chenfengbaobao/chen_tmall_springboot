package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 15:38:40
 */

@Table(name = "property")
@Entity
@Data
public class Property extends BaseEntity {


    @Column(name = "cid")
    private Integer cid;


    @Column(name = "name")
    private String name;


    @Transient
    private Category category;


}
