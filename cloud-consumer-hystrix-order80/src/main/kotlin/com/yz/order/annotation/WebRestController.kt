package com.yz.order.annotation

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-10-09
 */
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@RestController
@RequestMapping
@Target(AnnotationTarget.CLASS)
annotation class WebRestController(val path: Array<String> = [])