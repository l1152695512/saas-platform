package com.jikezhiji.tenant;

import com.jikezhiji.core.Tenant;
import com.jikezhiji.tenant.event.TenantEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by E355 on 2016/10/12.
 */
public class TenantEnvironmentProcessor implements ApplicationListener<ApplicationEnvironmentPreparedEvent>,ApplicationEventPublisherAware {

    private TenantPropertySource tenantPropertySource = new TenantPropertySource();
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        environment.getPropertySources().addFirst(tenantPropertySource);
    }

    public void preparedTenantEnvironment(Tenant tenant){
        tenantPropertySource.setCurrentTenantProperties(tenant.getValues());
        this.publisher.publishEvent(new TenantEnvironmentPreparedEvent(tenant));
    }
}
