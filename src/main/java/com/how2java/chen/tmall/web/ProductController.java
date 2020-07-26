package com.how2java.chen.tmall.web;

import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.query.PageQuery;
import com.how2java.chen.tmall.service.ProductImageService;
import com.how2java.chen.tmall.service.ProductService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-16 22:09:55
 */


@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;


    @GetMapping("/categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable("cid") int cid, PageQuery pageQuery) {
        Page4Navigator<Product> page = productService.list(cid, pageQuery.getStart(), pageQuery.getSize(), pageQuery.getNavigatePages());

        productImageService.setFirstProductImage(page.getContent());
        return page;
    }


    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) {

        return productService.get(id);
    }


    @PostMapping("/products")
    public Object add(@RequestBody Product product) {
        product.setCreateDate(new Date());

        product.setCid(product.getCategory().getId());
        productService.add(product);

        return product;
    }


    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id) {
        productService.delete(id);
        return null;
    }

    @PutMapping("/products")
    public Object update(@RequestBody Product product) {

        productService.update(product);

        return product;
    }

}
