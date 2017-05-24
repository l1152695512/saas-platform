package com.jikezhiji.tenant.scope;

/**
 * Created by E355 on 2016/10/14.
 */
public class TenantScopeBeanMetadata {
    private String beanName;
    private Class<?> beanType;
    private Object bean;

    public TenantScopeBeanMetadata(String beanName, Class<?> beanType, Object bean) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.bean = bean;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
