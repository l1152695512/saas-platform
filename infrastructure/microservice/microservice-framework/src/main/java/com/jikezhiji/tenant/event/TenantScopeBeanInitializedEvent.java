package com.jikezhiji.tenant.event;

import com.jikezhiji.core.Tenant;

import java.util.Set;

/**
 * Created by E355 on 2016/10/9.
 */
public class TenantScopeBeanInitializedEvent extends TenantEvent {
    private Set<String> tenantScopeBeanNameSet;
    public TenantScopeBeanInitializedEvent(Tenant tenant, Set<String> tenantScopeBeanNameSet){
        super(tenant);
        this.tenantScopeBeanNameSet = tenantScopeBeanNameSet;
    }
    public Set<String> getTenantScopeBeanNameSet(){
        return tenantScopeBeanNameSet;
    }

}
