package com.how2java.chen.tmall.web;

import com.how2java.chen.tmall.pojo.Product;
import com.how2java.chen.tmall.pojo.PropertyValue;
import com.how2java.chen.tmall.service.ProductService;
import com.how2java.chen.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 19:50:54
 */

@RestController
public class PropertyValueController {


    @Autowired
    private ProductService productService;

    @Autowired
    private PropertyValueService propertyValueService;

    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid) {

        Product product = productService.get(pid);

        propertyValueService.init(product);

        List<PropertyValue> propertyValues = propertyValueService.list(product);

        return propertyValues;

    }


    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue propertyValue) {


        propertyValueService.update(propertyValue);

        return propertyValue;
    }


}
