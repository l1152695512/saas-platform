package com.jikezhiji.domain.command;

import java.lang.annotation.*;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2016/10/26.
 */
@Retention(RUNTIME)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
@Inherited
@Documented
public @interface BusinessMethod {
    String value();
}
