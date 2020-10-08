package com.yz.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Data
@Component
@Slf4j
public class CarAware implements ApplicationContextAware, BeanNameAware, InitializingBean {

    private ApplicationContext applicationContext;
    private String beanName;

    @Override
    public void setBeanName(String name) {
        log.info("设置BeanName为" + name + "....");
        this.beanName = beanName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("设置ApplicationContext。。。。");
        this.applicationContext = applicationContext;
        Arrays.stream(this.applicationContext.getBeanDefinitionNames())
                .forEach(s -> {
                    log.info("容器中的bean： " + s);
                });
        log.info("设置ApplicationContext完成....");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet方法开始执行啦");
    }
}
