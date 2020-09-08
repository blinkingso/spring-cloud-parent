package com.yz.payment.mapper

import com.yz.commons.entities.Payment
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param

@Mapper
interface PaymentMapper {

    fun create(payment: Payment): Int

    fun findAll(): List<Payment>

    fun findById(@Param("id") id: Long): Payment
}