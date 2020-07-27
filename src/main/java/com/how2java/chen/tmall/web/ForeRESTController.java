package com.how2java.chen.tmall.web;

import com.google.common.collect.Maps;
import com.how2java.chen.tmall.pojo.*;
import com.how2java.chen.tmall.service.*;
import com.how2java.chen.tmall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserService userService;


    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private ReviewService reviewService;


    @GetMapping("/forehome")
    public Object home() {

        List<Category> categories = categoryService.list();

        productService.fill(categories);

        productService.fillByRow(categories);

        categoryService.removeCategoryFromProduct(categories);

        return categories;

    }


    /**
     * 前台用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/foreregister")
    public Object register(@RequestBody User user) {

        String name = user.getName();

        name = HtmlUtils.htmlEscape(name);

        user.setName(name);

        boolean exist = userService.isExist(name);
        if (exist) {
            String message = "用户名已经被使用，不能使用";
            return Result.fail(message);
        }

        userService.add(user);

        return Result.success();
    }


    @PostMapping("/forelogin")
    public Object login(@RequestBody User user, HttpSession session) {
        String name = user.getName();

        name = HtmlUtils.htmlEscape(name);
        User userInfo = userService.getByNameAndPassword(name, user.getPassword());

        if (null == userInfo) {

            return Result.fail("账户密码错误❌");
        }

        session.setAttribute("user", userInfo);

        return Result.success();

    }


    @GetMapping("/foreproduct/{pid}")
    public Object produt(@PathVariable("pid") int pid) {

        Product product = productService.get(pid);

        List<ProductImage> singleProductImages = productImageService.listSingleProductImages(product);


        List<ProductImage> detailProductImages = productImageService.listDetailProductImages(product);

        List<PropertyValue> propertyValues = propertyValueService.list(product);

        int count = reviewService.getCount(product);

        List<Review> reviews = reviewService.list(product);

        productService.setSaleAndReviewNumber(product);

        productImageService.setFirstProductImage(product);

        product.setProductSingleImages(singleProductImages);
        product.setProductDetailImages(detailProductImages);


        Map<String, Object> result = Maps.newHashMap();

        result.put("product", product);
        result.put("pvs", propertyValues);
        result.put("reviews", reviews);

        return Result.success(result);
    }


}
