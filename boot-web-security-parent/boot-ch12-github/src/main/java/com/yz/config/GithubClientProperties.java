package com.yz.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author andrew
 * @date 2020-11-06
 */
@Data
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "github")
public class GithubClientProperties implements Serializable {
    private String registrationId;
    private String clientId;
    private String clientSecret;
    // 客户端app名称
    private String clientName;
    // 重定向回调页面
    private String redirectUrl;
    // 权限
    private List<String> scope;
    // 获取token的endpoint
    private String tokenUri;
    // 用户信息获取的endpoint
    private String userInfoUri;
    // 属性名
    private String userNameAttributeName;
    // 授权类型
    private String authorizationGrantType;
    // 重定向uri模板
    private String redirectUriTemplate;
    // 认证的endpoint
    private String authorizationUri;
}
