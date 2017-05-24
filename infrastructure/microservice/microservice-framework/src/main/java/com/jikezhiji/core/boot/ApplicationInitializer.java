package com.jikezhiji.core.boot;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * Created by E355 on 2016/10/17.
 */
public interface ApplicationInitializer<E extends ApplicationInitializeEvent<?>> extends ApplicationListener<E>,Ordered{
    int getVersion();

    default int getOrder(){
        return getVersion();
    }

    @Override
    void onApplicationEvent(E event);
}
