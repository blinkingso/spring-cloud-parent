package com.yz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * product operations
 *
 * @author andrew
 * @date 2020-11-02
 */
@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @PostMapping
    public String add(@RequestParam String name) {
        log.info("adding product " + name);
        return "home";
    }
}
