package com.yz.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author andrew
 * @date 2020-11-09
 */
@Data
@ToString
@EqualsAndHashCode
@Entity(name = "tb_client_details")
public class CustomClientDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;
    @Column(name = "client_secret", nullable = false)
    private String clientSecret;
    @Column(name = "client_name", nullable = false)
    private String clientName;
    @Column(name = "redirect_urls", nullable = false)
    private String redirectUrls;
    @Column(name = "authorization_grant_types", nullable = false)
    private String authorizationGrantTypes;
    @Column(nullable = false)
    private String scopes;
    @Column(nullable = false)
    private String authorities;
    @Column(name = "resource_ids")
    private String resourceIds;
    @Column(name = "create_time")
    private Date createTime;
}
