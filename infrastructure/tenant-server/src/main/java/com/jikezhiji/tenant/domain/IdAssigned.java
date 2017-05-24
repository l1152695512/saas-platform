package com.jikezhiji.tenant.domain;

import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.util.ClassUtils;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by liusizuo on 2017/5/11.
 */
@MappedSuperclass
@EntityListeners(IdAssigned.CreateTimeGeneratorEntityListener.class)
public abstract class IdAssigned implements Persistable<String>,Serializable {

    public static class CreateTimeGeneratorEntityListener {
        @PrePersist
        public void prePersist(IdAssigned entity) {
            if(entity.getCreateTime() == null) {
                entity.setCreateTime(new Date());
            }
        }
    }

    @Id
    private String id;

    private Date createTime;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean isNew() {
        return this.createTime == null;
    }


    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(ClassUtils.getUserClass(obj))) {
            return false;
        }

        AbstractPersistable<?> that = (AbstractPersistable<?>) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new FatalBeanException("克隆失败 -> "+this.getId(), e);
        }
    }
}
