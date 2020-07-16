package com.how2java.chen.tmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author apple
 */
@SpringBootApplication
@MapperScan("com.how2java.chen.tmall.dao")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.out.println();
        System.out.println("CHEN TMALL SPRING BOOT STRAT SUCCESS ^_^ !");
        System.out.println("CHEN TMALL SPRING BOOT STRAT SUCCESS ^_^ !!");
        System.out.println("CHEN TMALL SPRING BOOT STRAT SUCCESS ^_^ !!!");
        System.out.println();


    }
}
