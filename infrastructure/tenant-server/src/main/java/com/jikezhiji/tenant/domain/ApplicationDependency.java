package com.jikezhiji.tenant.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by liusizuo on 2017/5/6.
 */
@Entity
@IdClass(ApplicationDependency.ApplicationDependencyPK.class)
public class ApplicationDependency {
    @Id
    private String applicationId;
    @Id
    private String dependencyApplicationId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDependencyApplicationId() {
        return dependencyApplicationId;
    }

    public void setDependencyApplicationId(String dependencyApplicationId) {
        this.dependencyApplicationId = dependencyApplicationId;
    }

    public static class ApplicationDependencyPK implements Serializable {
        private String applicationId;
        private String dependencyApplicationId;

        public String getApplicationId() {
            return applicationId;
        }

        public void setApplicationId(String applicationId) {
            this.applicationId = applicationId;
        }

        public String getDependencyApplicationId() {
            return dependencyApplicationId;
        }

        public void setDependencyApplicationId(String dependencyApplicationId) {
            this.dependencyApplicationId = dependencyApplicationId;
        }
    }

    public ApplicationDependency() {
    }

    public ApplicationDependency(String applicationId, String dependencyApplicationId) {
        this();
        this.applicationId = applicationId;
        this.dependencyApplicationId = dependencyApplicationId;
    }
}
