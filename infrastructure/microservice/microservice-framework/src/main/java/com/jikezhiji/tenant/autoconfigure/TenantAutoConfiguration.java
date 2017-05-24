package com.jikezhiji.tenant.autoconfigure;

import com.jikezhiji.core.RequestContext;
import com.jikezhiji.core.Tenant;
import com.jikezhiji.core.TenantContext;
import com.jikezhiji.tenant.*;
import com.jikezhiji.tenant.endpoint.AddTenantMvcEndpoint;
import com.jikezhiji.tenant.endpoint.RemoveTenantMvcEndpoint;
import com.jikezhiji.tenant.endpoint.TenantMvcEndpoint;
import com.jikezhiji.tenant.scope.TenantScope;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by E355 on 2016/10/11.
 */
@Configuration
@ConditionalOnProperty(name="spring.tenant.enable",havingValue = "true")
@EnableConfigurationProperties(TenantProperties.class)
public class TenantAutoConfiguration{

    private TenantProperties properties;

    public TenantAutoConfiguration(TenantProperties properties) {
        this.properties = properties;
    }

    @Bean
    public TenantScope tenantScope(){
        return new TenantScope();
    }

    @Bean
    public TenantEnvironmentProcessor tenantEnvironmentProcessor(){
        return new TenantEnvironmentProcessor();
    }

    @Bean
    public TenantInitializer tenantInitializer(TenantEnvironmentProcessor processor, TenantScope tenantScope){
        return new TenantInitializer(processor,tenantScope);
    }

    @Bean
    @ConditionalOnMissingBean
    public TenantPropertiesAccessor propertiesAccessor(ConfigurableEnvironment environment){
        return new SimpleTenantPropertiesAccessor(environment,properties);
    }


    @Bean
    public TenantContextProcessor tenantContextProcessor(){
        return new TenantContextProcessor(properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.tenant", name = "enableAddTenant", havingValue = "false", matchIfMissing = true)
    public TenantMvcEndpoint addTenantMvcEndpoint(TenantPropertiesAccessor accessor, TenantProperties properties){
        return new AddTenantMvcEndpoint(accessor,properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.tenant", name = "enableRemoveTenant")
    public TenantMvcEndpoint removeTenantMvcEndpoint(){
        return new RemoveTenantMvcEndpoint();
    }



    @Bean
    @ConditionalOnMissingBean
    public CurrentTenantResolver currentTenantResolver(){
        return ()-> {
            Collection<Tenant> tenants = TenantContext.getContextTenants();
            return tenants.stream().filter(t -> t.containsHost(RequestContext.getCurrentContext().getRequestURI().getHost())).findAny().get().getCode();
        };
    }

}
