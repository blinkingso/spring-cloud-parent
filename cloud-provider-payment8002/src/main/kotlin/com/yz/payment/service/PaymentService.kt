package com.yz.payment.service

import com.yz.commons.entities.Payment

interface PaymentService {

    fun create(payment: Payment): Int

    fun findAll(): List<Payment>

    fun findById(id: Long): Payment?
}