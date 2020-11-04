package com.yz.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Arrays;
import java.util.Base64;

/**
 * @author andrew
 * @date 2020-11-04
 */
@RestController
@RequestMapping
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    public static void main(String[] args) throws Exception {
        final KeyGenerator key = KeyGenerator.getInstance("HmacSHA256");
        key.init(256);
        final SecretKey secretKey = key.generateKey();
        System.out.println(new String((secretKey.getEncoded()), "utf-8"));
        System.out.println(new String(Base64.getEncoder().encode(secretKey.getEncoded())));

        var key2 = Keys.hmacShaKeyFor(secretKey.getEncoded());
        System.out.println(key2);
    }
}
