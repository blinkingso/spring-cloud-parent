package com.yz.payment.service.impl

import com.yz.commons.entities.Payment
import com.yz.payment.mapper.PaymentMapper
import com.yz.payment.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PaymentServiceImpl : PaymentService {

    @Autowired
    private lateinit var paymentMapper: PaymentMapper

    override fun create(payment: Payment): Int {
        return paymentMapper.create(payment = payment)
    }

    override fun findAll(): List<Payment> {
        return paymentMapper.findAll()
    }

    override fun findById(id: Long): Payment? {
        return paymentMapper.findById(id = id)
    }
}