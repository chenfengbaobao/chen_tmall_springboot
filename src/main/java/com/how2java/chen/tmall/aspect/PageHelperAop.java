//package com.how2java.chen.tmall.aspect;
//
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * @Author : haifeng.wu
// * @Date : 2020-07-15 22:31:16
// */
//
//@Component
//@Aspect
//@Slf4j
//public class PageHelperAop {
//
//    /**
//     * 以 controller 包下定义的所有请求为切入点
//     */
//
//    @Pointcut("execution(public * com.how2java.chen.tmall.web..*.*(..))")
//    public void pageHelper() {
//
//    }
//
//    /**
//     * 在切点之前织入
//     *
//     * @throws Throwable
//     */
//    @Before("pageHelper()")
//    public void doBefore() throws Throwable {
//
//    }
//
//
//    /**
//     * 环绕
//     *
//     * @param joinPoint
//     * @return
//     * @throws Throwable
//     */
//    @Around("pageHelper()")
//    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//
//    }
//}
