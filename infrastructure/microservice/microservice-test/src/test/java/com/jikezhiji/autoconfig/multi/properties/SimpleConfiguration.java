package com.jikezhiji.autoconfig.multi.properties;

import com.jikezhiji.autoconfig.SimpleProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by E355 on 2016/9/27.
 */
@Configuration
public class SimpleConfiguration {


    @Bean
    @ConfigurationProperties("core.dev")
    public SimpleProperties dev(){
        return new SimpleProperties();
    }

    @Bean
    @ConfigurationProperties("core.test")
    public SimpleProperties test(){
        return new SimpleProperties();
    }
}
