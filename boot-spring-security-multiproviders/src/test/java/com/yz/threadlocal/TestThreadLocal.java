package com.yz.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author andrew
 * @date 2020-10-19
 */
@Slf4j
public class TestThreadLocal {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    @Test
    public void testThreadLocal() throws InterruptedException {
        threadLocal.set("abc");
        inheritableThreadLocal.set("bca");
        log.info("主线程::" + threadLocal.get());
        log.info("主线程::" + inheritableThreadLocal.get());
        Thread main = Thread.currentThread();
        System.out.println(main.getName());
        Thread t = new Thread(() -> {
            log.info("子线程::" + threadLocal.get());
            log.info("子线程::" + inheritableThreadLocal.get());
        });

        t.start();
        t.join();
    }
}
