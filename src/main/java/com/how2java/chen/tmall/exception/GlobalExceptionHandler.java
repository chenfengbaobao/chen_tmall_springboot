package com.how2java.chen.tmall.exception;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author apple
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {


        log.info("Error.GlobalExceptionHandler.exception:{}", JSON.toJSONString(e));
        e.printStackTrace();


        Class aClass = Class.forName("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException");

        Class aClass1 = Class.forName("org.springframework.dao.DataIntegrityViolationException");


        if (Objects.nonNull(e.getCause()) && Objects.equals(aClass, e.getCause().getClass())) {
            return "违反了约束，多半是外键约束";
        }

        if (Objects.nonNull(e.getCause()) && Objects.equals(aClass1, e.getCause().getClass())) {
            return "    2 违反了约束，多半是外键约束";
        }

        if (Objects.nonNull(e.getClass()) && Objects.equals("java.lang.ClassCastException", e.getClass().getName())) {
            return "    3 违反了约束，多半是外键约束";
        }

        return e.getMessage();
    }

}

