//package com.how2java.chen.tmall.aspect;
//
//import com.alibaba.fastjson.JSON;
//import com.how2java.chen.tmall.query.PageQuery;
//import com.how2java.chen.tmall.util.Page4Navigator;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
///**
// * @Author : haifeng.wu
// * @Date : 2020-07-15 21:11:21
// */
//
//
//@Component
//@Aspect
//@Slf4j
//public class ParamDebugAop {
//
//
//    @Pointcut("@annotation(org.springframework.web.bind.annotation.Mapping)"
//            + "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)"
//            + "|| @annotation(org.springframework.web.bind.annotation.GetMapping)"
//            + "|| @annotation(org.springframework.web.bind.annotation.PostMapping)"
//    )
//
//    public void webPoint() {
//
//    }
//
//    @Around("webPoint()")
//    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object returnValue = null;
//        try {
//            returnValue = joinPoint.proceed();
//        } catch (Exception throwable) {
//            throwable.printStackTrace();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        } finally {
//            writeLog(joinPoint, returnValue);
//        }
//
//
//        writeLog(joinPoint, startTime);
//
//        return returnValue;
//
//    }
//
//
//    /**
//     * @param point
//     * @param responseValue
//     */
//    private void writeLog(ProceedingJoinPoint point, Object responseValue) {
//
//
//        StringBuilder sb = new StringBuilder(2000);
//
//    }
//
//    /**
//     * AOP 记录日志信息
//     *
//     * @param joinPoint
//     * @param startTime
//     * @throws Throwable
//     */
//    private void writeLog(ProceedingJoinPoint joinPoint, long startTime) throws Throwable {
//
////        Object[] args1 = joinPoint.getArgs();
////        if (args1 != null && args1.length > 0) {
////
////            Object arg = args1[0];
////            if (arg instanceof PageQuery) {
////                PageQuery pageQuery = (PageQuery) arg;
////
////
////                log.info("pageQuery before :{}", JSON.toJSON(pageQuery));
////
////                pageQuery.setStart(pageQuery.getStart() + 1);
////
////                log.info("pageQuery after :{}", JSON.toJSON(pageQuery));
////            }
////
////        }
//
////        Object proceed = joinPoint.proceed();
////        if (proceed instanceof Page4Navigator) {
////            Page4Navigator page4Navigator = (Page4Navigator) proceed;
////
////
////            log.info("page4Navigator before :{}", JSON.toJSON(page4Navigator));
////
////            page4Navigator.setNumber(page4Navigator.getNumber() - 1);
////
////            log.info("pageQuery after :{}", JSON.toJSON(page4Navigator));
////        }
//
//
////        StringBuilder sb = new StringBuilder(2000);
////
////        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
////
////        HttpServletRequest request = attributes.getRequest();
////
////        sb.append("\r\n    ");
////        sb.append("========================================== ParamDebugAop AOP Start ==========================================");
////        // 打印请求 url
////        sb.append("\r\n    请求地址URL：");
////        sb.append(request.getRequestURL().toString());
////
////        // 打印请求的 IP
////        sb.append("\r\n    请求地址IP：");
////        sb.append(request.getRemoteAddr());
////
////        sb.append("\r\n    HTTP Method：");
////        sb.append(request.getMethod());
////
////
////        // 打印调用 controller 的全路径以及执行方法
////        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
////        sb.append("\r\n    Class Method ：");
////        sb.append(method);
////
////
////        Object[] args = joinPoint.getArgs();
////        if (args != null) {
////            List<Object> argsList = new ArrayList<>();
////            for (Object arg : args) {
////
////                // 如果没有响应参数跳过，不然会报异常
////                if (arg instanceof ServletRequest || arg instanceof ServletResponse) {
////                    continue;
////                }
////                argsList.add(arg);
////
////            }
////
////
////            // 打印请求入参
////            sb.append("\r\n    请求参数：");
////            sb.append(JSON.toJSONString(argsList));
////
////        } else {
////            sb.append("\r\n    无请求参数!!!");
////        }
////
////
////        sb.append("\r\n    请求响应结果：");
////        sb.append(JSON.toJSONString(joinPoint.proceed()));
////        // 执行耗时
////        sb.append("\r\n    请求耗时：");
////        sb.append(System.currentTimeMillis() - startTime);
////        sb.append(" 毫秒");
////
////        sb.append("\r\n    ");
////        sb.append("========================================== ParamDebugAop AOP End ============================================");
////        log.info(sb.toString());
//
//    }
//
//}
