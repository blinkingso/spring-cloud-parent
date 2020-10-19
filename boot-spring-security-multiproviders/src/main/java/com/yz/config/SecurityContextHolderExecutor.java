package com.yz.config;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.Executor;

/**
 * @author andrew
 * @date 2020-10-19
 */
public class SecurityContextHolderExecutor {

    private final Executor executor;
    private final SecurityContext delegatingSecurityContext;

    public SecurityContextHolderExecutor(Executor executor) {
        this(executor, null);
    }

    public SecurityContextHolderExecutor(Executor executor, SecurityContext securityContext) {
        this.executor = executor;
        this.delegatingSecurityContext = (securityContext == null) ? SecurityContextHolder.getContext() : securityContext;
    }

    public Runnable wrap(Runnable runnable) {
        return new SecurityContextHolderRunnable(runnable, this.delegatingSecurityContext);
    }

    public void execute(Runnable r) {
        // 对Runnable进行包装
        Runnable task = wrap(r);
        this.executor.execute(task);
    }
}
