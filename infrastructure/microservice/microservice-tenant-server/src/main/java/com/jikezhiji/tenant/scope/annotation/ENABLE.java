package com.jikezhiji.tenant.scope.annotation;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.NameBinding;
import java.lang.annotation.*;

/**
 * Created by Administrator on 2016/10/25.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@HttpMethod("ENABLE")
@NameBinding
public @interface ENABLE {
}
