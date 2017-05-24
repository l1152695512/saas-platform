package com.jikezhiji.rest.autoconfigure;

import com.jikezhiji.core.Subject;
import com.jikezhiji.rest.ReleaseContextCallback;
import com.jikezhiji.rest.security.JwtSubjectFactory;
import com.jikezhiji.rest.SecurityRequestFilter;
import com.jikezhiji.rest.security.SubjectFactory;
import com.jikezhiji.rest.feature.FastJsonFeature;
import com.jikezhiji.rest.feature.IsomorphicFeature;
import com.jikezhiji.rest.provider.JAXBContextResolver;
import com.jikezhiji.rest.provider.SimpleExceptionMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.glassfish.jersey.server.mvc.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

/**
 * Created by E355 on 2016/9/14.
 */
@ConditionalOnBean(type = "org.glassfish.jersey.server.ResourceConfig")
@Configuration
@EnableConfigurationProperties(RestProperties.class)
@ImportAutoConfiguration(JerseyAutoConfiguration.class)
public class RestJerseyAutoConfiguration {

    private RestProperties jersey;

    public RestJerseyAutoConfiguration(RestProperties jersey){
        this.jersey = jersey;
    }


    @Bean
    public ResourceConfigCustomizer defaultConfiguration(){
        return config -> {
            config.property(ServerProperties.RESOURCE_VALIDATION_IGNORE_ERRORS, true);
            config.property(ServerProperties.RESOURCE_VALIDATION_DISABLE, true);
            config.property(ServerProperties.TRACING, TracingConfig.ALL);
            config.packages(jersey.getScanPackages());
            config.addProperties(jersey.getRestBuiltInProperties());
            config.register(LoggingFeature.class);
            config.register(SimpleExceptionMapper.class);
            config.register(JAXBContextResolver.class);
            config.register(ReleaseContextCallback.class);
        };
    }

    @Bean
    @ConditionalOnClass(com.alibaba.fastjson.JSON.class)
    public ResourceConfigCustomizer fastjson(){
        return config -> config.register(FastJsonFeature.class);
    }

    @Bean
    @ConditionalOnClass(Template.class)
    public ResourceConfigCustomizer isomorphic(){
        return config -> config.register(IsomorphicFeature.class);
    }

    @Bean
    public Base64.Decoder base64Decoder(){
        return Base64.getDecoder();
    }

    @Bean
    @ConditionalOnClass(JwtBuilder.class)
    @ConditionalOnProperty(prefix = "spring.jersey", name = "enableJwt", havingValue = "false")
    public JwtBuilder jwtBuilder(Base64.Decoder decoder){
        return Jwts.builder().signWith(SignatureAlgorithm.HS256,decoder.decode(jersey.getJwtSecret()));
    }

    @Bean
    @ConditionalOnClass(JwtParser.class)
    @ConditionalOnProperty(prefix = "spring.jersey", name = "enableJwt", havingValue = "false")
    public JwtParser jwtParser(Base64.Decoder decoder){
        return Jwts.parser().setSigningKey(decoder.decode(jersey.getJwtSecret()));
    }

    @Bean
    @Autowired(required = false)
    public SubjectFactory<String> subjectFactory(JwtParser parser){
        if(parser == null) {
            return token -> new Subject(new Subject.Visitor(token));
        }
        return new JwtSubjectFactory(parser);
    }


    @Bean
    public ResourceConfigCustomizer filter(SubjectFactory<String> factory) {
        return config -> config.register(new SecurityRequestFilter(factory));
    }
}
