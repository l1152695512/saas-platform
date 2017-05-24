package com.jikezhiji.autoconfig.dependency.priority;

import com.jikezhiji.autoconfig.SimpleProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created by E355 on 2016/9/27.
 */
@Configuration
public class SimpleConfiguration1 {


    @Bean
    @ConfigurationProperties("core.dev")
    public SimpleProperties dev1(){
        return new SimpleProperties();
    }

    @Bean
    @ConfigurationProperties("core.dev")
    public SimpleProperties dev2(){
        return new SimpleProperties();
    }
}
