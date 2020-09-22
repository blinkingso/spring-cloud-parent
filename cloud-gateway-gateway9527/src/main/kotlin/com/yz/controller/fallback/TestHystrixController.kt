package com.yz.controller.fallback

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-22
 */
@RestController
class TestHystrixController {

    @GetMapping("/fallback")
    fun fallback() : String {
        return "spring cloud gateway fallback"
    }
}