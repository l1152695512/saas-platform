package com.jikezhiji.domain.command.repository;

import com.jikezhiji.domain.command.event.Event;

/**
 * Created by E355 on 2016/8/26.
 */
public interface EventRepository {

    void persistEvent(Event event);

}
