package com.jikezhiji.tenant.event;

import com.jikezhiji.core.Tenant;

/**
 * Created by E355 on 2016/10/9.
 */
public class TenantInitializedEvent extends TenantEvent {
    public TenantInitializedEvent(Tenant tenant) {
        super(tenant);
    }
}
