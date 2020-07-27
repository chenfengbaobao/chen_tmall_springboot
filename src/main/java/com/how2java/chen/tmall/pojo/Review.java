package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 评价表
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-27 22:14:16
 */


@Data
@Entity
@Table(name = "review")
public class Review extends BaseEntity {


    @Column(name = "uid")
    private Integer uid;


    @Column(name = "pid")
    private Integer pid;


    @Column(name = "content")
    private String content;


    @Column(name = "createDate")
    private Date createDate;

    @Transient
    private User user;


    @Transient
    private Product product;


}
