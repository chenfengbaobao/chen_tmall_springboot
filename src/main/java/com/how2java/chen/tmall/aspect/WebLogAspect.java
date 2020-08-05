//package com.how2java.chen.tmall.aspect;
//
///**
// * @Author : haifeng.wu
// * @Date : 2020-07-15 19:41:03
// */
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.Lists;
//import com.how2java.chen.tmall.query.PageQuery;
//import com.how2java.chen.tmall.util.Page4Navigator;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//import java.util.Objects;
//
//
//@Aspect
//@Component
//@Slf4j
//public class WebLogAspect {
//
//
//    /**
//     * 以 controller 包下定义的所有请求为切入点
//     */
//
//    @Pointcut("execution(public * com.how2java.chen.tmall.web..*.*(..))")
//    public void webLog() {
//
//    }
//
//    /**
//     * 在切点之前织入
//     *
//     * @throws Throwable
//     */
//    @Before("webLog()")
//    public void doBefore(JoinPoint joinPoint) throws Throwable {
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
//    @Around("webLog()")
//    public Object doAround(ProceedingJoinPoint joinPoint) {
//
//        long startTime = System.currentTimeMillis();
//
//
//        Object returnValue = null;
//        try {
//
//            // 处理输入参数
//            processInputArg(joinPoint.getArgs());
//
//            returnValue = joinPoint.proceed();
//
//
//            writeLog(joinPoint, returnValue, startTime);
//
//
////            处理返回对象
//            processOutPutObj(returnValue);
//
//        } catch (FileNotFoundException ex) {
//            log.info("FileNotFoundException.输出对象是文件");
//
//            return "FileNotFoundException.输出对象是文件";
//        } catch (IOException ex) {
//            log.info("IOException.输出对象是文件");
//
//            return "IOException.输出对象是文件";
//        } catch (Throwable throwable) {
//            log.info("有异常抛出.Error.WebLogAspect.exception:{}", JSON.toJSONString(throwable));
//
//            returnValue = throwable;
//            throwable.printStackTrace();
//        }
//
//        return returnValue;
//    }
//
//
//    /**
//     * 在切点之后织入
//     *
//     * @throws Throwable
//     */
//    @After("webLog()")
//    public void doAfter() throws Throwable {
//
//    }
//
//
//    /**
//     * AOP 记录日志信息
//     *
//     * @param joinPoint
//     * @param responseValue
//     */
//    private void writeLog(ProceedingJoinPoint joinPoint, Object responseValue, long startTime) {
//        StringBuilder sb = new StringBuilder(2000);
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//
//        HttpServletRequest request = attributes.getRequest();
//
//        sb.append("\r\n    ");
//        sb.append("==========================================WebLogAspect AOP Start ==========================================");
//        // 打印请求 url
//        sb.append("\r\n    请求地址URL：");
//        sb.append(request.getRequestURL().toString());
//
//        // 打印请求的 IP
//        sb.append("\r\n    请求地址IP：");
//        sb.append(request.getRemoteAddr());
//
//        sb.append("\r\n    HTTP Method：");
//        sb.append(request.getMethod());
//
//        // 打印调用 controller 的全路径以及执行方法
//        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
//        sb.append("\r\n    Class Method ：");
//        sb.append(method);
//
//
//        Object[] args = joinPoint.getArgs();
//        if (args != null) {
//            List<Object> argsList = Lists.newArrayList();
//            for (Object arg : args) {
//
//                // 如果没有响应参数跳过，不然会报异常
//                if (arg instanceof ServletRequest || arg instanceof ServletResponse) {
//                    continue;
//                }
//                if (arg instanceof MultipartFile) {
//                    arg = "请求参数是文件，不再输出字节";
//                }
//                argsList.add(arg);
//
//            }
//
//
//            // 打印请求入参
//            sb.append("\r\n    请求参数：");
//            sb.append(JSON.toJSONString(argsList));
//
//        } else {
//            sb.append("\r\n    无请求参数!!!");
//        }
//
//
//        sb.append("\r\n    请求响应结果：");
//        if (Objects.isNull(responseValue)) {
//
//            sb.append("响应结果为空");
//        } else {
//
//            sb.append(JSON.toJSONString(responseValue));
//        }
//        // 执行耗时
//        sb.append("\r\n    请求耗时：");
//        sb.append(System.currentTimeMillis() - startTime);
//        sb.append(" 毫秒");
//
//        sb.append("\r\n    ");
//        sb.append("========================================== WebLogAspect AOP End ============================================");
//
//        log.info(sb.toString());
//
//    }
//
//
//    /**
//     * 处理输入参数
//     *
//     * @param args 入参列表
//     */
//    private void processInputArg(Object[] args) {
//
//
//        if (args != null && args.length > 0) {
//
//
//            for (Object arg : args) {
//                if (arg instanceof PageQuery) {
//                    PageQuery pageQuery = (PageQuery) arg;
//
//
//                    log.info("pageQuery before :{}", JSON.toJSON(pageQuery));
//
//                    pageQuery.setStart(pageQuery.getStart() + 1);
//
//                    log.info("pageQuery after  :{}", JSON.toJSON(pageQuery));
//                }
//
//            }
//
//        }
//
//
//    }
//
//
//    /**
//     * 处理返回对象
//     *
//     * @param proceed
//     */
//    private void processOutPutObj(Object proceed) {
//
//        if (null == proceed) {
//            return;
//        }
//
//
//        if (proceed instanceof Page4Navigator) {
//            Page4Navigator page4Navigator = (Page4Navigator) proceed;
//
//
//            log.info("page4Navigator before :{}", JSON.toJSON(page4Navigator));
//
//
//            if (page4Navigator.isLast()) {
//                log.info("page4Navigator  is     last");
//            } else {
//
//                page4Navigator.setNumber(page4Navigator.getNumber() - 1);
//
//                log.info("page4Navigator  after :{}", JSON.toJSON(page4Navigator));
//            }
//
//
//        }
//    }
//
//
//}
