package com.yz.payment.cache

import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.util.concurrent.Callable

/**
 *
 * @author andrew
 * @date 2020-09-15
 */
class HystrixThreadCallable<S>(private val delegate: Callable<S>,
                               private val requestAttributes: RequestAttributes?,
                               private val params: String
) : Callable<S> {

    override fun call(): S {
        return try {
            RequestContextHolder.setRequestAttributes(requestAttributes)
            HystrixThreadLocal.getThreadLocal().set(params)
            delegate.call()
        } finally {
            RequestContextHolder.resetRequestAttributes()
            HystrixThreadLocal.getThreadLocal().remove()
        }
    }
}