package com.yz.provider.listener

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.messaging.Sink
import org.springframework.messaging.Message

/**
 * 监听器
 * @author andrew
 * @date 2020-09-29
 */
@EnableBinding(Sink::class)
class Listener {

    @StreamListener(value = "input")
    fun listener(message: Message<String>) {
        println("c1 get message: ${message.payload}")
    }
}