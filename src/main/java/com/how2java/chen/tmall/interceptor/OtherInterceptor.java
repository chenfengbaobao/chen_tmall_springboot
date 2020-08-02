package com.how2java.chen.tmall.interceptor;

import com.how2java.chen.tmall.pojo.Category;
import com.how2java.chen.tmall.pojo.OrderItem;
import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.CategoryService;
import com.how2java.chen.tmall.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * @Author : haifeng.wu
 * @Date : 2020-08-01 15:58:22
 */

public class OtherInterceptor implements HandlerInterceptor {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderItemService orderItemService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int cartTotalNumber = 0;


        if (Objects.nonNull(user)) {

            List<OrderItem> orderItems = orderItemService.listByUser(user);
            for (OrderItem orderItem : orderItems) {
                cartTotalNumber += orderItem.getNumber();
            }
        }

        List<Category> categories = categoryService.list();

        String contextPath = request.getServletContext().getContextPath();

        request.getServletContext().setAttribute("categories_below_search", categories);

        session.setAttribute("cartTotalItemNumber", cartTotalNumber);

        request.getServletContext().setAttribute("contextPath", contextPath);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
