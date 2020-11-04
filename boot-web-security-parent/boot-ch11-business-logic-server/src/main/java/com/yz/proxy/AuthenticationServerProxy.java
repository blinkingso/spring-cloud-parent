package com.yz.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Request to the  Authentication Server
 *
 * @author andrew
 * @date 2020-11-04
 */
@Slf4j
@Component
public class AuthenticationServerProxy {

    // 请求url
    @Value("${auth.server.base.url:http\\://localhost\\::8080}")
    private String authServerBaseUrl;

    private final RestTemplate restTemplate;

    public AuthenticationServerProxy(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // return auth result
    public Boolean sendAuth(String username, String password) {
        String url = authServerBaseUrl + "/user/auth";
        var map = new HashMap<String, Object>();
        map.put("username", username);
        map.put("password", password);
        var response = restTemplate.postForEntity(url, map, Boolean.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return false;
    }

    public Boolean sendOTP(String username, String code) {
        String url = authServerBaseUrl + "/otp/check";
        var param = new HashMap<String, String>();
        param.put("username", username);
        param.put("code", code);
        var response = restTemplate.postForEntity(url, param, Boolean.class);
        if (HttpStatus.OK == response.getStatusCode()) {
            return response.getBody();
        }
        log.error("otp validate failed for {{}}", username);
        return false;
    }
}
