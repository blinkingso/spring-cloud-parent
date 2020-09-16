package com.yz.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

/**
 *
 * @author andrew
 * @date 2020-09-16
 */
class PostFilter : ZuulFilter() {
    override fun run(): Any? {
        println("这是一个PostFilter")
        val ctx = RequestContext.getCurrentContext()
        ctx.response.characterEncoding = "utf-8"
        ctx.response.contentType = "text/html;charset=UTF-8"
        val responseBody = ctx.responseBody

        // 不为空, 说明filter或流程有异常
        if (!responseBody.isNullOrEmpty()) {
            ctx.responseStatusCode = 500
            ctx.responseBody = responseBody
        }

        return null
    }

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = "post"

    override fun filterOrder(): Int = 0
}