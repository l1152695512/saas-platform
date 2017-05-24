package com.jikezhiji.domain.autoconfigure;

import com.jikezhiji.domain.query.QueryProjection;
import com.jikezhiji.domain.query.QueryRepositorySupport;
import com.jikezhiji.domain.query.mongo.MongoQueryRepository;
import com.jikezhiji.domain.query.mongo.MongoQuerydslRepositorySupport;
import com.jikezhiji.tenant.scope.annotation.TenantScope;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.StringUtils;

import java.net.UnknownHostException;

/**
 * Created by E355 on 2016/9/28.
 */
@Configuration
@ConditionalOnClass({ Mongo.class, MongoTemplate.class })
@ConditionalOnProperty(prefix="spring.query.mongodb")
public class MongoQueryRepositoryAutoConfiguration  implements InitializingBean {


    private final ApplicationContext applicationContext;
    private final Environment environment;
    private final MongoClientOptions options;

    public MongoQueryRepositoryAutoConfiguration(ApplicationContext applicationContext,
                                                 @SuppressWarnings("SpringJavaAutowiringInspection") MongoClientOptions options) {
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
        this.options = options;
    }


    private MongoProperties queryProperties;
    private MongoDbFactory queryFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.queryProperties = queryProperties();
        this.queryFactory = queryFactory();
    }

    @Bean
    @TenantScope
    @ConfigurationProperties("spring.query.mongodb")
    public MongoProperties queryProperties(){
        return new MongoProperties();
    }

    @Bean
    @TenantScope
    public MongoClient queryClient() throws UnknownHostException {
        return queryProperties.createMongoClient(options, environment);
    }

    @Bean
    @TenantScope
    public SimpleMongoDbFactory queryFactory() throws Exception {
        return new SimpleMongoDbFactory(queryClient(), queryProperties.getMongoClientDatabase());
    }


    @Bean
    public MongoMappingContext queryMapping() throws ClassNotFoundException {
        MongoMappingContext context = new MongoMappingContext();
        context.setInitialEntitySet(new EntityScanner(this.applicationContext).scan(Document.class, Persistent.class, QueryProjection.class));
        Class<?> strategyClass = this.queryProperties.getFieldNamingStrategy();
        if (strategyClass != null) {
            context.setFieldNamingStrategy((FieldNamingStrategy) BeanUtils.instantiate(strategyClass));
        }
        return context;
    }


    @Bean
    public MappingMongoConverter queryConverter() throws Exception {
        return new MappingMongoConverter(new DefaultDbRefResolver(queryFactory), queryMapping());
    }


    @Bean
    public MongoTemplate queryTemplate() throws Exception {
        return new MongoTemplate(queryFactory, queryConverter());
    }


    @Bean
    @ConditionalOnMissingBean
    public QueryRepositorySupport queryRepository() throws Exception {
        return new MongoQueryRepository(queryTemplate());
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoQuerydslRepositorySupport querydslRepositorySupport() throws Exception {
        return new MongoQuerydslRepositorySupport(queryTemplate());
    }
    @Bean
    @TenantScope
    public SimpleMongoDbFactory queryGridFactory() throws Exception {
        return StringUtils.hasText(queryProperties.getGridFsDatabase()) ?
                new SimpleMongoDbFactory(queryClient(), queryProperties.getGridFsDatabase()) : queryFactory();
    }

    @Bean
    public GridFsTemplate commandGridFsTemplate() throws Exception {
        return new GridFsTemplate(queryGridFactory(), queryConverter());
    }
}

