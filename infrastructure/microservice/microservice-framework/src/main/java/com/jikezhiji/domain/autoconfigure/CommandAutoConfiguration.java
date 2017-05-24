package com.jikezhiji.domain.autoconfigure;

import com.jikezhiji.domain.AggregateRootValidator;
import com.jikezhiji.domain.command.repository.CommandRepositorySupport;
import com.jikezhiji.domain.command.converters.EventSourcingAggregateRootToMapConverter;
import com.jikezhiji.domain.command.event.EventListener;
import com.jikezhiji.domain.command.core.simple.OperationCommand;
import com.jikezhiji.domain.command.core.simple.OperationEventPersistenceListener;
import com.jikezhiji.domain.command.core.simple.OperationEventSynchronizationListener;
import com.jikezhiji.domain.query.QueryRepositorySupport;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.validation.Validator;

import javax.validation.spi.ValidationProvider;
import java.util.Set;

/**
 * Created by E355 on 2016/8/30.
 */
@Configuration
@EnableConfigurationProperties(CommandProperties.class)
@ImportAutoConfiguration({MongoCommandRepositoryAutoConfiguration.class, MongoQueryRepositoryAutoConfiguration.class})
public class CommandAutoConfiguration {


    private Class<? extends ValidationProvider> validationClass;

    public CommandAutoConfiguration(CommandProperties properties){
        this.validationClass = properties.getValidationProviderClass();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultConversionService defaultConversionService(){
        return new DefaultConversionService();
    }

    @Bean
    public DefaultConversionService conversionService(DefaultConversionService conversionService){
        conversionService.addConverter(new EventSourcingAggregateRootToMapConverter());
        return conversionService;
    }


    @Bean
    public EventListener persistChangeSetEventListener(CommandRepositorySupport repository){
        return new OperationEventPersistenceListener(repository);
    }

    @Bean
    public EventListener synchronizeChangeSetEventListener(QueryRepositorySupport repository, DefaultConversionService conversionService){
        return new OperationEventSynchronizationListener(repository,conversionService);
    }

    @Bean
    public Validator aggregateValidator(){
        return new AggregateRootValidator(validationClass);
    }


    @Bean
    public OperationCommand changeSetCommand(Set<EventListener> providers, CommandRepositorySupport repository, Validator aggregateValidator){
        return new OperationCommand(providers,repository,aggregateValidator);
    }

}
