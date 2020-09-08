package com.yz.commons.entities

import java.io.Serializable

class CommonResult<T>(
        var code: Int = 200,
        var message: String = "success",
        var data: T? = null
) : Serializable