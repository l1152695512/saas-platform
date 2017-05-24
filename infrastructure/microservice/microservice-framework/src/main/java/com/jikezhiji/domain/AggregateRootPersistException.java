package com.jikezhiji.domain;

import com.jikezhiji.domain.AggregateRootException;

import java.util.Objects;

/**
 * Created by E355 on 2016/8/30.
 */
public class AggregateRootPersistException extends AggregateRootException {
    private Object entity;

    public AggregateRootPersistException(Object entity, String message) {
        super(message);
        this.entity = entity;
    }

    public AggregateRootPersistException(Object entity, String message,Exception cause) {
        super(message,cause);
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return "['" + Objects.toString(entity)+ "']" + super.getMessage();
    }
}
