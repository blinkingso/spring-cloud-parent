package com.yz.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration
import org.springframework.cloud.endpoint.RefreshEndpoint
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.IntervalTask
import org.springframework.scheduling.config.ScheduledTaskRegistrar

/**
 * 自动配置类, 客户端自动刷新
 * 通过客户端定时任务进行拉取的方式刷新到内存中
 *
 * @author andrew
 * @date 2020-09-27
 */
@ConditionalOnClass(RefreshEndpoint::class)
@ConditionalOnProperty("spring.cloud.config.refresh-interval")
@AutoConfigureAfter(RefreshAutoConfiguration::class)
@Configuration
class ConfigClientRefreshAutoConfiguration : SchedulingConfigurer {

    private val logger = LoggerFactory.getLogger(ConfigClientRefreshAutoConfiguration::class.java)

    @Value("\${spring.cloud.config.refresh-interval}")
    private var refreshInterval: Long = 0L

    @Autowired
    private lateinit var refreshEndpoint: RefreshEndpoint

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        val interval = getRefreshIntervalInMilliseconds()
        logger.info("Scheduling config refresh task with $interval ms delay")

        taskRegistrar.addFixedDelayTask(IntervalTask({ refreshEndpoint.refresh() }, interval, interval))
    }

    private fun getRefreshIntervalInMilliseconds(): Long {
        return refreshInterval * 1000
    }

    @ConditionalOnMissingBean(ScheduledAnnotationBeanPostProcessor::class)
    @EnableScheduling
    @Configuration
    protected class EnableSchedulingConfigProperties
}