package com.jikezhiji.domain.query;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by E355 on 2016/9/21.
 */
public abstract class StaticReadingEntity<ID extends Serializable> implements ReadingEntity<ID>,Auditable {

    private Date createdTime;
    private String createdBy;
    private Date lastModifiedTime;
    private String lastModifiedBy;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
