package com.yz;

import com.yz.bean.Person;
import com.yz.controller.PersonController;
import config.MainAutowiredConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Test_Autowired {

    private AnnotationConfigApplicationContext context;

    @Before
    public void initContext() {
        context = new AnnotationConfigApplicationContext(MainAutowiredConfig.class);
        context.start();
    }

    @Test
    public void test_bean() {
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
//        System.out.println(context.getBean("person"));
//        System.out.println(context.getBean("person-com.yz.bean.Person"));
        System.out.println(context.getBean(Person.class));
    }

    @Test
    public void test_Autowired() {
        context.getBean(PersonController.class).dao();
    }
}
