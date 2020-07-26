package com.how2java.chen.tmall.web;

import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.service.CategoryService;
import com.how2java.chen.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 22:21:09
 */


@RestController
public class ForeRESTController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


    @GetMapping("/forehome")
    public Object home() {

        List<Category> categories = categoryService.list();

        productService.fill(categories);

        productService.fillByRow(categories);

        categoryService.removeCategoryFromProduct(categories);

        return categories;

    }

}
