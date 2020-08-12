package com.how2java.chen.tmall.es;

import com.how2java.chen.tmall.pojo.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author : haifeng.wu
 * @Date : 2020-08-06 20:29:02
 */

public interface ProductESDAO extends ElasticsearchRepository<Product, Integer> {

}
