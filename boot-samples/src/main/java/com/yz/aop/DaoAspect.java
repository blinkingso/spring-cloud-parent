package com.yz.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.List;

@ComponentScan(basePackages = {"com.yz.controller", "com.yz.dao"})
@Configuration
@EnableAspectJAutoProxy
@Aspect
@Slf4j
public class DaoAspect {

    @Pointcut("execution(public * com.yz.dao.PersonDao.lists())")
    public void lists() {
    }

    @Before("lists()")
    public void aopStart(JoinPoint joinPoint) {
        log.info("准备调用方法： " + joinPoint.getSignature().getName() + ".....");
    }

    @After("lists()")
    public void aopEnd(JoinPoint joinPoint) {
        log.info("调用方法" + joinPoint.getSignature().getName() + "之后");
    }

    @AfterReturning(pointcut = "lists()", returning = "list")
    public void listsReturn(JoinPoint joinPoint, List<String> list) {
        log.info("调用方法" + joinPoint.getSignature().getName() + "正常完成并返回");
        list.forEach(
                s -> log.info("遍历结果为： " + s)
        );
    }

    @AfterThrowing(pointcut = "lists()", throwing = "ex")
    public void listsThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("方法" + joinPoint.getSignature().getName() + "调用发生异常 ： " + ex);
    }
}
