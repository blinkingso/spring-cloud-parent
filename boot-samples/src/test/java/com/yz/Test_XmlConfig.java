package com.yz;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class Test_XmlConfig {

    private ClassPathXmlApplicationContext applicationContext;

    @Before
    public void before() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:/spring-context.xml");
        applicationContext.start();
    }

    @After
    public void close() {
        applicationContext.close();
    }

    @Test
    public void testXmlConfig() {
        Arrays.stream(applicationContext.getBeanDefinitionNames()).
                forEach(s -> System.out.println("容器中的bean: " + s + "====>" + applicationContext.getBean(s)));
    }
}
