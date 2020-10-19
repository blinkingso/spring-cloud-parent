package com.yz.security.context;

/**
 * Spring Security Context
 * 在多线程模式下, 异步请求获取解决方案.
 *
 * @author andrew
 * @date 2020-10-19
 */
public class YzInheritableSecurityContext<T> extends InheritableThreadLocal<T> {
}
