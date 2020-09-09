package com.yz.order.lb

import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient

interface ILoadBalance {

    fun getAllInstances(discoveryClient: DiscoveryClient, instanceName: String): List<ServiceInstance>

    fun getServerInstance(discoveryClient: DiscoveryClient, instanceName: String): ServiceInstance?
}