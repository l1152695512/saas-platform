package com.jikezhiji.domain.command.event;

/**
 * Created by E355 on 2016/8/25.
 */
public abstract class GenericEventListener<E extends Event> implements EventListener<E> {

    @Override
    public void onEvent(E event) {
        if(getSupportedType().isAssignableFrom(event.getClass())) {
            handleEvent(event);
        }
    }


    public abstract void handleEvent(E event);
}
