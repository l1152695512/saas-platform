package com.jikezhiji.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by E355 on 2016/9/2.
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Inherited
public @interface IdIncrement {
    String sequence() default "${spring.entity.sequence:EntitySequence}";
}
