package com.yz.provider.listener

import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.Message
import org.springframework.messaging.SubscribableChannel
import org.springframework.stereotype.Component

/**
 * 监听器
 * @author andrew
 * @date 2020-09-29
 */
@EnableBinding(Listener2.Processor::class)
class Listener2 {

    @StreamListener(target = "input2")
    fun listener(message: Message<String>) {
        println("c2 get message: ${message.payload}")
    }


    @Component
    interface Processor {
        @Input(value = "input2")
        fun input2(): SubscribableChannel
    }
}