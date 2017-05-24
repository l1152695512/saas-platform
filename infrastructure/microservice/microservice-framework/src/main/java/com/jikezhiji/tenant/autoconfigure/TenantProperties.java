package com.jikezhiji.tenant.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by E355 on 2016/10/14.
 */
@ConfigurationProperties(TenantProperties.PREFIX)
public class TenantProperties {
    public static final String PREFIX = "spring.tenant";

    private boolean enable;

    private boolean master;

    private boolean enableAddTenant = true;
    private boolean enableRemoveTenant;

    @NotNull
    private String tenantPropertiesPrefix = "tenant";

    @NotNull
    private String tenantPropertiesJoiner;

    @NestedConfigurationProperty
    private Client client;


    public static class Client {
        private String serviceId;
        private String username;
        private String password;
        private String uri = "http://localhost:9988";

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnableAddTenant() {
        return enableAddTenant;
    }

    public void setEnableAddTenant(boolean enableAddTenant) {
        this.enableAddTenant = enableAddTenant;
    }

    public boolean isEnableRemoveTenant() {
        return enableRemoveTenant;
    }

    public void setEnableRemoveTenant(boolean enableRemoveTenant) {
        this.enableRemoveTenant = enableRemoveTenant;
    }

    public String getTenantPropertiesPrefix() {
        return tenantPropertiesPrefix;
    }

    public void setTenantPropertiesPrefix(String tenantPropertiesPrefix) {
        this.tenantPropertiesPrefix = tenantPropertiesPrefix;
    }

    public String getTenantPropertiesJoiner() {
        return tenantPropertiesJoiner;
    }

    public void setTenantPropertiesJoiner(String tenantPropertiesJoiner) {
        this.tenantPropertiesJoiner = tenantPropertiesJoiner;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
