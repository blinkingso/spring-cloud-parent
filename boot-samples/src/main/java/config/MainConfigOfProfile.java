package config;

import com.yz.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:/conf.properties"})
public class MainConfigOfProfile {

    @Bean
    @Profile("dev")
    public Person personDev() {
        Person dev = new Person();
        dev.setName("dev");
        return dev;
    }

    @Bean
    @Profile("test")
    public Person personTest() {
        Person dev = new Person();
        dev.setName("test");
        return dev;
    }

    @Bean
    @Profile("prod")
    public Person personProd() {
        Person dev = new Person();
        dev.setName("prod");
        return dev;
    }
}
