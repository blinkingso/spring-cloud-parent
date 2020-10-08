package com.yz.controller;

import com.yz.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class PersonController {

    // spring 标准
     @Autowired
    // AutowiredAnnotationBeanPostProcessor @Autowired解析完成自动装配功能
    // JSR-250
//    @Resource
    // JSR-330标准 @Inject 导入javax.inject
    private PersonDao personDao;

    public void dao() {
        System.out.println(personDao);
    }
}
