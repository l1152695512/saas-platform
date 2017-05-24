package com.jikezhiji.autoconfig.refresh.scope;

import com.jikezhiji.autoconfig.SimpleProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;

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

    @Bean(initMethod = "initSP")
    @RefreshScope
    public SimpleProperties testValue(@Value("${core.test.name}") String name){
        SimpleProperties simpleProperties = new SimpleProperties();
        simpleProperties.setName(name);
        return simpleProperties;
    }

    @Bean
    @ConfigurationProperties("core.test")
    public SimpleProperties test(){
        return new SimpleProperties();
    }
}
