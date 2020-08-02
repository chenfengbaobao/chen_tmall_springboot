package com.how2java.chen.tmall.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.how2java.chen.tmall.comparator.*;
import com.how2java.chen.tmall.configure.OrderStatusEnum;
import com.how2java.chen.tmall.pojo.*;
import com.how2java.chen.tmall.service.*;
import com.how2java.chen.tmall.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author : haifeng.wu
 * @Date : 2020-07-26 22:21:09
 */

@Slf4j
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

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;


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
    public Object product(@PathVariable("pid") int pid) {


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

    @GetMapping("forecategory/{cid}")
    public Object category(@PathVariable("cid") int cid, String sort) {

        Category category = categoryService.get(cid);

        productService.fill(category);

        productService.setSaleAndReviewNumber(category.getProducts());

        categoryService.removeCategoryFromProduct(category);

        if (null != sort) {

            switch (sort) {

                case "review":
                    Collections.sort(category.getProducts(), new ProductReviewComparator());
                    break;

                case "date":
                    Collections.sort(category.getProducts(), new ProductDateComparator());
                    break;

                case "saleCount":
                    Collections.sort(category.getProducts(), new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(category.getProducts(), new ProductPriceComparator());
                    break;

                default:
                    Collections.sort(category.getProducts(), new ProductAllComparator());

            }


        }


        return category;
    }


    @PostMapping("/foresearch")
    public Object search(String keyword) {

        if (Objects.isNull(keyword)) {
            keyword = "";
        }

        List<Product> products = productService.search(keyword, 0, 20);

        productImageService.setFirstProductImage(products);

        productService.setSaleAndReviewNumber(products);

        return products;
    }


    /**
     * 用户购买商品
     *
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @GetMapping("forebuyone")
    public Object buyone(int pid, int num, HttpSession session) {

        return buyOneAndAddCart(pid, num, session);
    }


    private int buyOneAndAddCart(int pid, int num, HttpSession session) {

        Product product = productService.get(pid);

        int oiid = 0;

        User user = (User) session.getAttribute("user");
        boolean found = false;

        List<OrderItem> orderItems = orderItemService.listByUser(user);
        for (OrderItem orderItem : orderItems) {

            if (Objects.equals(orderItem.getId(), product.getId())) {

                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.update(orderItem);

                found = true;
                oiid = orderItem.getId();
                break;
            }

        }

        if (!found) {

            OrderItem item = new OrderItem();
            item.setUid(user.getId());
            item.setNumber(num);
            item.setPid(pid);

            Integer addId = orderItemService.add(item);

            oiid = item.getId();

            log.info("buyOneAndAddCart.addId:{},oiid:{}", addId, oiid);

        }

        return oiid;
    }


    @GetMapping("forebuy")
    public Object buy(String[] oiid, HttpSession session) {

        List<OrderItem> orderItems = Lists.newArrayList();

        float total = 0;
        for (String srtId : oiid) {

            int id = Integer.parseInt(srtId);

            OrderItem item = orderItemService.get(id);

            total += item.getProduct().getPromotePrice() * item.getNumber();

            orderItems.add(item);
        }


        productImageService.setFirstProductImagesOnOrderItems(orderItems);

        session.setAttribute("ois", orderItems);

        Map<String, Object> map = Maps.newHashMap();

        map.put("orderItems", orderItems);
        map.put("total", total);

        return Result.success(map);
    }

    @GetMapping("foreaddCart")
    public Object addCart(int pid, int num, HttpSession session) {
        buyOneAndAddCart(pid, num, session);

        return Result.success();
    }


    @GetMapping("forecart")
    public Object cart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user);
        productImageService.setFirstProductImagesOnOrderItems(ois);

        return ois;
    }


    @GetMapping("forechangeOrderItem")
    public Object changeOrderItem(HttpSession session, int pid, int num) {
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            return Result.fail("未登录");
        }


        List<OrderItem> ois = orderItemService.listByUser(user);
        for (OrderItem oi : ois) {
            if (oi.getProduct().getId() == pid) {
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }

        return Result.success();
    }

    @GetMapping("foredeleteOrderItem")
    public Object deleteOrderItem(HttpSession session, int oiid) {
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {

            return Result.fail("未登录");
        }
        orderItemService.delete(oiid);
        return Result.success();
    }


    @PostMapping("forecreateOrder")
    public Object createOrder(@RequestBody Order order, HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)) {
            return Result.fail("用户未登录");
        }

        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);


        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUid(user.getId());
        order.setStatus(OrderStatusEnum.WAIT_PAY.getOrderStatus());


        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");

        float total = orderService.add(order, ois);

        Map<String, Object> map = Maps.newHashMap();

        map.put("oid", order.getId());
        map.put("total", total);

        return Result.success(map);
    }


    @GetMapping("forepayed")
    public Object payed(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderStatusEnum.WAIT_DELIVERY.getOrderStatus());
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }


    @GetMapping("forebought")
    public Object bought(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (Objects.isNull(user)){

            return Result.fail("未登录");
        }
        List<Order> os = orderService.listByUserWithoutDelete(user);
        orderService.removeOrderFromOrderItem(os);
        return os;
    }


    @GetMapping("foreconfirmPay")
    public Object confirmPay(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        orderService.cacl(o);
        orderService.removeOrderFromOrderItem(o);
        return o;
    }


    @GetMapping("foreorderConfirmed")
    public Object orderConfirmed(int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderStatusEnum.WAIT_REVIEW.getOrderStatus());
        o.setConfirmDate(new Date());
        orderService.update(o);

        return Result.success();
    }

    @PutMapping("foredeleteOrder")
    public Object deleteOrder(int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderStatusEnum.DELETE.getOrderStatus());
        orderService.update(o);
        return Result.success();
    }


    @GetMapping("forereview")
    public Object review(int oid) {
        Order order = orderService.get(oid);

        orderItemService.fill(order);

        orderService.removeOrderFromOrderItem(order);

        Product product = order.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(product);

        productService.setSaleAndReviewNumber(product);

        Map<String, Object> map = Maps.newHashMap();

        map.put("p", product);
        map.put("o", order);
        map.put("reviews", reviews);


        return Result.success(map);
    }

    @PostMapping("foredoreview")
    public Object doreview(HttpSession session, int oid, int pid, String content) {

        Order order = orderService.get(oid);
        order.setStatus(OrderStatusEnum.FINISH.getOrderStatus());
        orderService.update(order);

        Product product = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user = (User) session.getAttribute("user");

        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setUid(user.getId());
        review.setCreateDate(new Date());

        reviewService.add(review);

        return Result.success();
    }

}
