package com.yz.payment.config

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 *
 * @author andrew
 * @date 2020-09-15
 */
class WebInterceptor : HandlerInterceptor {

    private lateinit var context: HystrixRequestContext

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        context = HystrixRequestContext.initializeContext()
        return super.preHandle(request, response, handler)
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        context.shutdown()
        super.afterCompletion(request, response, handler, ex)
    }
}