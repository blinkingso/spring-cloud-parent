package config;

import com.yz.bean.Person;
import com.yz.dao.PersonDao;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@PropertySource(value = {"classpath:conf.properties"})
//@EnableConfigurationProperties(value = {Person.class})
@Configuration
@ComponentScan(basePackages = {"com.yz.bean", "com.yz.controller", "com.yz.dao"})
public class MainAutowiredConfig {

    @Bean
    public Person person() {
        return new Person();
    }

    // @Primary注解配合@Autowired注解使用时优先被解析依赖, 在JSR-250@Resource时不能被正常解析
    @Bean
    @Primary
    public PersonDao personDao2() {
        PersonDao dao = new PersonDao();
        dao.setLabel("222");
        return dao;
    }
}
