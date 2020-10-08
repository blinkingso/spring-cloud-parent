package config;

import com.yz.bean.Color;
import com.yz.bean.ColorBeanDefinitionRegistrar;
import com.yz.bean.ColorImport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ioc容器引入bean的方式
 * 1. @Bean注解
 * 2. @Import注解
 */
@Configuration
//@Import(ColorBeanDefinitionRegistrar.class)
@Import(ColorImport.class)
public class BeanConfig {

    @Bean
    public Color color() {
        return new Color();
    }
}
