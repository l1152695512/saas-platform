package com.jikezhiji.tenant.resource.value.out;

import com.jikezhiji.tenant.aggregate.Service;
import com.jikezhiji.tenant.aggregate.Tenant;
import com.jikezhiji.tenant.aggregate.TenantService;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ServiceInfo {
    private Service service;
    private TenantService tenantService;

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public TenantService getTenantService() {
        return tenantService;
    }

    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    public ServiceInfo(Service service, TenantService tenantService) {
        this.service = service;
        this.tenantService = tenantService;
    }
}
