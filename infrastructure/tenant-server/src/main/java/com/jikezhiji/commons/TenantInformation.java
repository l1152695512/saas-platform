package com.jikezhiji.commons;

import java.util.Map;
import java.util.Set;

/**
 * Created by liusizuo on 2017/5/12.
 */
public class TenantInformation {
    private String code;
    private String name;
    private Set<String> hosts;
    private Map<String,Object> tenantProperties;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getHosts() {
        return hosts;
    }

    public void setHosts(Set<String> hosts) {
        this.hosts = hosts;
    }

    public Map<String,Object> getTenantProperties() {
        return tenantProperties;
    }

    public void setTenantProperties(Map<String,Object> tenantProperties) {
        this.tenantProperties = tenantProperties;
    }
}
