package com.how2java.chen.tmall.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 前台页面跳转
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-26 22:20:25
 */

@Controller
public class ForePageController {
    @GetMapping(value = "/")
    public String index() {
        return "redirect:home";
    }

    @GetMapping(value = "/home")
    public String home() {
        return "fore/home";
    }
}