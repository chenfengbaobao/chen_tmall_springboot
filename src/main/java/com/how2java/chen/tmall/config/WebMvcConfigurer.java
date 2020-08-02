package com.how2java.chen.tmall.config;

import com.how2java.chen.tmall.interceptor.LoginInterceptor;
import com.how2java.chen.tmall.interceptor.OtherInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author : haifeng.wu
 * @Date : 2020-08-01 15:54:18
 */


@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {


    @Bean
    public LoginInterceptor getLoginInterceptor() {

        return new LoginInterceptor();
    }

    @Bean
    public OtherInterceptor getOtherInterceptor() {

        return new OtherInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getLoginInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(getOtherInterceptor())
                .addPathPatterns("/**");
    }


}
