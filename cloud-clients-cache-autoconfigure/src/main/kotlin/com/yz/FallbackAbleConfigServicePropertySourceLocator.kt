package com.yz

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.config.client.ConfigClientProperties
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator
import org.springframework.core.annotation.Order
import org.springframework.core.env.CompositePropertySource
import org.springframework.core.env.Environment
import org.springframework.security.crypto.encrypt.TextEncryptor
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

@Order(0)
open class FallbackAbleConfigServicePropertySourceLocator(configClientProperties: ConfigClientProperties,
                                                          private var fallbackLocation: String?,
                                                          private var fallbackEnabled: Boolean = !fallbackLocation.isNullOrEmpty()
) : ConfigServicePropertySourceLocator(configClientProperties) {

    @Autowired(required = false)
    private var textEncrypt: TextEncryptor? = null

    override fun locate(environment: Environment?): org.springframework.core.env.PropertySource<*>? {
        val propertySource = super.locate(environment)
        if (fallbackEnabled) {
            if (propertySource != null) {
                storeLocally(propertySource)
            }
        }
        return propertySource
    }

    // 将环境变量保存到本地服务器上
    private fun storeLocally(propertySource: org.springframework.core.env.PropertySource<*>) {
        val source = propertySource as CompositePropertySource
        val text = source.propertyNames.map {
            var value = source.getProperty(it)
            if (null != textEncrypt) {
                value = "{cipher}${textEncrypt!!.encrypt(value.toString())}"
            }
            "${it}=${value}"
        }.toList().joinToString("\n")

        // 把文本保存到文件中
        saveFile(text)
    }

    private fun saveFile(contents: String) {
        val file = File(fallbackLocation + File.separator + ConfigServerBootstrap.FALLBACK_FILE_NAME)
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        FileWriter(file).use { fw ->
            BufferedWriter(fw).use { bw ->
                bw.write(contents)
            }
        }
    }
}
