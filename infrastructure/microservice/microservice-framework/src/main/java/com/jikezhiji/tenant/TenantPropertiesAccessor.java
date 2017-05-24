package com.jikezhiji.tenant;

import java.util.Map;
import java.util.Set;

/**
 * 当前服务的租户配置项
 * Created by E355 on 2016/10/12.
 */
public interface TenantPropertiesAccessor {

    /**
     * 必须要提供的配置项
     * @return
     */
    Map<String,Object> requiredProperties();


}
