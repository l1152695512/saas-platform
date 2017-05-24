package com.jikezhiji.domain.query;

/**
 * Created by E355 on 2016/9/22.
 */
public class ProjectionMappingException extends RuntimeException {
    public ProjectionMappingException(String message){
        super(message);
    }
    public ProjectionMappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
