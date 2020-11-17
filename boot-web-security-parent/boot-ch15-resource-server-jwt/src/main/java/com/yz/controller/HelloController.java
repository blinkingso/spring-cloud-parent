package com.yz.controller;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 * @date 2020-11-17
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("/hello")
    public String getHello(OAuth2Authentication authentication) {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        return "jwt get hello == > " + details.getDecodedDetails();
    }

    @PostMapping("/hello")
    public String postHello() {
        return "jwt post hello";
    }
}
