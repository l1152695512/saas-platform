package com.jikezhiji.tenant.scope;

import com.jikezhiji.core.Tenant;
import com.jikezhiji.core.TenantContext;
import com.jikezhiji.tenant.event.TenantScopeBeanInitializedEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by E355 on 2016/10/11.
 */
public class TenantScope implements Scope, BeanFactoryPostProcessor,ApplicationEventPublisherAware {

    public static final String SCOPE_NAME = "tenant";

    private Map<String,TenantScopeBeanWrapper> tenantScopedBeans = new ConcurrentHashMap<>();

    private ConfigurableListableBeanFactory beanFactory;

    private ApplicationEventPublisher publisher;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        beanFactory.registerScope(SCOPE_NAME,this);
        this.beanFactory = beanFactory;
    }

    @Override
    public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
        TenantScopeBeanWrapper wrapper = tenantScopedBeans.get(name);
        if(wrapper != null) {
            if(wrapper.isUninitialized())  {
                wrapper.addDelegate(objectFactory.getObject());
            }
        } else {
            this.tenantScopedBeans.put(name,new TenantScopeBeanWrapper(objectFactory.getObject()));
        }
        return tenantScopedBeans.get(name).getBeanProxy();
    }

    @Override
    public Object remove(String name) {
        TenantScopeBeanWrapper beanWrapper = tenantScopedBeans.remove(name);
        try {
            beanWrapper.destroy();
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
        return beanWrapper.getBeanProxy();
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        tenantScopedBeans.get(name).registerDestroyTask(callback);
    }

    @Override
    public Object resolveContextualObject(String key) {
        return TenantContext.currentTenantValue(key);
    }

    @Override
    public String getConversationId() {
        return TenantContext.currentTenantCode();
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void initAllOfTenantScopedBean(Tenant tenant){
        this.publisher.publishEvent(new TenantScopeBeanInitializedEvent(tenant,tenantScopedBeans.keySet()));
    }

}
