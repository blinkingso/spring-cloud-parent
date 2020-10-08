package com.yz;

import config.MainAutowiredConfig;
import config.MainConfigOfProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

@Slf4j
public class Test_Profile {

    private AnnotationConfigApplicationContext context;

    @Before
    public void initContext() {
        context = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
        context.getEnvironment().addActiveProfile("test");
        context.getEnvironment().setActiveProfiles("prod");
        context.start();
    }

    @Test
    public void test_Profile() {
        Arrays.stream(context.getBeanDefinitionNames()).forEach(s -> log.info("容器中的bean：" + s));
    }
}
