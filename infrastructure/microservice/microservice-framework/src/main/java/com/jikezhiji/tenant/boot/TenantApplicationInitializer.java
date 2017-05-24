package com.jikezhiji.tenant.boot;

import com.jikezhiji.core.Tenant;
import com.jikezhiji.core.boot.ApplicationInitializeEvent;
import com.jikezhiji.core.boot.ApplicationInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * Created by E355 on 2016/10/17.
 */
public interface TenantApplicationInitializer extends ApplicationInitializer<TenantApplicationInitializeEvent> {
    @Override
    default void onApplicationEvent(TenantApplicationInitializeEvent event) {
        initTenantApplication(event.getApplicationContext(),event.getPayload());
    }

    void initTenantApplication(ApplicationContext context, Tenant tenant);
}
