package com.jikezhiji.core;

import org.springframework.core.GenericTypeResolver;

/**
 * Created by E355 on 2016/9/13.
 */
public interface GenericType<T> {
    default Class<?> getSupportedType(){
        return GenericTypeResolver.resolveTypeArgument(getClass(),GenericType.class);
    }
}
