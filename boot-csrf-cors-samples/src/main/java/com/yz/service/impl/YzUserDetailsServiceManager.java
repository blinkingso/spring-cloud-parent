package com.yz.service.impl;

import com.yz.pojo.SecurityUser;
import com.yz.pojo.User;
import com.yz.repository.UserRepository;
import com.yz.service.YzUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理服务
 *
 * @author andrew
 * @date 2020-10-24
 */
@Slf4j
public class YzUserDetailsServiceManager implements UserDetailsManager, YzUserDetailsService {

    // user operations repository
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public YzUserDetailsServiceManager(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        Assert.notNull(userRepository, "userRepository must not be null");
        Assert.notNull(passwordEncoder, "passwordEncoder must not be null");
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> users() {
        return this.userRepository.findAll();
    }

    // return null if not present
    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username).orElse(null);
    }

    @Override
    public void createUser(UserDetails user) {
        User entity = new User();
        entity.setUsername(user.getUsername());
        entity.setPassword(this.passwordEncoder.encode(user.getPassword()));
        if (!user.getAuthorities().isEmpty()) {
            // SET AUTHORITIES
            entity.setAuthorities(user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(",")));
        }
        this.userRepository.save(entity);
    }

    @Transactional
    @Override
    public void updateUser(UserDetails user) {
        // STEP 1 FIND USER user
        this.userRepository.findUserByUsername(user.getUsername()).ifPresent(u -> {
            u.setPassword(this.passwordEncoder.encode(user.getPassword()));
            u.setAuthorities(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")));
            this.userRepository.save(u);
        });
    }

    @Override
    public void deleteUser(String username) {
        this.userRepository.findUserByUsername(username).ifPresent(u -> {
            this.userRepository.deleteById(u.getId());
        });
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
        return this.userRepository.findUserByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }
}
