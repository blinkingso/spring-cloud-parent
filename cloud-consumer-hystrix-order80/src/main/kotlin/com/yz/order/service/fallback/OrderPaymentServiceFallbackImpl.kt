package com.yz.order.service.fallback

import com.yz.order.service.OrderPaymentService
import org.springframework.stereotype.Component

/**
 *
 * @author andrew
 * @date 2020-09-14
 */
@Component
class OrderPaymentServiceFallbackImpl : OrderPaymentService {
    override fun ok(id: String): String = "ok出现异常了, 这个是用来做降级fallback, 线程是: ${Thread.currentThread().name}, id=$id"
    override fun timeout(id: String): String = "timeout出现异常了, 这个是用来做降级fallback, 线程是: ${Thread.currentThread().name}, id=$id"
    override fun isNum(id: String): String = "isNum出现异常了, 这个是用来做降级fallback, 线程是: ${Thread.currentThread().name}, id=$id"
    override fun hystrixBadRequest(id: String): String = "hystrixBadRequest出现异常了, 这个是用来做降级fallback, 线程是: ${Thread.currentThread().name}, id=$id"
}