package com.yz.payment.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 *
 * @author andrew
 * @date 2020-09-10
 */
@Service
class PaymentService {

    fun ok(id: String): String {
        return "线程: ${Thread.currentThread().name}-> ok(), id: $id, ~~~~~~哈哈"
    }

    @HystrixCommand(fallbackMethod = "timeoutFallback", commandProperties = [
        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    ])
    fun timeout(id: String): String {
        TimeUnit.SECONDS.sleep(3)
        return "线程: ${Thread.currentThread().name}-> timeout(), id: $id, ~~~~~~哈哈, 耗时3s"
    }

    /**
     * 要求正常服务在2s内执行结束， 超过2s后服务降级返回
     */
    fun timeoutFallback(id: String): String {
        return "线程: ${Thread.currentThread().name}-> timeoutFallback(), id: $id, ~~~~~~哈哈, 我是服务降级的功能啦"
    }
}