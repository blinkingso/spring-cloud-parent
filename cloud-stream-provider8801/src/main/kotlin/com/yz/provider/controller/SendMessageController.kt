package com.yz.provider.controller

import com.yz.provider.IMessageProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-29
 */
@RestController
class SendMessageController {

    @Autowired
    private lateinit var messageProvider: IMessageProvider

    @GetMapping("/send")
    fun send(): String {
        return messageProvider.send()
    }
}