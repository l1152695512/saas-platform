package com.jikezhiji.tenant.domain;

import com.jikezhiji.tenant.enumeration.EntityStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by E355 on 2016/9/19.
 */
@Entity
@Table(name="`Application`")
public class Application extends IdAssigned {


    @Column(name="`name`")
    private String name;

    @Column(name="`group`")
    private String group;

    @Column(name="`description`")
    private String description;

    @Column(name="`version`")
    private String version;

    @Enumerated(EnumType.STRING)
    @Column(name="`status`")
    private EntityStatus status;


    @Transient
    private List<String> dependencies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }
}
