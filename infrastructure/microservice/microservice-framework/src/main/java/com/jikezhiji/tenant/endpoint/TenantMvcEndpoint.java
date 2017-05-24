package com.jikezhiji.tenant.endpoint;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Created by E355 on 2016/10/14.
 */

public class TenantMvcEndpoint implements ApplicationEventPublisherAware,MvcEndpoint {


    protected ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }


    @Override
    public String getPath() {
        return "/tenants";
    }

    @Override
    public boolean isSensitive() {
        return true;
    }

    @Override
    public Class<? extends Endpoint> getEndpointType() {
        return Endpoint.class;
    }
}
