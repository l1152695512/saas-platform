package com.jikezhiji.domain.autoconfigure;

import com.jikezhiji.domain.command.repository.CommandRepositorySupport;
import com.jikezhiji.domain.command.converters.ConverterHolder;
import com.jikezhiji.domain.command.core.simple.data.MongoSnapshotRepository;
import com.jikezhiji.tenant.scope.annotation.TenantScope;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScanner;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
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
@ConditionalOnProperty(prefix="spring.command.mongodb")
public class MongoCommandRepositoryAutoConfiguration  implements InitializingBean{

    private final ConfigurableApplicationContext applicationContext;
    private final ConfigurableEnvironment environment;
    private final MongoClientOptions options;
    private final CommandProperties command;
    private final DefaultConversionService conversion;

    public MongoCommandRepositoryAutoConfiguration(
            ConfigurableApplicationContext applicationContext,
            @SuppressWarnings("SpringJavaAutowiringInspection") MongoClientOptions options,
            ObjectProvider<CommandProperties> propertiesProvider,
            ObjectProvider<DefaultConversionService> conversionProvider) {
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
        this.options = options;
        this.command = propertiesProvider.getIfAvailable();
        this.conversion = conversionProvider.getIfAvailable();
    }


    private MongoProperties commandProperties;
    private MongoDbFactory commandFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.commandProperties = commandProperties();
        this.commandFactory = commandFactory();
    }

    @Bean
    @TenantScope
    @ConfigurationProperties("spring.command.mongodb")
    public MongoProperties commandProperties(){
        return new MongoProperties();
    }

    @Bean
    @TenantScope
    public MongoClient commandClient() throws UnknownHostException {
        return commandProperties.createMongoClient(options, environment);
    }

    @Bean
    @TenantScope
    public SimpleMongoDbFactory commandFactory() throws Exception {
        return new SimpleMongoDbFactory(commandClient(), commandProperties.getMongoClientDatabase());
    }


    @Bean
    public CustomConversions commandConversions(){
        CustomConversions conversions = new CustomConversions(ConverterHolder.extraMongoConverters());
        if(conversion != null) {
            conversions.registerConvertersIn(conversion);
            ConverterHolder.initCustomConversions(conversions,conversion);
        }
        return conversions;
    }

    @Bean
    public MongoMappingContext commandMapping() throws ClassNotFoundException {
        MongoMappingContext context = new MongoMappingContext();
        context.setInitialEntitySet(new EntityScanner(this.applicationContext).scan(Document.class, Persistent.class));
        Class<?> strategyClass = this.commandProperties.getFieldNamingStrategy();
        if (strategyClass != null) {
            context.setFieldNamingStrategy((FieldNamingStrategy) BeanUtils.instantiate(strategyClass));
        }
        return context;
    }


    @Bean
    public MappingMongoConverter commandConverter() throws Exception{
        MappingMongoConverter mappingConverter = new MappingMongoConverter(new DefaultDbRefResolver(commandFactory),commandMapping());
        mappingConverter.setCustomConversions(commandConversions());
        return mappingConverter;
    }


    @Bean
    public MongoTemplate commandTemplate() throws Exception {
        return new MongoTemplate(commandFactory, commandConverter());
    }

    @Bean
    @ConditionalOnMissingBean
    public CommandRepositorySupport writingRepository() throws Exception {
        return new MongoSnapshotRepository(commandTemplate(),environment,command.getBuildSnapshotInterval());
    }


    @Bean
    @TenantScope
    public SimpleMongoDbFactory commandGridFactory() throws Exception {
        return StringUtils.hasText(commandProperties.getGridFsDatabase()) ?
                new SimpleMongoDbFactory(commandClient(), commandProperties.getGridFsDatabase()) : commandFactory();
    }

    @Bean
    public GridFsTemplate commandGridFsTemplate(MongoConverter commandConverter) throws Exception {
        return new GridFsTemplate(commandGridFactory(), commandConverter);
    }


}

