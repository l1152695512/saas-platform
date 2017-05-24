package com.jikezhiji.tenant.scope.annotation;

import org.springframework.context.annotation.Scope;

import java.lang.annotation.*;

/**
 * Created by E355 on 2016/9/30.
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Scope(com.jikezhiji.tenant.scope.TenantScope.SCOPE_NAME)
@Documented
public @interface TenantScope {}
