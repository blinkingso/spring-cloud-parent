package com.yz;

import com.yz.bean.Color;
import config.BeanConfig;
import config.MainConfigOfProfile;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

@Slf4j
public class Test_IOC {

    private AnnotationConfigApplicationContext context;

    @Before
    public void initContext() {
        context = new AnnotationConfigApplicationContext(BeanConfig.class);
        context.start();
    }

    @After
    public void close() {
        context.close();
    }

    @Test
    public void test_IOC() {
        Arrays.stream(context.getBeanDefinitionNames()).forEach(s -> log.info("容器中的bean：" + s + "=====>" + context.getBean(s)));

        Color color = (Color) context.getBean("com.yz.bean.Color");
        System.out.println(color);
    }
}
