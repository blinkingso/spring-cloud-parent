package com.yz.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager

/**
 * web security 配置
 * @author andrew
 * @date 2020-09-18
 */
@EnableWebSecurity
class SecurityConfigure : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/oauth2/keys").permitAll()
                // 其他接口请求需要登陆认证
                .anyRequest().authenticated()
                .and()
                .formLogin()
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun users(): UserDetailsService {
        val guest = User.withUsername("guest").password(passwordEncoder().encode("guest")).roles("GUEST").build()
        val admin = User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("GUEST", "ADMIN").build()
        return with(InMemoryUserDetailsManager()) {
            this.createUser(guest)
            this.createUser(admin)
            this
        }
    }

    @Bean(name = [BeanIds.AUTHENTICATION_MANAGER])
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}