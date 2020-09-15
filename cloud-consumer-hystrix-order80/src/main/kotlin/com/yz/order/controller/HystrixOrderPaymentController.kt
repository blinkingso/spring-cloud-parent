package com.yz.order.controller

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager
import com.yz.order.service.OrderPaymentService
import com.yz.order.service.cache.OrderPaymentOkCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

/**
 *
 * @author andrew
 * @date 2020-09-11
 */
@RestController
@RequestMapping("/hystrix")
// 全局的fallback方法, 需要在每个方法上添加注解@HystrixCommand
//@DefaultProperties(defaultFallback = "defaultGlobalFallback", commandProperties = [
//    HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2500")
//])
class HystrixOrderPaymentController {

    @Autowired
    private lateinit var orderPaymentService: OrderPaymentService

    @GetMapping("/order/ok/{id}")
    fun ok(@PathVariable("id") id: String): String {
        orderPaymentService.ok(id)
        return orderPaymentService.ok(id)
    }

    @GetMapping("/order/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "timeoutFallback", commandProperties = [HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")])
//    @HystrixCommand
    fun timeout(@PathVariable("id") id: String): String {
        return orderPaymentService.timeout(id)
    }

    @HystrixCommand(commandProperties = [
        HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000"),
        HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD"),
        HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "true"),
        HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "20"),
        HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "10000"),
        HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"),
        // 支持的最大请求数(并发数)
        HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS, value = "100"),
        // 请求超时时线程中断
        HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_INTERRUPT_ON_TIMEOUT, value = "true")])
    @GetMapping("/order/num/{id}")
    fun isNum(@PathVariable("id") id: String): String {
        return orderPaymentService.isNum(id)
    }

    @GetMapping("/order/hbr/{id}")
    fun hystrixBadRequest(@PathVariable("id") id: String): String {
        return orderPaymentService.hystrixBadRequest(id)
    }

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @GetMapping("/order/cache/{id}")
    fun cache(@PathVariable("id") id: String): String {
        val first = OrderPaymentOkCommand(restTemplate = restTemplate, id = id)
        first.execute()
        println("第一次是否开启缓存: ${first.isResponseFromCache}")
        val second = OrderPaymentOkCommand(restTemplate = restTemplate, id = id)
        val secondResult = second.execute()
        println("第二次是否开启缓存: ${second.isResponseFromCache}")
        return secondResult
    }

    // 指定降级方法
    fun timeoutFallback(id: String): String {
        return "客户端线程: " + Thread.currentThread().name + "调用超时, 请稍后重试吧...id 是 $id"
    }

    // @DefaultProperties 当前controller下全局默认的降级策略
    fun defaultGlobalFallback(): String {
        return "请求出现了问题喽, 可能是超时喽, 客户端线程: ${Thread.currentThread().name}"
    }
}