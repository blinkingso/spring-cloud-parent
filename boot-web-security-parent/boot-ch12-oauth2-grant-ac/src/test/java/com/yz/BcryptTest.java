package com.yz;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author andrew
 * @date 2020-11-09
 */
public class BcryptTest {

    @Test
    public void printBcrypt() {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }

    @Test
    public void echoHome() {
        System.out.println(System.getProperty("user.home"));
    }
}
