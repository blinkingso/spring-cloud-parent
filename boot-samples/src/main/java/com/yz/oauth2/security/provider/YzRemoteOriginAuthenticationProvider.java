package com.yz.oauth2.security.provider;

import com.yz.oauth2.security.authentication.YzRemoteOriginAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author andrew
 * @date 2020-10-14
 */
@Component
@Slf4j
public class YzRemoteOriginAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取header中的信息
        final String name = authentication.getName();
        if ("localhost:8080".equals(name)) {
            return new YzRemoteOriginAuthentication(name, null, List.of(() -> "admin"));
        }

        throw new UsernameNotFoundException("请求头认证失败");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return YzRemoteOriginAuthentication.class.equals(authentication);
    }
}
