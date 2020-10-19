package com.yz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author andrew
 * @date 2020-10-19
 */
@Slf4j
public final class SecurityContextHolderRunnable implements Runnable {

    // 委托对象
    private final Runnable delegate;
    private SecurityContext originSecurityContext;
    private final SecurityContext delegatingSecurityContext;

    protected SecurityContextHolderRunnable(Runnable delegate, SecurityContext delegatingSecurityContext) {
        this.delegate = delegate;
        this.delegatingSecurityContext = delegatingSecurityContext;
        this.originSecurityContext = SecurityContextHolder.getContext();
    }

    @Override
    public void run() {
        // 获取context设置到当前线程环境中
        SecurityContextHolder.setContext(this.delegatingSecurityContext);
        try {
            log.info("获取到了SecurityContextHolder" + SecurityContextHolder.getContext());
            delegate.run();
            log.info("自定义线程池执行SecurityContextHolder结束" + SecurityContextHolder.getContext());
        } finally {
//            SecurityContextHolder.clearContext();
            SecurityContextHolder.setContext(this.originSecurityContext);
        }
    }
}
