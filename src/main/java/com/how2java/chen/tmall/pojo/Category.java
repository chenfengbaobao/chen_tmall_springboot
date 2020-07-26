package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Entity
@Table(name = "category")
@Data
public class Category extends BaseEntity {


    @Column(name = "name")
    String name;

    @Transient
    List<Product> products;


    @Transient
    List<List<Product>> productsByRow;


}
