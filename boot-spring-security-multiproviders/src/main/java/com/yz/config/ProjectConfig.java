package com.yz.config;

import com.yz.dao.UserRepository;
import com.yz.service.DefaultUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * @author andrew
 * @date 2020-10-15
 */
@Configuration
@ComponentScan("com.yz")
@EnableAsync
public class ProjectConfig {

    @Bean
    public InitializingBean initializingBean() {
        return () -> {
            // 子线程继承问题
            // 设置SecurityContextHolder的SecurityContextHolderStrategy为InheritableThreadLocalSecurityContextHolderStrategy
            // 解决异步请求模式下子线程无法获取父线程中的ThreadLocal缓存的问题
            // 同样可以使用: DelegatingSecurityContextRunnable
            // 或者: DelegatingSecurityContextExecutor
            // 或者: DelegatingSecurityContextExecutorService
            // 线程池实现此功能, 更加方便便捷
//            SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        };
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create(this.getClass().getClassLoader())
                .type(DriverManagerDataSource.class)
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/yz?useSSL=false")
                .username("root")
                .password("123456")
                .build();
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService(@Autowired UserRepository userRepository) {
        return new DefaultUserDetailsService(userRepository);
    }
}
