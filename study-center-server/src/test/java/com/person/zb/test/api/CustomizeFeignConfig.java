package com.person.zb.test.api;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import feign.Request;
import feign.RequestInterceptor;
import feign.Util;
import feign.codec.Decoder;
import feign.codec.Encoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Charsets.UTF_8;

/**
 * 自定义FastJson 解码器， 此处 @Configuration 不能开启，否则全局生效
 * wms 所有入参与出参都是小写，系统交互时，对方返回的报文是 驼峰类型，wms无法解析， 使用此解码器 可以避免问题
 *
 * @author zhoubin
 */
@Slf4j
public class CustomizeFeignConfig {

    public static final ThreadLocal<Map<String, String>> HEADER_LOCAL = new ThreadLocal<>();

    /**
     * 自定义feign 接口响应内容解码器
     */
    @Bean
    public Decoder decoder() {
        /**
         * response 响应码 200-300 以及部分特殊条件下404 会走此实现
         */
        return (response, type) -> {
            if (response.body() == null) {
                return null;
            }
            String result = Util.toString(response.body().asReader());
            log.debug("feign接口返回: {}", result);
            return JSONObject.parseObject(result, type);
        };
    }

    /**
     * 自定义feign 接口请求编码器
     */
    @Bean
    public Encoder encoder() {
        return (body, bodyType, requestTemplate) -> {
            if (body != null) {
                requestTemplate.body(JSONObject.toJSONBytes(body), UTF_8);
                Map<String, Collection<String>> headerMap = new HashMap<>();
                requestTemplate.headers().forEach(headerMap::put);
                Collection<String> contentTypes = headerMap.get("Content-Type");
                if (CollectionUtils.isEmpty(contentTypes)) {
                    //添加默认请求头  application/json;charset=UTF-8
                    headerMap.put("Content-Type", Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8_VALUE));
                    requestTemplate.headers(null);
                    requestTemplate.headers(headerMap);
                }

            }
        };
    }

    @Bean
    public RequestInterceptor customizeInterceptor() {
        return template -> {
            Map<String, String> header = HEADER_LOCAL.get();
            if (header != null && header.size() > 0) {
                header.forEach(template::header);
                log.info("请求指定header信息:{}", JSONObject.toJSONString(header));
            }
        };
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(8, TimeUnit.SECONDS, 8, TimeUnit.SECONDS, true);
    }


}
