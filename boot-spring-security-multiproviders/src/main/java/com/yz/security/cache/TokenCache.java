package com.yz.security.cache;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author andrew
 * @date 2020-10-16
 */
@Component
public class TokenCache {

    private static final int MAX_SIZE = 10240;
    /**
     * cache 的token的header名称
     */
    public static final String HEADER_AUTHORIZATION = HttpHeaders.AUTHORIZATION;

    private final Set<String> tokens = Collections.synchronizedSet(new HashSet<>());

    /**
     * 生成token
     *
     * @return token
     */
    public static String createToken() {
        return UUID.randomUUID().toString();
    }

    public Boolean put(String token) {
        if (this.tokens.size() >= MAX_SIZE) {
            clear();
        }
        return tokens.add(token);
    }

    public void clear() {
        this.tokens.clear();
    }

    public Boolean contains(String token) {
        return this.tokens.contains(token);
    }

    public Boolean remove(String token) {
        return this.tokens.remove(token);
    }
}
