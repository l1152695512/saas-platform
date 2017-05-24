package com.jikezhiji.tenant;

import com.jikezhiji.core.structure.Property;
import com.jikezhiji.core.Tenant;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by E355 on 2016/10/14.
 */
public class TenantInformation {
    private String code;
    private String name;
    private Set<String> hosts;
    private Set<Property<?>> tenantProperties;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getHosts() {
        return hosts;
    }

    public void setHosts(Set<String> hosts) {
        this.hosts = hosts;
    }

    public Set<Property<?>> getTenantProperties() {
        return tenantProperties;
    }

    public void setTenantProperties(Set<Property<?>> tenantProperties) {
        this.tenantProperties = tenantProperties;
    }


    private boolean containsAllProperty(Set<String> requiredKeys){
        Set<String> keySet = tenantProperties.stream().map(Property::getKey).collect(Collectors.toSet());
        return keySet.containsAll(requiredKeys);
    }

    private boolean hostsNotEmpty(){
        return hosts!=null && !hosts.isEmpty();
    }

    public boolean isEffective(Set<String> requiredKeys) {
        return hostsNotEmpty() &&
                StringUtils.hasText(code) &&
                StringUtils.hasText(name) &&
                containsAllProperty(requiredKeys);
    }

    public Tenant build(String prefix,String separator) {
        Map<String,Object> values = new HashMap<>();
        String keyPrefix = prefix + separator;
        tenantProperties.forEach(property -> values.put(keyPrefix + property.getKey(), property.getValue()));
        values.put(keyPrefix+"code",code);
        values.put(keyPrefix+"name",name);
        values.put(keyPrefix+"hosts",hosts);
        return new Tenant(keyPrefix,values);
    }


    public Map<String,Object> properties(){
        Map<String,Object> properties = new HashMap<>();
        tenantProperties.forEach(property -> properties.put(property.getKey(),property.getKey()));
        return properties;
    }
}
