package com.yz.order.controller

import com.yz.commons.entities.CommonResult
import com.yz.commons.entities.Payment
import com.yz.order.lb.ILoadBalance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping(value = ["/order"])
class OrderController {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    //    private val paymentURL = "http://localhost:8001"
    // RestTemplate Bean 添加@LoadBalanced注解
    private val paymentURL = "http://CLOUD-PROVIDER-PAYMENT"

    @Autowired
    private lateinit var discoveryClient: DiscoveryClient

    @Autowired
    @Qualifier(value = "defaultRoundLoadBalance")
    private lateinit var iLoadBalance: ILoadBalance

    @GetMapping("/payment/create")
    fun create(payment: Payment): CommonResult<Payment>? {
        return restTemplate.postForObject(paymentURL.plus("/payment/create"),
                payment,
                CommonResult<Payment>().javaClass)
    }

    @GetMapping("/payment/find")
    fun findAll(): CommonResult<List<Payment>>? {
        return restTemplate.getForObject(paymentURL.plus("/payment/find"),
                CommonResult<List<Payment>>().javaClass)
    }

    @GetMapping("/payment/find/{id}")
    fun findPaymentById(@PathVariable("id") id: Long): CommonResult<Payment>? {
        return restTemplate.getForObject(paymentURL.plus("/payment/find/${id}"),
                CommonResult<Payment>().javaClass)
    }

    @GetMapping("/v2/payment/find/{id}")
    fun findPaymentById2(@PathVariable("id") id: Long): CommonResult<Payment>? {
        val resp = restTemplate.getForEntity(paymentURL.plus("/payment/find/${id}"),
                CommonResult<Payment>().javaClass)

        return if (resp.statusCode.is2xxSuccessful) {
            resp.body
        } else {
            CommonResult(code = 500, message = "接口请求失败")
        }
    }

    @GetMapping("/v3/payment/find/{id}")
    fun findPaymentById3(@PathVariable("id") id: Long): CommonResult<Payment>? {
        val instance = iLoadBalance.getServerInstance(discoveryClient, "CLOUD-PROVIDER-PAYMENT")
        val url = instance?.uri?.toString()
        println("url is $url")
        if (url?.isNotEmpty()!!) {
            val resp = restTemplate.getForEntity(url.plus("/payment/find/${id}"),
                    CommonResult<Payment>().javaClass)

            if (resp.statusCode.is2xxSuccessful) {
                return resp.body
            }
        }

        return CommonResult(code = 500, message = "接口请求失败")
    }
}
