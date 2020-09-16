package com.yz.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

/**
 *
 * @author andrew
 * @date 2020-09-16
 */
class ThirdPreFilter : ZuulFilter() {

    override fun run(): Any? {
        println("这是一个ThirdPreFilter")
        // 获取上下文
        val context = RequestContext.getCurrentContext()
        // 从上下文中获取request
        val request = context.request
        val b = request.getParameter("b")
        if (b.isNullOrEmpty()) {
            context.apply {
                setSendZuulResponse(false)
                responseBody = """{"status":"500", "message":"b参数为空"}"""
                // logic-is-success 用于保存上下文, 作为同类型下游Filter的执行开关
                set("logic-is-success", false)
                return@run null
            }
        }
        return null
    }

    override fun shouldFilter(): Boolean {
        val ctx = RequestContext.getCurrentContext()
        return ctx["logic-is-success"] as Boolean
    }

    override fun filterType(): String = "pre"

    override fun filterOrder(): Int = 3
}