package com.yz.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.io.File

/**
 * 本地缓存属性配置
 * @author andrew
 * @date 2020-09-28
 */
@Component
@ConfigurationProperties(prefix = LocalConfigProperties.PREFIX)
class LocalConfigProperties {

    @Value("\${spring.application.name}")
    private lateinit var applicationName: String

    var enabled: Boolean = false
    var fallbackLocation: String = "/tmp"
        set(value) {
            var fallback = value + File.separator + applicationName + DEFAULT_FILE_NAME_SUFFIX
            if (value.last() == File.separatorChar) {
                fallback = value + applicationName + DEFAULT_FILE_NAME_SUFFIX
            }
            field = fallback
        }

    companion object {
        const val PREFIX = "spring.cloud.config.local"
        const val DEFAULT_FILE_NAME_SUFFIX = ".properties"
    }
}