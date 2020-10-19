package com.yz.controller;

import com.yz.config.SecurityContextHolderExecutor;
import com.yz.pojo.authentication.OtpAuthentication;
import com.yz.pojo.authentication.TokenAuthentication;
import com.yz.pojo.authentication.UsernamePasswordAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author andrew
 * @date 2020-10-15
 */
@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello")
    public String helloSync() {
        hello1();
        return "helloSync";
    }

    @GetMapping("/helloAsync")
//    @Async
    public String helloAsync() {
        Runnable r0 = this::hello1;
        Runnable r = new DelegatingSecurityContextRunnable(this::hello1);
        final ExecutorService es = Executors.newSingleThreadExecutor();
        final DelegatingSecurityContextExecutor ds = new DelegatingSecurityContextExecutor(Executors.newSingleThreadExecutor());
        final DelegatingSecurityContextExecutorService s = new DelegatingSecurityContextExecutorService(es);
        final SecurityContextHolderExecutor sce = new SecurityContextHolderExecutor(es);
        es.execute(r);
        ds.execute(r0);
        ds.execute(r);
        s.execute(r0);
        sce.execute(r0);
        es.shutdown();
        return "helloAsync";
    }

    private void hello1() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("保存的Authentication为: " + authentication.getClass().getName());
        if (authentication instanceof TokenAuthentication) {
            String token = authentication.getName();
            log.info("controller中解析到的token为: " + token);
        } else if (authentication instanceof UsernamePasswordAuthentication) {
            String username = authentication.getName();
            log.info("controller中解析到的username为: " + username);
        } else if (authentication instanceof OtpAuthentication) {
            String otp = authentication.getCredentials().toString();
            String username = authentication.getName();
            log.info("controller中解析到的username为: " + username + ", otp is: " + otp);
        }
    }
}
