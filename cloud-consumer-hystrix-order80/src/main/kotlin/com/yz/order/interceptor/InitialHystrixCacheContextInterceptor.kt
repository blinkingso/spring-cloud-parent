package com.yz.order.interceptor

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author andrew
 * @date 2020-09-14
 */
class InitialHystrixCacheContextInterceptor : HandlerInterceptor {

    private var context: HystrixRequestContext? = null

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("preHandle Init HystrixRequestContext")
        context = HystrixRequestContext.initializeContext()
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        context?.shutdown()
    }
}