package com.yz.payment.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

/**
 * 页面操作
 * @author andrew
 * @date 2020-09-21
 */
@Controller
class IndexController {

    @GetMapping("/")
    fun index(@RequestParam(required = false, name = "requestTime", defaultValue = "0") requestTime: String,
              @RequestParam(required = false, name = "endTime", defaultValue = "0") endTime: String
    ): ModelAndView {
        println("index called")

        val mav = ModelAndView("index")

        mav.addObject("obj", mutableMapOf("requestTime" to requestTime, "endTime" to endTime))

        return mav
    }
}