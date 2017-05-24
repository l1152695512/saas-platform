package com.jikezhiji.tenant.event;

import com.jikezhiji.core.Tenant;

/**
 * Created by E355 on 2016/10/9.
 */
public class TenantAddEvent extends TenantEvent {
    public TenantAddEvent(Tenant tenant) {
        super(tenant);
    }
}
