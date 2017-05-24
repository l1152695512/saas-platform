package com.jikezhiji.core;

import com.jikezhiji.core.boot.ApplicationInitializeEvent;
import com.jikezhiji.domain.autoconfigure.CommandAutoConfiguration;
import com.jikezhiji.rest.autoconfigure.RestJerseyAutoConfiguration;
import com.jikezhiji.tenant.autoconfigure.TenantAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

/**
 * Created by E355 on 2016/9/18.
 */
@Configuration
public class CommonConfiguration {

    private ApplicationEventPublisher publisher;

    public CommonConfiguration(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener(classes= ContextStartedEvent.class)
    public void initApplication(ContextStartedEvent event){
        this.publisher.publishEvent(new ApplicationInitializeEvent<>(event.getApplicationContext()));
    }


}
