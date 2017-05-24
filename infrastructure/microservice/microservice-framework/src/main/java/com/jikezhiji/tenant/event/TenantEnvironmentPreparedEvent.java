package com.jikezhiji.tenant.event;

import com.jikezhiji.core.Tenant;

import java.util.Map;

/**
 * Created by E355 on 2016/10/10.
 */
public class TenantEnvironmentPreparedEvent extends TenantEvent {
    public TenantEnvironmentPreparedEvent(Tenant tenant) {
        super(tenant);
    }

    public Map<String,Object> getTenantProperties(){
        return getTenant().getValues();
    }
}
