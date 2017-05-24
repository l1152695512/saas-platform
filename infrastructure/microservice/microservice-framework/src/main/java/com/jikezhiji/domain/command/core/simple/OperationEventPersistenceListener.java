package com.jikezhiji.domain.command.core.simple;

import com.jikezhiji.domain.command.core.simple.OperationEvent;
import com.jikezhiji.domain.command.repository.CommandRepositorySupport;
import com.jikezhiji.domain.command.event.GenericEventListener;

/**
 * Created by E355 on 2016/8/24.
 */
public class OperationEventPersistenceListener extends GenericEventListener<OperationEvent> {

    private CommandRepositorySupport commandSupport;

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void handleEvent(OperationEvent event) {
        commandSupport.persistEvent(event);
    }

    public OperationEventPersistenceListener(CommandRepositorySupport repository) {
        this.commandSupport = repository;
    }
}

