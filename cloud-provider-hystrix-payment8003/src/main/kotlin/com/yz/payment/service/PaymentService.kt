package com.yz.payment.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager
import com.netflix.hystrix.exception.HystrixBadRequestException
import com.yz.payment.cache.HystrixThreadLocal
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

/**
 *
 * @author andrew
 * @date 2020-09-10
 */
@Service
class PaymentService {

    @HystrixCommand
    fun ok(id: String): String {
        return "线程: ${Thread.currentThread().name}-> ok(), id: $id, ~~~~~~哈哈"
    }

    @HystrixCommand(fallbackMethod = "timeoutFallback", commandProperties = [
        HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    ])
    fun timeout(id: String): String {
        TimeUnit.SECONDS.sleep(5)
        return "线程: ${Thread.currentThread().name}-> timeout(), id: $id, ~~~~~~哈哈, 耗时3s"
    }

    fun isNum(id: String): String {
        if (id.toInt() < 0) {
            throw IllegalArgumentException("非法参数")
        }
        return "线程: ${Thread.currentThread().name}-> isNum(), id: $id, ~~~~~~哈哈, 耗时3s"
    }

    fun hystrixBadRequest(id: String): String {
        if (id.toInt() < 0) {
            throw HystrixBadRequestException("bad request param id is $id")
        }

        return "线程: ${Thread.currentThread().name}-> hystrixBadRequest(), id: $id, ~~~~~~哈哈, 耗时0s"
    }

    /**
     * 对同一个线程的多次调用生效
     * 在多线程请求时多次请求会报错
     */
    @HystrixCollapser(batchMethod = "hystrixCollapsing", collapserProperties = [
        // 1s内的请求
        HystrixProperty(name = HystrixPropertiesManager.TIMER_DELAY_IN_MILLISECONDS, value = "1000")])
    fun collapsing(id: String): Future<String>? {
        return null
    }

    /**
     * 对所有的请求生效(不同的线程的请求)
     */
    @HystrixCollapser(batchMethod = "hystrixCollapsing",
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
            collapserProperties = [
                HystrixProperty(name = HystrixPropertiesManager.TIMER_DELAY_IN_MILLISECONDS, value = "1000")])
    fun collapsingGlobal(id: String): Future<String>? {
        return null
    }

    @HystrixCommand
    fun hystrixCollapsing(messages: List<String>): List<String> {
        println("当前线程为: ${Thread.currentThread().name}")
        println("请求个数为: ${messages.size}个")
        return messages.map {
            "collapsing-$it"
        }.toList()
    }

    /**
     * 要求正常服务在2s内执行结束， 超过2s后服务降级返回
     */
    fun timeoutFallback(id: String): String {
        return "线程: ${Thread.currentThread().name}-> timeoutFallback(), id: $id, ~~~~~~哈哈, 我是服务降级的功能啦"
    }

    // 线程池隔离模式, 默认下java.lang.IllegalStateException: No thread-bound request found:
    // 信号量模式可以解决, 但不推荐,
    // 第二种办法, 自定义策略使用HystrixConcurrencyStrategy
    @HystrixCommand(commandProperties = [
        HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD")
    ])
    fun getUser(id: Int): String {
        logger.info("current thread : ${Thread.currentThread().name}")
        logger.info("thread context object : ${HystrixThreadLocal.getThreadLocal().get()}")
        logger.info("request: ${RequestContextHolder.currentRequestAttributes().getAttribute("context", RequestAttributes.SCOPE_REQUEST).toString()}")

        return "user-$id"
    }

    private val logger = LoggerFactory.getLogger(PaymentService::class.java)
}