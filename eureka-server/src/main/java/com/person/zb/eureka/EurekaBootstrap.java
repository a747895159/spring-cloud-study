package com.person.zb.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Desc:
 * @Author: ZhouBin
 * @Date: 2021/9/5
 */
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class EurekaBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(EurekaBootstrap.class, args);
        log.info("Bootstrap started successfully");
    }
}
