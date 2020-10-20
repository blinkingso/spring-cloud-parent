package com.yz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * @author andrew
 * @date 2020-10-20
 */
@Controller
@RequestMapping
@Slf4j
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/userLogin")
    public String userLogin(HttpServletRequest request, HttpServletResponse response) {
        final Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            log.info("userLogin: 请求属性列表: " + attributeNames.nextElement());
        }
        return "userLogin";
    }
}
