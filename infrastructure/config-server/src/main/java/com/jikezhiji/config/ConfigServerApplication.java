package com.jikezhiji.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@EnableConfigServer
public class ConfigServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}