package com.how2java.chen.tmall.base;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类基类
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-23 19:12:30
 */

@Data
public class BaseEntity implements Serializable {


    @Id
    @Column(name = "id")
    protected Integer id;

//    /***
//     * 创建时间
//     */
//    @Column(name = "gmtCreate")
//    protected Date gmtCreate;
//
//    /***
//     * 修改时间
//     */
//    @Column(name = "gmtModified")
//    protected Date gmtModified;
//
//    /***
//     * 创建人ID
//     */
//    @Column(name = "creator")
//    protected String creator;
//
//    /***
//     * 修改人ID
//     */
//    @Column(name = "updator")
//    protected String updator;
//
//    /***
//     * 有效标示：1有效 0无效
//     */
//    @Column(name = "is_usable")
//    protected Boolean usable;
}
