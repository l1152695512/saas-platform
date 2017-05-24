package com.jikezhiji.domain;

/**
 * Created by E355 on 2016/9/1.
 */
public class AggregateRootException extends RuntimeException {
    public AggregateRootException() {
        super();
    }

    public AggregateRootException(String message) {
        super(message);
    }

    public AggregateRootException(String message, Throwable cause) {
        super(message, cause);
    }

    public AggregateRootException(Throwable cause) {
        super(cause);
    }

    protected AggregateRootException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
