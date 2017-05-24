package com.jikezhiji.tenant.endpoint;

import com.jikezhiji.core.structure.ActionResult;
import com.jikezhiji.tenant.TenantInformation;
import com.jikezhiji.tenant.TenantPropertiesAccessor;
import com.jikezhiji.tenant.autoconfigure.TenantProperties;
import com.jikezhiji.tenant.event.TenantAddEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.mvc.HypermediaDisabled;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by liusizuo on 2017/5/5.
 */
public class AddTenantMvcEndpoint extends TenantMvcEndpoint{

    private TenantPropertiesAccessor accessor;
    protected TenantProperties properties;

    public AddTenantMvcEndpoint(TenantPropertiesAccessor accessor,TenantProperties properties) {
        this.accessor = accessor;
        this.properties = properties;
    }

    @PostMapping(value = "{code}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @HypermediaDisabled
    public ActionResult<Map<String,Object>> value(@PathVariable("code")String code, TenantInformation parameter) {
        if(parameter.isEffective(accessor.requiredProperties().keySet())){
            String prefix = properties.getTenantPropertiesPrefix();
            String joiner = properties.getTenantPropertiesJoiner();
            super.publisher.publishEvent(new TenantAddEvent(parameter.build(prefix, joiner)));
            return ActionResult.success("租户增加成功",parameter.properties());
        } else {
            return ActionResult.fail("租户增加失败，请参考必填配置项的默认值",accessor.requiredProperties());
        }
    }
}
