package com.yz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Test Async Method Using {@link org.springframework.security.core.context.SecurityContext}
 * and {@link org.springframework.security.core.context.SecurityContextHolderStrategy} strategy to
 * configure SecurityContext Async Strategies
 * <p>
 * 1. Using @{@link org.springframework.scheduling.annotation.EnableAsync} and @{@link Async}
 * 2. Using customized ThreadPool {@link java.util.concurrent.ExecutorService}
 * 3. Using wrapped SecurityContext Thread Pool {@link org.springframework.security.concurrent.DelegatingSecurityContextExecutorService}
 * and {@link org.springframework.security.concurrent.DelegatingSecurityContextExecutor} etc...
 * <p>
 * auth2为null的解决方法为: 定义一个InitializingBean在Bean初始化完成后设置策略
 * 为{@link org.springframework.security.core.context.SecurityContextHolderStrategy}
 * 实现为: org.springframework.security.core.context.InheritableThreadLocalSecurityContextHolderStrategy
 *
 * @author andrew
 * @date 2020-10-29
 */
@RestController
@RequestMapping
@Slf4j
public class AsyncController {

    private final ExecutorService es = Executors.newCachedThreadPool();

    @Async
    @GetMapping("/async")
    public void async(Authentication authentication) {

        printAuth(authentication);

        log.info("now async invoked");
    }

    /**
     * 自定义线程池处理
     *
     * @param authentication
     */
    @GetMapping("/async2")
    public void async2(Authentication authentication) {
        log.info("log async2 ===============custom thread pool===============");
        es.execute(() -> printAuth(authentication));
        log.info("log async2 =============delegating thread pool=================");
        final DelegatingSecurityContextExecutor des = new DelegatingSecurityContextExecutor(es);
        des.execute(() -> printAuth(authentication));

        log.info("now async2 invoked finished");
    }

    /**
     * 自定义线程池处理
     *
     * @param authentication
     */
    @GetMapping("/async3")
    public void async3(Authentication authentication) {
        log.info("log async3 =============delegating thread pool=================");
        final DelegatingSecurityContextExecutor des = new DelegatingSecurityContextExecutor(es);
        des.execute(printAuthSecurityContext(authentication));

        log.info("now async3 invoked finished");
    }

    @GetMapping("/call")
    public String callable(Authentication authentication) {
        Callable<String> task = () -> {
            var context = SecurityContextHolder.getContext();
            return context.getAuthentication().getName();
        };

        try {
            return "callable :: " + es.submit(task).get() + "!";
        } catch (Exception e) {
            // here cause an exception  and go ahead to execute next line
            e.printStackTrace();
            var des = new DelegatingSecurityContextCallable<>(task);

            // also you can use DelegatingSecurityContextExecutorService
            try {
                return es.submit(des).get();
            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            }
        } finally {
            es.shutdown();
        }

        return null;
    }

    private Runnable printAuthSecurityContext(Authentication authentication) {
        final DelegatingSecurityContextRunnable r = new DelegatingSecurityContextRunnable(() -> printAuth(authentication));
        es.execute(r);
        return r;
    }

    private void printAuth(Authentication authentication) {
        if (null != authentication) {
            log.info("now authentication is " + authentication.getName());

            final Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
            if (auth2 == null) {
                log.info("auth2 is null");
            } else {
                log.info("auth2 is " + auth2.getName());
            }
        }
    }
}
