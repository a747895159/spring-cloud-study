package com.person.zb.test.api;

import feign.RequestLine;

public interface UserApi {

    @RequestLine(value = "POST /test/hello")
    void queryUser();
}
