package com.jikezhiji.domain.query;

import org.springframework.data.domain.Persistable;

import java.lang.annotation.*;

/**
 * 查询投影，我们可以把它看做为数据库的一个视图
 * types 和 mappings 必须有一个不能为空
 * Created by E355 on 2016/9/22.
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
public @interface QueryProjection {

    Class<? extends Persistable> type() default Persistable.class;

    String mapping() default "";

    String id() default "";
}
