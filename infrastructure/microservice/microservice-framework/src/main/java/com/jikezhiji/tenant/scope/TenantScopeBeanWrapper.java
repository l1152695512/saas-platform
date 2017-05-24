package com.jikezhiji.tenant.scope;

import com.jikezhiji.core.TenantContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by E355 on 2016/10/17.
 */
class TenantScopeBeanWrapper implements DisposableBean {
    private Map<String, Object> delegates = new HashMap<>();
    private Map<String, Runnable> destroyTasks = new HashMap<>();

    private String DEFAULT_TENANT_NAME = "default";

    private final Object beanProxy;

    public Object getBeanProxy() {
        return beanProxy;
    }

    public void addDelegate(Object bean) {
        String currentTenant = TenantContext.currentTenantCode();
        if (StringUtils.isEmpty(currentTenant)) {
            currentTenant = DEFAULT_TENANT_NAME;
        }
        delegates.put(currentTenant, bean);
    }

    public boolean isUninitialized() {
        return delegates.containsKey(TenantContext.currentTenantCode());
    }


    public void registerDestroyTask(Runnable callback) {
        String currentTenant = TenantContext.currentTenantCode();
        if (StringUtils.isEmpty(currentTenant)) {
            currentTenant = DEFAULT_TENANT_NAME;
        }
        destroyTasks.put(currentTenant, callback);
    }

    public TenantScopeBeanWrapper(Object bean) {
        this.addDelegate(bean);
        this.beanProxy = createBeanProxy(bean.getClass());
    }


    @Override
    public synchronized void destroy() throws Exception {
        delegates.clear();
        destroyTasks.clear();
        destroyTasks.forEach((key, value) -> value.run());
    }

    private Object createBeanProxy(Class<?> targetClass) {
        ProxyFactory factory = new ProxyFactory();
        factory.setTargetClass(targetClass);
        factory.setProxyTargetClass(true);
        factory.setOpaque(true);
        factory.addAdvice((MethodInterceptor) in -> {
            String currentTenant = TenantContext.currentTenantCode();
            if (StringUtils.isEmpty(currentTenant)) {
                currentTenant = DEFAULT_TENANT_NAME;
            }
            return in.getMethod().invoke(delegates.get(currentTenant), in.getArguments());
        });
        return factory.getProxy();
    }
}
