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
import java.util.Objects;

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


    /**
     * 产品页
     *
     * @param pid
     * @return
     */
    @GetMapping("/foreproduct/{pid}")
    public Object produt(@PathVariable("pid") int pid) {


        // 1、根据pid参数获取product对象
        Product product = productService.get(pid);

        // 2、根据对象product，获取这个产品对应的【单个】图片集合
        List<ProductImage> singleProductImages = productImageService.listSingleProductImages(product);


        // 3、根据对象product，获取这个产品对应的【详情】图片集合
        List<ProductImage> detailProductImages = productImageService.listDetailProductImages(product);


        // 4、获取产品的所有属性值
        List<PropertyValue> propertyValues = propertyValueService.list(product);


        // 5、获取该产品的所有评价
        List<Review> reviews = reviewService.list(product);


        // 6、设置产品所有的销量和评价
        productService.setSaleAndReviewNumber(product);

        productImageService.setFirstProductImage(product);

        product.setProductSingleImages(singleProductImages);
        product.setProductDetailImages(detailProductImages);


        // 8、把上述所有结果放入map中，返回给前端
        Map<String, Object> result = Maps.newHashMap();

        result.put("product", product);
        result.put("pvs", propertyValues);
        result.put("reviews", reviews);

        return Result.success(result);
    }


    /**
     * 校验是否登录
     *
     * @return
     */
    @GetMapping("/forecheckLogin")
    public Object checkLogin(HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (Objects.isNull(user)) {
            return Result.fail("用户未登录");
        }

        return Result.success();


    }


}
