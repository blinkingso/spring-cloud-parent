package com.yz.util;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.GeneratedValue;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author andrew
 * @date 2020-11-03
 */
@Slf4j
public class GenerateCodeUtil {

    // codes
    private static final char[] CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();


    private GenerateCodeUtil() {
    }

    public static String generateCode() {
        try {
            return generateCode(6);
        } catch (Exception e) {
            log.error("error : ", e);
            throw new RuntimeException("Problem when generating the random code .");
        }
    }

    public static String generateCode(int length) throws RuntimeException {
        if (length < 4) {
            throw new IllegalArgumentException("length must grater than 4 ...");
        }

        final Random random = new Random();
        final char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = CHARACTERS[random.nextInt(CHARACTERS.length)];
        }

        return String.valueOf(result);
    }

    public static void main(String[] args) {
        System.out.println(generateCode());
        var arr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    }
}
