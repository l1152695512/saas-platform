package com.jikezhiji.core;

import com.jikezhiji.tenant.CurrentTenantResolver;

import java.util.*;

/**
 * Created by E355 on 2016/9/29.
 */
public class TenantContext {

    private static Map<String,Tenant> tenants = new HashMap<>();

    private static CurrentTenantResolver resolver ;

    public static void initTenantContext(CurrentTenantResolver resolver){
        TenantContext.resolver = resolver;
    }
    public static Tenant currentTenant(){
        return tenants.get(resolver.resolveCurrentTenantCode());
    }

    public static String currentTenantCode(){
        return resolver.resolveCurrentTenantCode();
    }

    public static Object currentTenantValue(String key){
        return currentTenant().getValue(key);
    }

    public static void addTenant(Tenant tenant) {
        tenants.put(tenant.getCode(),tenant);
    }

    public static Collection<Tenant> getContextTenants(){
        return tenants.values();
    }
    public static Tenant getTenant(String code){
        return tenants.get(code);
    }
}
