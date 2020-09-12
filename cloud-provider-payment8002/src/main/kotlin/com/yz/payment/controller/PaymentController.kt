package com.yz.payment.controller

import com.yz.commons.entities.CommonResult
import com.yz.commons.entities.Payment
import com.yz.payment.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation.*
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/payment")
class PaymentController {

    @Autowired
    private lateinit var paymentService: PaymentService

    @Value("\${server.port}")
    private var serverPort: Int = 0

    @Autowired
    private lateinit var discoveryClient: DiscoveryClient

    @PostMapping(value = ["/create"])
    fun create(@RequestBody payment: Payment): CommonResult<Payment> {
        val result = paymentService.create(payment = payment)
        return if (result <= 0) {
            println("insert failed")
            CommonResult(code = 500, message = "failed", data = null)
        } else {
            println("insert success")
            CommonResult(data = payment)
        }
    }

    @GetMapping("/find")
    fun findAll(): CommonResult<List<Payment>> {
        println("now server is : $serverPort")
        return CommonResult(data = paymentService.findAll(), message = "now server is ${serverPort}")
    }

    @GetMapping("/find/{id}")
    fun findById(@PathVariable id: Long): CommonResult<Payment> {
        return CommonResult(data = paymentService.findById(id = id))
    }

    @GetMapping("/discovery")
    fun discovery(): Any {
        val services = discoveryClient.services
        services.forEach {
            println("service ==> $it")
        }

        val instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT")
        instances.forEach {
            println("instanceId is ${it.instanceId}")
        }
        return ""
    }

    @GetMapping("/timeout")
    fun timeout(): String {
        TimeUnit.SECONDS.sleep(5)
        return serverPort.toString()
    }
}