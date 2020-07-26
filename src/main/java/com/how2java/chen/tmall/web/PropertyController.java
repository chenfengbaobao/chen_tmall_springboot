package com.how2java.chen.tmall.web;

import com.how2java.chen.tmall.pojo.Property;
import com.how2java.chen.tmall.query.PageQuery;
import com.how2java.chen.tmall.service.PropertyService;
import com.how2java.chen.tmall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-25 16:09:47
 */


@RestController
public class PropertyController {


    @Autowired
    private PropertyService propertyService;


    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(PageQuery pageQuery, @PathVariable("cid") int cid) {

        Page4Navigator<Property> list = propertyService.list(cid, pageQuery.getStart(), pageQuery.getSize(), 5);

        return list;
    }


    @GetMapping("properties/{id}")
    public Property get(@PathVariable("id") int id) {

        return propertyService.get(id);
    }

    @PostMapping("/properties")
    public Property add(@RequestBody Property property) {
        propertyService.add(property);

        return property;
    }


    @DeleteMapping("properties/{id}")
    public String delete(@PathVariable("id") int id) {
        propertyService.delete(id);

        return null;
    }


    @PutMapping("/properties")
    public Property update(@RequestBody Property property) {

        propertyService.update(property);

        return property;

    }

}
