package com.jikezhiji.tenant.resource.value.out;

import com.jikezhiji.tenant.aggregate.Tenant;
import com.jikezhiji.tenant.aggregate.TenantService;

/**
 * Created by Administrator on 2016/10/25.
 */
public class TenantInfo {
    private Tenant tenant;
    private TenantService tenantService;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public TenantService getTenantService() {
        return tenantService;
    }

    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    public TenantInfo(Tenant tenant, TenantService tenantService) {
        this.tenant = tenant;
        this.tenantService = tenantService;
    }
}
