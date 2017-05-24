package com.jikezhiji.tenant;

import com.jikezhiji.core.TenantContext;
import com.jikezhiji.tenant.event.TenantAddEvent;
import org.springframework.context.ApplicationListener;

/**
 * Created by E355 on 2016/10/10.
 */
public class TenantAddEventListener implements ApplicationListener<TenantAddEvent> {

    private final TenantInitializer initializer;

    public TenantAddEventListener(TenantInitializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public void onApplicationEvent(TenantAddEvent event) {
        TenantContext.addTenant(event.getTenant());
        initializer.initTenant(event.getTenant());
    }

}
