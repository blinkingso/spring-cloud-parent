package com.yz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author andrew
 * @date 2020-11-02
 */
@Controller
@RequestMapping
public class PageController {

    @GetMapping(value = {"", "/", "home"})
    public String home() {
        return "home";
    }
}
