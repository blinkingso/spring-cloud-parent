package com.yz.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author andrew
 * @date 2020-11-17
 */
@Data
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class JwtProperties {
    // 对称加密
    private Boolean isSymmetric;
    // 算法名称
    private String algorithm;
    private String signingKey;
    private String privateKey;
    private String publicKey;
    private String alias;
    private String storePass;
    private String keyPass;
}
