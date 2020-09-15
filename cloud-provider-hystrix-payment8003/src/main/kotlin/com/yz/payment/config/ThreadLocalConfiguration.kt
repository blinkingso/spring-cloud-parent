package com.yz.payment.config

import com.netflix.hystrix.strategy.HystrixPlugins
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import com.yz.payment.cache.CloudHystrixConcurrencyStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

/**
 *
 * @author andrew
 * @date 2020-09-15
 */
@Configuration
class ThreadLocalConfiguration {

    /**
     * 解决线程池隔离情况下ThreadLocal保存变量的问题
     * 将线程的状态copy基于HystrixConcurrencyStrategy自定义
     * 复写wrapCallable方法, 将线程状态copy到callable
     */
    @PostConstruct
    fun init() {
        val eventNotifier = HystrixPlugins.getInstance().eventNotifier
        val metricsPublisher = HystrixPlugins.getInstance().metricsPublisher
        val propertiesStrategy = HystrixPlugins.getInstance().propertiesStrategy
        val commandExecutionHook = HystrixPlugins.getInstance().commandExecutionHook
        val concurrencyStrategy = HystrixPlugins.getInstance().concurrencyStrategy
        HystrixPlugins.reset()
        HystrixPlugins.getInstance().registerConcurrencyStrategy(CloudHystrixConcurrencyStrategy(concurrencyStrategy))
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier)
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher)
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy)
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook)
    }
}