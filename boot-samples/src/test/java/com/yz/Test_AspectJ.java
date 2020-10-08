package com.yz;

import com.yz.aop.DaoAspect;
import com.yz.dao.PersonDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

@Slf4j
public class Test_AspectJ {

    private AnnotationConfigApplicationContext context;

    @Before
    public void before() {
        context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(DaoAspect.class);
        context.refresh();
    }

    @After
    public void after() {
        context.close();
    }

    @Test
    public void test_Aspect() {
        Arrays.stream(context.getBeanDefinitionNames())
                .forEach(s -> {
                    log.info("ASPECT容器中的BEAN为： " + s + "===>" + context.getBean(s));
                });
        context.getBean(PersonDao.class).lists();
    }
}
