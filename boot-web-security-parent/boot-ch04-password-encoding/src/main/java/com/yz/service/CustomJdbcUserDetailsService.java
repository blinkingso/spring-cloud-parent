package com.yz.service;

import com.yz.repository.UserJpaRepository;
import com.yz.userdetails.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 基于简单jpa查询的模式查询用户
 *
 * @author andrew
 * @date 2020-10-27
 */
@Slf4j
@Service
public class CustomJdbcUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public CustomJdbcUserDetailsService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("username can not be null");
        }
        log.info("now start to load user");
        return this.userJpaRepository.findUserByUsername(username)
                .map(u -> {
                    u.setPassword("{bcrypt}".concat(u.getPassword()));
                    return u;
                })
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}
