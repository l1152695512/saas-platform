package com.jikezhiji.tenant;


import com.jikezhiji.tenant.autoconfigure.TenantProperties;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liusizuo on 2017/5/5.
 */
public class SimpleTenantPropertiesAccessor implements TenantPropertiesAccessor{
    private ConfigurableEnvironment environment;
    private TenantProperties properties;

    public SimpleTenantPropertiesAccessor(ConfigurableEnvironment environment, TenantProperties properties) {
        this.environment = environment;
        this.properties = properties;
    }

    @Override
    public Map<String, Object> requiredProperties() {
        Map<String, Object> requiredProperties = new HashMap<>();
        environment.getPropertySources().forEach((PropertySource<?> propertySource) -> {
            if(propertySource instanceof EnumerablePropertySource<?>) {
                EnumerablePropertySource<?> eps = (EnumerablePropertySource<?>) propertySource;
                for (String name: eps.getPropertyNames()){
                    String source = eps.getProperty(name).toString().trim();
                    if(source.startsWith("${tenant.")) {
                        String[] kv = source.substring(3,source.length() - 1).split(":");
                        requiredProperties.put(kv[0],kv[1]);
                    }
                }
            }
        });

        return requiredProperties;
    }
}
