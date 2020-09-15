package com.yz.payment.controller

import com.yz.payment.cache.HystrixThreadLocal
import com.yz.payment.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder

/**
 *
 * @author andrew
 * @date 2020-09-10
 */
@RestController
@RequestMapping("/payment")
class PaymentController {

    @Autowired
    private lateinit var paymentService: PaymentService

    @Value("\${server.port}")
    private lateinit var serverPort: String

    @GetMapping("/ok/{id}")
    fun ok(@PathVariable("id") id: String): String {
        return paymentService.ok(id)
    }

    @GetMapping("/timeout/{id}")
    fun timeout(@PathVariable("id") id: String): String {
        return paymentService.timeout(id)
    }

    @GetMapping("/num/{id}")
    fun isNum(@PathVariable("id") id: String): String {
        return paymentService.isNum(id)
    }

    // global request 全局请求有效
    @GetMapping("/collapsing/{id}")
    fun collapsing(@PathVariable("id") id: String): String? {
        val future = paymentService.collapsingGlobal(id)
        // test collapsing 请求合并
        return future?.get()
    }

    // 一次请求
    @GetMapping("/collapsing")
    fun collapsing(): String? {
        val future1 = paymentService.collapsing("123")
        val future2 = paymentService.collapsing("222")
        // test collapsing 请求合并
        println(future1?.get())
        println(future2?.get())
        return future2?.get()
    }

    /**
     * HystrixBadRequestException 不会被fallback捕获而造成服务熔断
     * circuit breaker
     */
    @GetMapping("/hbr/{id}")
    fun hystrixBadRequest(@PathVariable("id") id: String): String {
        return paymentService.hystrixBadRequest(id)
    }

    @GetMapping("/getUser/{id}")
    fun getUser(@PathVariable("id") id : Int) : String {
        // 放入上下文对象
        HystrixThreadLocal.getThreadLocal().set("userId : $id")
        // 利用RequestContextHolder放入对象测试
        RequestContextHolder.currentRequestAttributes().setAttribute("userId", "userId : $id", RequestAttributes.SCOPE_REQUEST)
        println("Current thread : ${Thread.currentThread().name}")
        println("Thread local : ${HystrixThreadLocal.getThreadLocal().get()}")
        println("RequestContextHolder : ${RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST)}")

        val user = paymentService.getUser(id)
        return user
    }
}