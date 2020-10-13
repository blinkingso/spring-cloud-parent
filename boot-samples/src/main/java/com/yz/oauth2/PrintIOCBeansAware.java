package com.yz.oauth2;

import com.yz.oauth2.security.Oauth2AuthorizationServerInMemory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author andrew
 * @date 2020-10-13
 */
@Component
@Slf4j
@Order
@Data
//@ConditionalOnBean(value = {ClientDetailsService.class, Oauth2AuthorizationServerInMemory.class})
public class PrintIOCBeansAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
