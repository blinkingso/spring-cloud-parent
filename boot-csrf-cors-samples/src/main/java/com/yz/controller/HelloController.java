package com.yz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author andrew
 * @date 2020-10-20
 */
@RestController
public class HelloController {

    @GetMapping("/helllo")
    public String getHello() {
        return "get hello";
    }

    @PostMapping("/helllo")
    public String postHello() {
        return "post hello";
    }
}
