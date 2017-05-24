package com.jikezhiji.core.boot;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Created by E355 on 2016/10/17.
 */
public class ApplicationInitializeEvent<Payload> extends ApplicationContextEvent{
    private Payload payload;
    public ApplicationInitializeEvent(ApplicationContext source) {
        super(source);
    }

    public ApplicationInitializeEvent(ApplicationContext source,Payload payload) {
        super(source);
        this.payload = payload;
    }

    public Payload getPayload(){
        return payload;
    }
}
