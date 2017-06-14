package com.jikezhiji.survey;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.util.AnnotatedTypeScanner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.CustomValidatorBean;

import javax.validation.Validator;


@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(repositoryImplementationPostfix="CustomImpl")
@EnableTransactionManagement
public class SurveyApplication {


	@Bean
	public Validator defaultValidator(){
		return new CustomValidatorBean();
	}

	@Bean
	public ProjectionFactory projectionFactory(){
		return new SpelAwareProxyProjectionFactory();
	}

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer(ApplicationContext applicationContext) {

		return new RepositoryRestConfigurerAdapter() {
			@Override
			public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
				objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			}

			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				AnnotatedTypeScanner scanner = new AnnotatedTypeScanner(Projection.class);
				scanner.setEnvironment(applicationContext.getEnvironment());
				scanner.setResourceLoader(applicationContext);

				scanner.findTypes("com.jikezhiji.survey.rest.value").forEach(projection->{
					config.getProjectionConfiguration().addProjection(projection);
				});
			}
		};
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(SurveyApplication.class, args);
	}



}
