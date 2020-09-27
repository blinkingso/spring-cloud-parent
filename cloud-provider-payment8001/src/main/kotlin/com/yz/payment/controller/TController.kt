package com.yz.payment.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-23
 */
@RestController
@RequestMapping
@RefreshScope
class TController {

    @Value("\${foo.bar}")
    private lateinit var fooBar: String

    @Value("\${foo.username}")
    private lateinit var fooUsername: String

    @Value("\${server.port}")
    private lateinit var serverPort: String

    @GetMapping("/limit/test")
    fun limit(): String {
        return "limit pass"
    }

    @GetMapping("/limit/redis")
    fun redis(): String {
        return "limit redis pass"
    }

    @GetMapping("/foo")
    fun foo(): String {
        return "foo.bar is : $fooBar, foo.username is $fooUsername, now server is $serverPort"
    }
}