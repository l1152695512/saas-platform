package com.jikezhiji.tenant;

import com.jikezhiji.core.Tenant;
import com.jikezhiji.tenant.boot.TenantApplicationInitializeEvent;
import com.jikezhiji.tenant.event.TenantInitializedEvent;
import com.jikezhiji.tenant.scope.TenantScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by E355 on 2016/10/10.
 */
public class TenantInitializer implements ApplicationContextAware {

    private final TenantEnvironmentProcessor processor;
    private final TenantScope scope;
    private ApplicationContext context;

    public TenantInitializer(TenantEnvironmentProcessor processor, TenantScope scope) {
        this.processor = processor;
        this.scope = scope;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public void initTenant(Tenant tenant) {
        this.processor.preparedTenantEnvironment(tenant);
        this.scope.initAllOfTenantScopedBean(tenant);
        this.context.publishEvent(new TenantApplicationInitializeEvent(this.context,tenant));
        this.context.publishEvent(new TenantInitializedEvent(tenant));
    }

}
