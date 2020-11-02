package com.yz.config;

import com.yz.filters.StaticKeyAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author andrew
 * @date 2020-10-30
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = "security.enabled", havingValue = "true")
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new StaticKeyAuthenticationFilter(), BasicAuthenticationFilter.class);
        http.httpBasic();
        // READ OR WRITE AUTHORITY
        // AUTHORITIES WAYS : hasAuthority(), hasAnyAuthority(), access() implementing with previous methods.
//        http.authorizeRequests().anyRequest().hasAuthority("WRITE");
//        http.authorizeRequests().anyRequest().hasAnyAuthority("READ", "WRITE");
        // as customize functional with SpEL
//        http.authorizeRequests().anyRequest().access("hasAuthority('WRITE') and hasAuthority('DELETE')");

        // PARTY 2 AUTHORITY VIA ROLE
        http.authorizeRequests()
                // hello method are denied
                .antMatchers("/hello").denyAll()

                // request '/a' POST authenticated but GET is permitted simulate dis-csrf for path '/a'
                .mvcMatchers(HttpMethod.GET, "/a").authenticated()
                .mvcMatchers(HttpMethod.POST, "/a").permitAll()

                // all prefix authenticated
                .mvcMatchers("/a/b/**").authenticated()

                // SpEL matches regular expression permitAll()
                .mvcMatchers("/product/{code:^[0-9]*$}").permitAll()

                // email .com matches permitAll()
                .mvcMatchers("/email/{email:.*(.+@.+\\.com)}").permitAll()

                // video/{country}/{language} authenticated via regex
                .regexMatchers(".*/(us|uk|ca)+/(en|fr).*").authenticated()

//                .anyRequest().hasAnyRole("ADMIN", "GUEST");
                .anyRequest().hasRole("ADMIN");

        http.csrf().disable();
    }
}
