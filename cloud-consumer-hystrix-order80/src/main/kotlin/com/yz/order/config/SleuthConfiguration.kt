package com.yz.order.config

import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 全链路配置
 * @author andrew
 * @date 2020-10-09
 */
@Configuration
class SleuthConfiguration {

    @Autowired
    private lateinit var beanFactory: BeanFactory

    @Bean
    fun executorService(): ExecutorService {
        val executorService = Executors.newFixedThreadPool(2)
        return TraceableExecutorService(this.beanFactory, executorService)
    }

}