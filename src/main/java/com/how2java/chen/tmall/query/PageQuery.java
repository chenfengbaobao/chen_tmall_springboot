package com.how2java.chen.tmall.query;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-15 22:24:50
 */


@Getter
@Setter
public class PageQuery {


    /**
     * 页码
     */
    private Integer start = 1;


    /**
     * 每页页数
     */
    private Integer size = 5;


    /**
     * 导航栏数量
     */
    private Integer navigatePages = 5;


}
