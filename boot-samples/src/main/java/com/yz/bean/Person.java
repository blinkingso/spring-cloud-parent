package com.yz.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ToString
//@ConfigurationProperties(prefix = "person")
//@Component(value = "person")
public class Person {

    @Value("${person.name}")
    private String name;
    @Value("${person.sex}")
    private String sex;
    @Value("${person.age}")
    private Integer age;
}
