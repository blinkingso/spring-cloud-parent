package com.yz.controller

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author andrew
 * @date 2020-09-18
 */
@RestController
class AuthorizationController {

    fun authorized(model: Model) : String {

        return "index"
    }
}