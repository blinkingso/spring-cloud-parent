package com.yz.payment.controller

import com.yz.commons.entities.CommonResult
import com.yz.commons.entities.Payment
import com.yz.payment.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/payment")
class PaymentController {

    @Autowired
    private lateinit var paymentService: PaymentService

    @Value("\${server.port}")
    private var serverPort: Int = 0

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
        return CommonResult(data = paymentService.findAll(), message = "now server is $serverPort")
    }

    @GetMapping("/find2")
    fun findAll2(req: HttpServletRequest): CommonResult<List<Payment>> {
        println("now server is : $serverPort")
        req.headerNames.iterator().forEach {
            println("header ---- > $it")
        }
        req.parameterMap.forEach {
            println("it === > (${it.key}, ${it.value[0]})")
        }
        return CommonResult(data = paymentService.findAll(), message = "now server is $serverPort")
    }

    @GetMapping("/find/{id}")
    fun findById(@PathVariable id: Long): CommonResult<Payment> {
        return CommonResult(data = paymentService.findById(id = id))
    }

    @GetMapping("/timeout")
    fun timeout(): String {
        TimeUnit.SECONDS.sleep(5)
        return serverPort.toString()
    }
}