package com.yz.order.service.cache

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommandKey
import com.netflix.hystrix.HystrixRequestCache
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault
import org.springframework.web.client.RestTemplate

/**
 * cache 一个context上下文的范围
 * 1. 首先要初始化请求的上下文, 可以通过filter或interceptor初始化
 * 2. 类缓存
 * @see com.yz.order.controller.HystrixOrderPaymentController.cache
 * @author andrew
 * @date 2020-09-14
 */
class OrderPaymentOkCommand : HystrixCommand<String> {

    private val id: String
    private val restTemplate: RestTemplate

    constructor(id: String, restTemplate: RestTemplate) : super(HystrixCommandGroupKey.Factory.asKey("springCloudCacheGroup")) {
        this.id = id
        this.restTemplate = restTemplate
    }

    override fun run(): String? {
        val resp = restTemplate.getForObject("http://PROVIDER-HYSTRIX-PAYMENT/payment/ok/{id}", String::class.java, id)
        println(resp)
        return resp
    }

    // 同一个请求id作为cache的key
    override fun getCacheKey(): String {
        return id
    }

    companion object {
        fun cleanCache(id: String) {
            HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey("springCloudCacheGroup"),
                    HystrixConcurrencyStrategyDefault.getInstance()).clear(id)
        }
    }
}