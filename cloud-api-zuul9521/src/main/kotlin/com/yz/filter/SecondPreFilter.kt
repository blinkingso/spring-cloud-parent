package com.yz.filter

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext

/**
 *
 * @author andrew
 * @date 2020-09-16
 */
class SecondPreFilter : ZuulFilter() {

    override fun run(): Any? {
        println("这是一个SecondPreFilter")
        // 获取上下文
        val context = RequestContext.getCurrentContext()
        // 从上下文中获取request
        val request = context.request
        val a = request.getParameter("a")
        if (a.isNullOrEmpty()) {
            context.apply {
                setSendZuulResponse(false)
                responseBody = """{"status":"500", "message":"a参数为空"}"""
                // logic-is-success 用于保存上下文, 作为同类型下游Filter的执行开关
                set("logic-is-success", false)
                return@run null
            }
        }

        // 设置避免报空
        context.set("logic-is-success", true)
        return null
    }

    override fun shouldFilter(): Boolean = true

    override fun filterType(): String = "pre"

    override fun filterOrder(): Int = 2
}