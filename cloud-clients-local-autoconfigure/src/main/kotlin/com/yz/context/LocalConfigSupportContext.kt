package com.yz.context

import com.yz.config.LocalConfigProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.PropertiesFactoryBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.bootstrap.config.PropertySourceBootstrapConfiguration
import org.springframework.cloud.bootstrap.config.PropertySourceLocator
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator
import org.springframework.cloud.config.environment.PropertySource
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.env.CompositePropertySource
import org.springframework.core.env.MapPropertySource
import org.springframework.core.env.MutablePropertySources
import org.springframework.core.io.FileSystemResource
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.HashMap
import kotlin.streams.toList

/**
 * 判断远程加载信息是否可用, 如果不能用将读取加载本地配置文件进行启动
 * @author andrew
 * @date 2020-09-28
 */
@Configuration
@ConditionalOnProperty("spring.application.name", matchIfMissing = false)
@EnableConfigurationProperties(LocalConfigProperties::class)
class LocalConfigSupportContext : ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    private val logger = LoggerFactory.getLogger(LocalConfigSupportContext::class.java)

    @Autowired(required = false)
    private var propertySourceLocators: List<PropertySourceLocator> = Collections.emptyList()

    @Autowired
    private lateinit var localConfigProperties: LocalConfigProperties

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        if (hasCloudConfigLocator(this.propertySourceLocators)) {
            logger.info("未启用Config Server管理配置")
        }

        logger.info("检查Config Service配置资源")
        val environment = applicationContext.environment
        val propertySources = environment.propertySources
        logger.info("加载PropertySources源: ${propertySources.size()}个")
        if (!localConfigProperties.enabled) {
            logger.warn("未启用配置备份功能, 可配置${LocalConfigProperties.PREFIX}.enabled=true启用")
            return
        }

        if (isCloudConfigLoaded(propertySources)) {
            val cloudConfigSource = getLoadedCloudPropertySource(propertySources)
            logger.info("成功获取ConfigService配置资源")
            // 备份
            val map = backupPropertyMap(cloudConfigSource)
            backup(map, localConfigProperties.fallbackLocation)
        } else {
            logger.error("获取ConfigService配置资源失败")
            val backupProperty = loadBackupProperty(localConfigProperties.fallbackLocation)
            if (backupProperty != null) {
                val backupSourceMap = HashMap<String, Any>()
                val backupSource = MapPropertySource("backupSource", backupSourceMap)
                propertySources.addFirst(backupSource)
                logger.warn("使用备份的配置启动: ${localConfigProperties.fallbackLocation}")
            }
        }
    }

    private fun loadBackupProperty(fallbackLocation: String): Properties? {
        return try {
            PropertiesFactoryBean().apply {
                val fsr = FileSystemResource(fallbackLocation)
                this.setLocation(fsr)
                this.afterPropertiesSet()
            }.`object`
        } catch (e: Exception) {
            logger.error("backup property load error : ", e)
            null
        }
    }

    private fun backup(map: Map<String, Any?>, fallbackLocation: String) {
        FileSystemResource(fallbackLocation).apply {
            if (!this.file.exists()) {
                this.file.parentFile.mkdirs()
                this.file.createNewFile()
            }

            if (!this.file.canWrite()) {
                logger.error("无法读写文件: ${this.path}")
            }

            val properties = Properties().apply {
                map.filter { it.value != null }.forEach { (t, u) -> this.setProperty(t, u.toString()) }
            }

            FileOutputStream(this.file).use {
                properties.store(it, "backup cloud config")
            }
        }
    }

    private fun backupPropertyMap(cloudConfigSource: PropertySource?): Map<String, Any?> {
        val map = hashMapOf<String, Any?>()
        if (cloudConfigSource is CompositePropertySource) {
            cloudConfigSource.propertySources.stream().filter { it is MapPropertySource }
                    .forEach { ps ->
                        (ps as MapPropertySource).propertyNames.forEach {
                            if (!map.containsKey(it)) {
                                map[it] = ps.getProperty(it)
                            }
                        }
                    }
        }

        return map
    }

    private fun isCloudConfigLoaded(propertySources: MutablePropertySources): Boolean {
        return null != getLoadedCloudPropertySource(propertySources)
    }

    // 获取加载的Cloud Config配置项
    private fun getLoadedCloudPropertySource(propertySources: MutablePropertySources): PropertySource? {
        if (!propertySources.contains(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME)) {
            return null
        }

        val propertySource = propertySources.get(PropertySourceBootstrapConfiguration.BOOTSTRAP_PROPERTY_SOURCE_NAME)
        if (propertySource is CompositePropertySource) {
            return propertySource.propertySources.find { it.name == "configService" }?.source as PropertySource
        }

        return null
    }

    private fun hasCloudConfigLocator(propertySourceLocators: List<PropertySourceLocator>): Boolean {
        return propertySourceLocators.stream().filter { it is ConfigServicePropertySourceLocator }.toList().isNotEmpty()
    }

    override fun getOrder(): Int = Ordered.HIGHEST_PRECEDENCE + 11
}