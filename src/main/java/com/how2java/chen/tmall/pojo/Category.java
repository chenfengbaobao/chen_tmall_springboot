package com.how2java.chen.tmall.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @Column(name = "id")
    int id;

    @Column(name = "name")
    String name;

}
