package com.yz.oauth2.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟自定义db查询用户
 * @author andrew
 * @date 2020-10-13
 */
public class InMemoryUserDetailsServices implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public InMemoryUserDetailsServices(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private static final Map<String, com.yz.oauth2.security.User> users = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        users.computeIfAbsent("admin", s -> createNewUser(s, s, "ADMIN,GUEST"));
        users.computeIfAbsent("guest", s -> createNewUser(s, s, "GUEST"));
    }

    public User createNewUser(String username, String password, String authorities) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }
}
