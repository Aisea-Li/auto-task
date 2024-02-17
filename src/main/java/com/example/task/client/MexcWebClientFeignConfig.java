package com.example.task.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;


@Slf4j
public class MexcWebClientFeignConfig implements RequestInterceptor {

    @Value("${mexc.web.token:}")
    private String token;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.debug("token:{}", token);
        String cookieValue = String.format("u_id=%s; uc_token=%s;", token, token);
        requestTemplate.header("cookie", cookieValue);
    }

}
