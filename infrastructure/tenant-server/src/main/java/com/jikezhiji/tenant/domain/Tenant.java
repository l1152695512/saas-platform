package com.jikezhiji.tenant.domain;

import com.jikezhiji.tenant.enumeration.EntityStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tenant extends IdAssigned {


    private String name;
    
    private String description;

    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityStatus status) {
        this.status = status;
    }

}