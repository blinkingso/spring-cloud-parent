package com.yz.payment.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 *
 * @author andrew
 * @date 2020-09-22
 */
@RestController
@RequestMapping("/test")
class TestController {

    private val cache = ConcurrentHashMap<String, AtomicInteger>()

    @GetMapping("/retry")
    fun retry(@RequestParam("key") key: String, @RequestParam("count") count: Int): String {
        val num = cache.computeIfAbsent(key) { AtomicInteger() }
        val i = num.incrementAndGet()
        if (i < count) {
            throw RuntimeException("failure, please retry")
        }
        cache.clear()
        return "重试${i}此成功"
    }

    @GetMapping("/hystrix")
    fun hystrix(): String {
        println("hystrix begin")
        TimeUnit.SECONDS.sleep(3)
        println("hystrix return")
        return "hystrix call success"
    }

    @GetMapping("/v1")
    fun testVersion(): String {
        return "v1 版本"
    }

    @GetMapping("/v2")
    fun testVersion2(): String {
        return "v2 版本"
    }
}