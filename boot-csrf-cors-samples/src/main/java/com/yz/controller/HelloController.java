package com.yz.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author andrew
 * @date 2020-10-20
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping(value = {"/hello"})
    public String getHello() {
        return "get hello";
    }

    @PostMapping(value = {"/hello"})
    public String postHello() {
        return "post hello";
    }

    // 测试csrf忽略的接口
    @PostMapping(value = {"/csrf/ignore"})
    public String postIgnore1() {
        return "ignore 1";
    }

    @PostMapping(value = {"/csrf/ignore/{id}"})
    public String postIgnore(@PathVariable("id") String id) {
        return "ignore " + id;
    }
}
