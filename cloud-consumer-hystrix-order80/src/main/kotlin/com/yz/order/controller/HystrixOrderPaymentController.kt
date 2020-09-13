package com.yz.order.controller

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import com.yz.commons.entities.CommonResult
import com.yz.order.service.OrderPaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-11
 */
@RestController
@RequestMapping("/hystrix")
// 全局的fallback方法, 需要在每个方法上添加注解@HystrixCommand
@DefaultProperties(defaultFallback = "defaultGlobalFallback", commandProperties = [
    HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2500")
])
class HystrixOrderPaymentController {

    @Autowired
    private lateinit var orderPaymentService: OrderPaymentService

    @GetMapping("/order/ok/{id}")
    fun ok(@PathVariable("id") id: String): String {
        return orderPaymentService.ok(id)
    }

    @GetMapping("/order/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "timeoutFallback", commandProperties = [HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")])
    @HystrixCommand
    fun timeout(@PathVariable("id") id: String): String {
        return orderPaymentService.timeout(id)
    }

    fun timeoutFallback(id: String): String {
        return "客户端线程: " + Thread.currentThread().name + "调用超时, 请稍后重试吧...id 是 $id"
    }

    fun defaultGlobalFallback(): String {
        return "请求出现了问题喽, 可能是超时喽, 客户端线程: ${Thread.currentThread().name}"
    }
}