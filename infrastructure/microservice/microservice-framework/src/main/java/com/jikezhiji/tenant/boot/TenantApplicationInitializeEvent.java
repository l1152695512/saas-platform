package com.jikezhiji.tenant.boot;

import com.jikezhiji.core.Tenant;
import com.jikezhiji.core.boot.ApplicationInitializeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.util.Assert;

/**
 * Created by E355 on 2016/10/17.
 */
public class TenantApplicationInitializeEvent extends ApplicationInitializeEvent<Tenant> {
    public TenantApplicationInitializeEvent(ApplicationContext source, Tenant tenant) {
        super(source, tenant);
        Assert.notNull(source);
        Assert.notNull(tenant);
    }
}
