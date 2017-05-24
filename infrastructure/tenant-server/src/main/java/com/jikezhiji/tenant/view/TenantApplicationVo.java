package com.jikezhiji.tenant.view;

import com.jikezhiji.tenant.domain.Application;
import com.jikezhiji.tenant.domain.Tenant;
import com.jikezhiji.tenant.domain.TenantApplication;

/**
 * Created by liusizuo on 2017/5/8.
 */
public class TenantApplicationVo extends TenantApplication{
    private Tenant tenant;
    private Application application;

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public TenantApplicationVo() {
    }

    public TenantApplicationVo(Tenant t, TenantApplication ta) {
        tenant = new Tenant();
        tenant.setId(t.getId());
        tenant.setName(t.getName());
        tenant.setDescription(t.getDescription());
        tenant.setCreateTime(t.getCreateTime());
        tenant.setStatus(t.getStatus());
        this.copyProperty(ta);
    }
    public TenantApplicationVo(Application a, TenantApplication ta) {
        this.application = new Application();
        application.setId(a.getId());
        application.setName(a.getName());
        application.setGroup(a.getGroup());
        application.setDescription(a.getDescription());
        application.setVersion(a.getVersion());
        application.setStatus(a.getStatus());
        application.setCreateTime(a.getCreateTime());
        this.copyProperty(ta);
    }
    public void copyProperty(TenantApplication ta) {
        this.setTenantId(ta.getTenantId());
        this.setApplicationId(ta.getApplicationId());
        this.setDescription(ta.getDescription());
        this.setProperties(ta.getProperties());
        this.setEndTime(ta.getEndTime());
        this.setStartTime(ta.getStartTime());
        this.setStatus(ta.getStatus());
    }
}
