package com.yz.payment.cache

/**
 *
 * @author andrew
 * @date 2020-09-15
 */
class HystrixThreadLocal {
    companion object {
        private val threadLocal = ThreadLocal<String>()
        fun getThreadLocal(): ThreadLocal<String> = threadLocal
    }
}