package com.yz.clients.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.Jackson2ArrayOrStringDeserializer;
import org.springframework.security.oauth2.provider.client.JacksonArrayOrStringDeserializer;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * @author andrew
 * @date 2020-10-21
 */
@Data
//@ToString
//@EqualsAndHashCode
@Entity(name = "tb_client_details")
public class YzClientDetails extends BaseClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "client_id")
    private String clientId;
    private String scope;
    @Column(columnDefinition = "authorized_grant_types")
    private String authorizedGrantTypes;

}
