package com.yz

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.config.client.ConfigClientProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.ConfigurableEnvironment

/**
 * 客户端回退功能, cache到文件系统中
 * @author andrew
 * @date 2020-09-27
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnProperty("spring.cloud.config.fallback-location")
@PropertySource(value = ["classpath:config-client.properties", "file:\${spring.cloud.config.fallback-location}/fallback.properties"],
        ignoreResourceNotFound = true)
class ConfigServerBootstrap {

    @Value("\${spring.cloud.config.fallback-location}")
    private lateinit var fallbackLocation: String

    @Autowired
    private lateinit var configurableEnvironment: ConfigurableEnvironment

    @Bean
    fun configClientProperties(): ConfigClientProperties {
        val configClientProperties = ConfigClientProperties(this.configurableEnvironment)
        configClientProperties.isEnabled = false
        return configClientProperties
    }

    @Bean
    fun fallbackAbleConfigServicePropertySourceLocator(): FallbackAbleConfigServicePropertySourceLocator {
        val configClientProperties = configClientProperties()
        return FallbackAbleConfigServicePropertySourceLocator(configClientProperties = configClientProperties,
                fallbackLocation = fallbackLocation)
    }

    companion object {
        // 默认的cache文件名
        const val FALLBACK_FILE_NAME = "fallback.properties"
    }
}