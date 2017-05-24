package com.jikezhiji.domain.command.event;

import com.jikezhiji.core.GenericType;

/**
 * Created by E355 on 2016/8/24.
 */
public interface EventListener<E extends Event> extends GenericType<E>, java.util.EventListener {
    int getOrder();

    void onEvent(E event);
}
