package com.jikezhiji.tenant;

import com.jikezhiji.commons.DiscoveryClientPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

/**
 * Created by E355 on 2016/9/9.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableJpaAuditing
public class TenantApp {

    @Bean
    public DiscoveryClientPostProcessor discoveryClientPostProcessor(){
        return new DiscoveryClientPostProcessor();
    }


    public static void main(String[] args) throws IOException {
        SpringApplication.run(TenantApp.class,args);
    }
}
