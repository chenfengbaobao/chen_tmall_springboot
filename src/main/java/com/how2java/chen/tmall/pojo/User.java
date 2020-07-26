package com.how2java.chen.tmall.pojo;

import com.how2java.chen.tmall.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 14:51:25
 */


@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity {


    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "salt")
    private String salt;


    @Transient
    private String anonymousName;


    /**
     * 获取用户匿名
     *
     * @return
     */
    public String getAnonymousName() {

        if (null != anonymousName) {
            return anonymousName;
        }


        if (null == name) {
            anonymousName = null;
        } else if (name.length() <= 1) {
            anonymousName = "*";
        } else if (name.length() == 2) {

            anonymousName = name.substring(0, 1) + "*";
        } else {
            char[] cs = name.toCharArray();
            for (int i = 1; i < cs.length - 1; i++) {
                cs[i] = '*';
            }
            anonymousName = new String(cs);
        }


        return anonymousName;
    }


}
