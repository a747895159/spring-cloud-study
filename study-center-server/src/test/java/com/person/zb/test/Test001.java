package com.person.zb.test;

import com.person.zb.center.CenterBootstrap;
import com.person.zb.test.api.UserApi;
import feign.Feign;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : ZhouBin
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CenterBootstrap.class})
@Slf4j
public class Test001 {

    @Test
    public void test1() {
        UserApi client = Feign.builder()
                //设置连接和读超时间都是5s
                .options(new Request.Options(2000, 1000))
                .target(UserApi.class, "http://localhost:18088");
        log.info("开始执行");
        try {
            client.queryUser();
        } catch (Exception e) {
            log.info("执行异常", e);
        }
        log.info("结束执行111");
        log.info("结束执行111");
    }
}
