package com.jikezhiji.tenant;

import com.jikezhiji.core.Tenant;
import com.jikezhiji.core.TenantContext;
import com.jikezhiji.tenant.autoconfigure.TenantProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

/**
 * Created by E355 on 2016/10/17.
 */
public final class TenantContextProcessor implements ApplicationListener<ContextStartedEvent> {

    private Log logger = LogFactory.getLog(TenantContextProcessor.class);
    private TenantProperties properties;

    @Autowired
    private CurrentTenantResolver resolver;
    @Autowired
    private TenantInitializer initializer;

    public TenantContextProcessor(TenantProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        TenantContext.initTenantContext(resolver);
        RestTemplate template = getSecureRestTemplate();
        String path = "/{serviceId}";
        ResponseEntity<TenantInformation[]> entity = template.getForEntity(properties.getClient().getUri() + path,
                TenantInformation[].class,event.getApplicationContext().getId());
        if(entity.getStatusCode() == HttpStatus.OK) {
            TenantInformation[] parameters = entity.getBody();
            for(TenantInformation info : parameters) {
                Tenant tenant = info.build(properties.getTenantPropertiesPrefix(),properties.getTenantPropertiesJoiner());
                TenantContext.addTenant(tenant);
                initializer.initTenant(tenant);
            }
        }
    }

    private RestTemplate getSecureRestTemplate(){
        String username = properties.getClient().getUsername();
        String password = properties.getClient().getPassword();
        RestTemplate template = new RestTemplate();
        if(StringUtils.hasText(username) && StringUtils.hasText(password)) {
            template.setInterceptors(Collections.singletonList((request, body, execution) -> {
                byte[] token = Base64.getEncoder().encode((username + ":" + password).getBytes());
                request.getHeaders().add("Authorization", "Basic " + new String(token));
                return execution.execute(request, body);
            }));
        }
        return template;
    }
}
