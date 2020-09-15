package com.yz.order.service

import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult
import com.yz.order.service.fallback.OrderPaymentServiceFallbackImpl
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 *
 * @author andrew
 * @date 2020-09-11
 */
@FeignClient(value = "PROVIDER-HYSTRIX-PAYMENT", path = "/payment", fallback = OrderPaymentServiceFallbackImpl::class)
interface OrderPaymentService {

    @GetMapping("/ok/{id}")
    @CacheResult
    fun ok(@PathVariable("id") @CacheKey id: String): String

    @GetMapping("/ok/{id}")
    fun getId(@PathVariable("id") id: String): String = id

    /**
     * 10s内有20%的请求失败则打开断路器开启熔断
     */
    @GetMapping("/timeout/{id}")
    fun timeout(@PathVariable("id") id: String): String

    @GetMapping("/num/{id}")
    fun isNum(@PathVariable("id") id: String): String

    @GetMapping("/hbr/{id}")
    fun hystrixBadRequest(@PathVariable("id") id: String): String
}