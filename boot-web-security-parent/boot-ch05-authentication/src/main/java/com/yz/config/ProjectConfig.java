package com.yz.config;

import com.yz.formlogin.CustomAuthenticationFailureHandler;
import com.yz.formlogin.CustomAuthenticationSuccessHandler;
import com.yz.httpbasic.CustomEntryPoint;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Project SpringBoot Configuration
 * <p>
 * Summary:
 * 1. The  {@link AuthenticationProvider} is the component that allows you to implement custom authentication logic
 * 2. When you implement custom authentication logic, it's a good practice to keep the responsibilities decoupled.
 * For user management, the {@link AuthenticationProvider} delegates to a {@link UserDetailsService}, and for the
 * responsibility of password validation, the AuthenticationProvider delegates to a {@link PasswordEncoder}
 * 3. The {@link org.springframework.security.core.context.SecurityContext} keeps details about the authenticated
 * entity after successful authentication.
 * 4. You can use three strategies to manage the security context : MODE_THREADLOCAL, MODE_INTERITABLETHREADLOCAL,
 * and MODE_GLOBAL(only useful for standalone application). Access from different threads to the security context
 * details works differently depending on the mode you choose.
 * 5. Remember that when using the shared-thread local mode, it's only applied for threads that are managed by Spring
 * . The framework won't copy the security context for the threads that are not governed by it.
 * 6. Spring Security offers you great utility classes to manage the threads created by your code, about which the
 * framework is now aware. To manage the {@link org.springframework.security.core.context.SecurityContext} for the
 * threads that you create, you can use
 * - {@link org.springframework.security.concurrent.DelegatingSecurityContextRunnable}
 * - {@link org.springframework.security.concurrent.DelegatingSecurityContextExecutor}
 * - {@link org.springframework.security.concurrent.DelegatingSecurityContextCallable}
 * etc..
 * 7. Spring Security autoconfigures a form for login and an option log out with the form-based login authentication
 * method,{@see formLogin().} It is straightforward to use when developing small web application.
 * 8. The formLogin authentication method is highly customizable. Moreover, you can use this type of authentication
 * together with the HTTP Basic method.
 *
 * @author andrew
 * @date 2020-10-28
 */
@Configuration
@EnableAsync
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        final InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        userDetailsManager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .authorities("ADMIN").build());
        return userDetailsManager;
    }

    @Autowired
    @Qualifier("customAuthenticationProvider")
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic(conf -> {
            conf.realmName("OTHER");
            // 认证失败时的处理方案. 失败处理入口
            conf.authenticationEntryPoint(new CustomEntryPoint());
        });
        http.formLogin().defaultSuccessUrl("/home", false)
                // 自定义登陆认证成功或失败的处理逻辑
                .successHandler(new CustomAuthenticationSuccessHandler())
                .failureHandler(new CustomAuthenticationFailureHandler());
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    /**
     * 设置SecurityContextHolder处理SecurityContext的策略
     *
     * @return bean
     */
    @Bean
    public InitializingBean setupSecurityContextStrategy() {
        return () -> {
            // 增强的ThreadLocal实现了对OriginThread中的ThreadLocal状态的复制能力
//            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        };
    }
}
