package com.how2java.chen.tmall;

import com.how2java.chen.tmall.util.PortUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author apple
 */

// 用于启动缓存
@EnableCaching
@SpringBootApplication
@MapperScan("com.how2java.chen.tmall.dao")
public class Application {

    static {
        PortUtil.checkPort(6379, "Redis 服务端", true);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.out.println();
        System.out.println("CHEN TMALL SPRING BOOT STRAT SUCCESS ^_^ !");
        System.out.println("CHEN TMALL SPRING BOOT STRAT SUCCESS ^_^ !!");
        System.out.println("CHEN TMALL SPRING BOOT STRAT SUCCESS ^_^ !!!");
        System.out.println();


    }
}
