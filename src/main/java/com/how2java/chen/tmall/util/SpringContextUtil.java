package com.how2java.chen.tmall.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author : haifeng.wu
 * @Date : 2020-08-06 19:52:22
 */


@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    public SpringContextUtil() {

    }

    public static <T> T getBean(Class<T> clazz) {

        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextUtil.applicationContext = applicationContext;

    }


}
