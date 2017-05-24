package com.jikezhiji.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import javax.validation.Validator;

@SpringBootApplication
@EnableDiscoveryClient
public class SurveyApplication {


	@Bean
	@ConditionalOnMissingBean
	public Validator defaultValidator(){
		return new CustomValidatorBean();
	}

	public static void main(String[] args) {
		SpringApplication.run(SurveyApplication.class, args);
	}



}
