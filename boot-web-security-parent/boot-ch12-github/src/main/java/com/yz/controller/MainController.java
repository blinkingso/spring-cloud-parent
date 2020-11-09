package com.yz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author andrew
 * @date 2020-11-06
 */
@Slf4j
@Controller
@RequestMapping
public class MainController {

    @GetMapping(value = {"", "/", "/home"})
    public String home(OAuth2AuthenticationToken token) {
        log.info("token is : " + token.getPrincipal());
        return "home.html";
    }
}
