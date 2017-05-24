package com.jikezhiji.tenant.endpoint;

import com.jikezhiji.tenant.TenantPropertiesAccessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by liusizuo on 2017/5/5.
 */
public class GetRequiredPropertiesTenantMvcEndpoint extends TenantMvcEndpoint{

    private TenantPropertiesAccessor accessor;

    public GetRequiredPropertiesTenantMvcEndpoint(TenantPropertiesAccessor accessor) {
        this.accessor = accessor;
    }

    @GetMapping("/required-properties")
    @ResponseBody
    public Map<String,Object> requiredProperties() {
        return accessor.requiredProperties();
    }
}
