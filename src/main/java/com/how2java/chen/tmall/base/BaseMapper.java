package com.how2java.chen.tmall.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-14 22:45:53
 */

public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
