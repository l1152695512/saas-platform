package com.jikezhiji.rest.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by E355 on 2016/9/13.
 */
@ConfigurationProperties("spring.rest")
public class RestProperties {

    /**
     * enable jwt
     */
    private boolean enableJwt;

    /**
     * jwt secret
     */
    private String jwtSecret;

    /**
     * jwt auth scheme
     */
    private String jwtAuthScheme;

    /**
     * scan packages
     */
    private String[] scanPackages;

    /**
     * rest built-in properteis
     */
    private Map<String,Object> restBuiltInProperties;


    public boolean isEnableJwt() {
        return enableJwt;
    }

    public void setEnableJwt(boolean enableJwt) {
        this.enableJwt = enableJwt;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getJwtAuthScheme() {
        return jwtAuthScheme;
    }

    public void setJwtAuthScheme(String jwtAuthScheme) {
        this.jwtAuthScheme = jwtAuthScheme;
    }

    public String[] getScanPackages() {
        return scanPackages;
    }

    public void setScanPackages(String[] scanPackages) {
        this.scanPackages = scanPackages;
    }

    public Map<String, Object> getRestBuiltInProperties() {
        return restBuiltInProperties;
    }

    public void setRestBuiltInProperties(Map<String, Object> restBuiltInProperties) {
        this.restBuiltInProperties = restBuiltInProperties;
    }
}
