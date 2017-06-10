package com.jikezhiji.commons.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jikezhiji.commons.domain.listener.AutoFillingEntityListener;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AutoFillingEntityListener.class)
@JsonIgnoreProperties("new")
public abstract class Entity<ID extends Serializable> implements JacksonSerializable, Persistable<ID>{


	@Column(updatable=false,name="CREATE_TIME",insertable = false)
	protected Date createTime;

	@Column(name="UPDATE_TIME",updatable=false)
	protected Date updateTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean isNew() {
		return getId() == null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Entity)) return false;

		Entity<?> entity = (Entity<?>) o;

		return getId() != null && getId().equals(entity.getId());
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}
}
