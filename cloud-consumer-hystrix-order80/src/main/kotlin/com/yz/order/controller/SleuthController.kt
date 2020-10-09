package com.yz.order.controller

import com.yz.order.annotation.WebRestController
import com.yz.order.service.OrderPaymentService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.client.RestTemplate
import java.util.concurrent.ExecutorService

/**
 * api gateway test
 * @author andrew
 * @date 2020-10-09
 */
@WebRestController(path = ["/sleuth"])
class SleuthController {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var executorService: ExecutorService

    @Autowired
    lateinit var orderPaymentService: OrderPaymentService

    @GetMapping("/sleuth/helloByFeign")
    fun helloByFeign(name: String): String {
        LOGGER.info("client sent Feign 方式, 参数name=: $name")
        val ok = orderPaymentService.ok(name)
        LOGGER.info("client received. Feign 方式, 结果为 $ok")
        return ok
    }

    @GetMapping("/sleuth/helloByRest")
    fun helloByRestTemplate(name: String): String {
        LOGGER.info("client sent RestTemplate 方式, 参数name=: $name")
        val ok = restTemplate.getForObject("http://PROVIDER-HYSTRIX-PAYMENT/payment/ok/${name}", String::class.java)
                ?: "Default"
        LOGGER.info("client received. RestTemplate 方式, 结果为 $ok")
        return ok
    }

    @GetMapping("/sleuth/helloByNewThread")
    fun helloByNewThread(name: String): String {
        LOGGER.info("client sent NewSubThread 方式, 参数name=: $name")
        val future = executorService.submit {
            LOGGER.info("进入子线程, 参数: $name")
            orderPaymentService.ok(name)
        }

        val ok = future.get().toString()
        LOGGER.info("client received. NewSubThread 方式, 结果为 $ok")
        return ok
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(SleuthController::class.java)
    }
}