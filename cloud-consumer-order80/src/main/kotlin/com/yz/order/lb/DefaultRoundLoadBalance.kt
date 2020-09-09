package com.yz.order.lb

import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@Component(value = "defaultRoundLoadBalance")
class DefaultRoundLoadBalance : ILoadBalance {

    private val requests = AtomicInteger(0)

    fun nextServer(): Int {
        var current: Int
        var next: Int
        do {
            current = requests.get()
            next = if (current >= Int.MAX_VALUE) 0 else current + 1
        } while (!this.requests.compareAndSet(current, next))

        return next
    }

    override fun getAllInstances(discoveryClient: DiscoveryClient, instanceName: String): List<ServiceInstance> {
        val list = discoveryClient.getInstances(instanceName)
        if (list.isNullOrEmpty()) {
            return Collections.emptyList()
        }
        return list
    }

    override fun getServerInstance(discoveryClient: DiscoveryClient, instanceName: String): ServiceInstance? {
        val list = getAllInstances(discoveryClient, instanceName)
        if (list.isEmpty())
            return null
        val index = nextServer() % list.size
        return list[index]
    }
}