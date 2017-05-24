package com.jikezhiji.domain.command;

import com.jikezhiji.domain.AggregateRoot;
import org.springframework.data.annotation.AccessType;

import java.io.Serializable;

/**
 * Created by E355 on 2016/8/30.
 */
@AccessType(AccessType.Type.FIELD)
@EnableEventSourcing
public class EventSourcingAggregateRoot<ID extends Serializable> extends AggregateRoot<ID> {
    private boolean deleted;
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

}
