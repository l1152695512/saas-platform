package com.jikezhiji.domain.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 启用事件溯源。
 * Created by E355 on 2016/8/25.
 */
@Retention(RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface EnableEventSourcing {
    String value() default "";
}

