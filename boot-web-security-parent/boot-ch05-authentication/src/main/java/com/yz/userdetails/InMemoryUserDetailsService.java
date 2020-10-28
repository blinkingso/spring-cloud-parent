package com.yz.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * manager users using cache
 *
 * @author andrew
 * @date 2020-10-27
 */
public class InMemoryUserDetailsService implements UserDetailsService {

    // cache user in memory
    private final List<UserDetails> cache;

    public InMemoryUserDetailsService(List<UserDetails> cache) {
        if (cache.isEmpty() || cache.size() > Integer.MAX_VALUE / 10) {
            throw new IllegalStateException("users cache invalid");
        }
        this.cache = cache;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("username can not be null");
        }
        return cache.stream().filter(u -> username.equals(u.getUsername()))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));

    }
}
