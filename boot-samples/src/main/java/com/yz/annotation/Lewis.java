package com.yz.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义注解 标记方法被远程调用 类似Dubbo的@Service
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Lewis {

    /**
     * method name cache的key
     * @return cache key
     */
    String value() default "";

    /**
     * 服务版本号
     * @return version
     */
    String version() default "0.0.0";
}
