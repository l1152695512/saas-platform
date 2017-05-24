package com.jikezhiji.tenant;

import com.jikezhiji.core.TenantContext;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by E355 on 2016/10/11.
 */
public class TenantPropertySource extends EnumerablePropertySource<Map<String, Object>> {
    public static final String TENANT_PROPERTIES_NAME = "tenantProperties";
    private Map<String,Map<String,Object>> tenantProperties = new HashMap<>();

    public TenantPropertySource() {
        super(TENANT_PROPERTIES_NAME);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean containsProperty(String name) {
        String current = TenantContext.currentTenantCode();
        return tenantProperties.get(current).containsKey(name);
    }

    @Override
    public String[] getPropertyNames() {
        return StringUtils.toStringArray(getSource().keySet());
    }

    @Override
    public Map<String, Object> getSource() {
        return tenantProperties.get(TenantContext.currentTenantCode());
    }

    @Override
    public Object getProperty(String name) {
        return getSource().get(name);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public int hashCode() {
        return tenantProperties.hashCode();
    }

    @Override
    public String toString() {
        return getName() + tenantProperties.keySet();
    }

    public void setCurrentTenantProperties(Map<String,Object> properties){
        this.tenantProperties.put(TenantContext.currentTenantCode(),properties);
    }
}
