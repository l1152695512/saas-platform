package com.jikezhiji.tenant.endpoint;

import com.jikezhiji.core.TenantContext;
import com.jikezhiji.tenant.event.TenantRemovedEvent;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liusizuo on 2017/5/5.
 */
public class RemoveTenantMvcEndpoint extends TenantMvcEndpoint{


    @DeleteMapping("{code}")
    @ResponseBody
    public void value(@PathVariable("code")String code) {
        super.publisher.publishEvent(new TenantRemovedEvent(TenantContext.getTenant(code)));
    }

}
