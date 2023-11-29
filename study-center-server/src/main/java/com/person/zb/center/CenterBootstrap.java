package com.person.zb.center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Desc:
 * @Author: ZhouBin
 * @Date: 2021/9/5
 */
@SpringBootApplication
@Slf4j
public class CenterBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(CenterBootstrap.class, args);
        log.info("Bootstrap started successfully");
    }
}
