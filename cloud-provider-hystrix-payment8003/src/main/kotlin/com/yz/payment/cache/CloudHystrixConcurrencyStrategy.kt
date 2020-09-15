package com.yz.payment.cache

import com.netflix.hystrix.HystrixThreadPoolKey
import com.netflix.hystrix.HystrixThreadPoolProperties
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle
import com.netflix.hystrix.strategy.properties.HystrixProperty
import org.springframework.web.context.request.RequestContextHolder
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Callable
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 *
 * @author andrew
 * @date 2020-09-15
 */
class CloudHystrixConcurrencyStrategy(private val delegateHystrixConcurrentStrategy: HystrixConcurrencyStrategy?) : HystrixConcurrencyStrategy() {

    override fun <T : Any> wrapCallable(callable: Callable<T>): Callable<T> {
        return HystrixThreadCallable(callable,
                RequestContextHolder.getRequestAttributes(),
                HystrixThreadLocal.getThreadLocal().get())
    }

    override fun getThreadPool(threadPoolKey: HystrixThreadPoolKey?,
                               corePoolSize: HystrixProperty<Int>?,
                               maximumPoolSize: HystrixProperty<Int>?,
                               keepAliveTime: HystrixProperty<Int>?,
                               unit: TimeUnit?,
                               workQueue: BlockingQueue<Runnable>?
    ): ThreadPoolExecutor? {
        return this.delegateHystrixConcurrentStrategy?.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
                ?: super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
    }

    override fun getThreadPool(threadPoolKey: HystrixThreadPoolKey?,
                               threadPoolProperties: HystrixThreadPoolProperties?
    ): ThreadPoolExecutor {
        return this.delegateHystrixConcurrentStrategy?.getThreadPool(threadPoolKey, threadPoolProperties)
                ?: super.getThreadPool(threadPoolKey, threadPoolProperties)
    }

    override fun getBlockingQueue(maxQueueSize: Int): BlockingQueue<Runnable> {
        return this.delegateHystrixConcurrentStrategy?.getBlockingQueue(maxQueueSize)
                ?: super.getBlockingQueue(maxQueueSize)
    }

    override fun <T : Any?> getRequestVariable(rv: HystrixRequestVariableLifecycle<T>?): HystrixRequestVariable<T> {
        return this.delegateHystrixConcurrentStrategy?.getRequestVariable(rv)
                ?: super.getRequestVariable(rv)
    }
}