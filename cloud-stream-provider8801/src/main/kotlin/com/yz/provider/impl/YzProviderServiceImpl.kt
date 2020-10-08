package com.yz.provider.impl

import com.yz.provider.IMessageProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import java.util.*

/**
 *
 * @author andrew
 * @date 2020-09-29
 */
@EnableBinding(Source::class)
class YzProviderServiceImpl : IMessageProvider {

    @Autowired
    private lateinit var output: MessageChannel

    override fun send(): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        output.send(MessageBuilder.withPayload(uuid).build())
        return uuid
    }
}