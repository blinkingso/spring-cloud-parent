package com.yz.config;

import com.yz.csrf.YzCsrfRepository;
import com.yz.filter.YzCsrfTokenLoggerFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author andrew
 * @date 2020-10-20
 */
@Configuration
@EnableWebMvc
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // 自定义csrf存储加载, 这里仅测试加入了日志, 委托HttpSessionCsrfTokenRepository的实现
        http.csrf(c -> {
            c.csrfTokenRepository(new YzCsrfRepository(new HttpSessionCsrfTokenRepository()));
        });

        // 添加filter在CsrfFilter之后, 打印CsrfToken
        http.addFilterAfter(new YzCsrfTokenLoggerFilter(), CsrfFilter.class);
    }
}
