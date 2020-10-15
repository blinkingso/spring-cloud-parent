package com.yz.service;

import com.yz.dao.UserRepository;
import com.yz.pojo.SecurityUser;
import com.yz.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author andrew
 * @date 2020-10-15
 */
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DefaultUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }

        final Optional<User> opt = userRepository.findUserByUsername(username);
        final User currentUser = opt.orElseThrow(() -> new UsernameNotFoundException("用户名不存在"));
        // 将查询到的用户返回
        return new SecurityUser(currentUser);
    }
}
