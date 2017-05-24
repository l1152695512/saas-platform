package com.jikezhiji.domain;

/**
 * Created by E355 on 2016/9/13.
 */
public class AggregateRootVersionException extends AggregateRootPersistException{
    public AggregateRootVersionException(Object entity, String message) {
        super(entity, message);
    }

    public AggregateRootVersionException(Object entity, String message, Exception cause) {
        super(entity, message, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
