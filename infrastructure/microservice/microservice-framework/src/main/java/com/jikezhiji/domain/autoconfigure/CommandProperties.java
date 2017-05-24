package com.jikezhiji.domain.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.spi.ValidationProvider;

/**
 * Created by E355 on 2016/8/31.
 */
@ConfigurationProperties("spring.ddd")
public class CommandProperties {

    /**
     * build snapshot interval
     */
    private int buildSnapshotInterval = 3;

    /**
     * annotation bus thread pool size
     */
    private int commandPoolSize = 10;


    /**
     * validation provider class
     */
    private Class<? extends ValidationProvider> validationProviderClass;

    public int getBuildSnapshotInterval() {
        return buildSnapshotInterval;
    }

    public void setBuildSnapshotInterval(int buildSnapshotInterval) {
        this.buildSnapshotInterval = buildSnapshotInterval;
    }

    public int getCommandPoolSize() {
        return commandPoolSize;
    }

    public void setCommandPoolSize(int commandPoolSize) {
        this.commandPoolSize = commandPoolSize;
    }

    public Class<? extends ValidationProvider> getValidationProviderClass() {
        return validationProviderClass;
    }

    public void setValidationProviderClass(Class<? extends ValidationProvider> validationProviderClass) {
        this.validationProviderClass = validationProviderClass;
    }
}
