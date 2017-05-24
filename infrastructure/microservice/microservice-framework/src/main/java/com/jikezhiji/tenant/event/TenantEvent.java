package com.jikezhiji.tenant.event;

import com.jikezhiji.core.Tenant;
import org.springframework.context.ApplicationEvent;

/**
 * Created by E355 on 2016/10/12.
 */
public class TenantEvent extends ApplicationEvent {
    public TenantEvent(Tenant source) {
        super(source);
    }

    public Tenant getTenant(){
        return (Tenant)getSource();
    }
}
