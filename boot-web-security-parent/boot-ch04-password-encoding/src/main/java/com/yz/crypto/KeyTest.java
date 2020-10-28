package com.yz.crypto;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.keygen.StringKeyGenerator;

/**
 * @author andrew
 * @date 2020-10-28
 */
public class KeyTest {

    public static void main(String[] args) {
        final StringKeyGenerator kg = KeyGenerators.string();
        // 8-byte key 转化为16进制的字符串
        final String salt = kg.generateKey();
        final byte[] bytes = "a".getBytes();
        System.out.println(Hex.encode(bytes));
        System.out.println(salt);

        final TextEncryptor encryptor = Encryptors.text("password", salt);
        final String encryptedText = encryptor.encrypt("hello");
        System.out.println("encryptedText is " + encryptedText);
        final String originText = encryptor.decrypt(encryptedText);
        System.out.println(originText.equals("hello"));
    }
}
