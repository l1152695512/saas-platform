package com.jikezhiji.commons.domain.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class IdAssignedEntity<ID extends Serializable> extends Entity<ID> {

	@Id
	@Column(name="ID")
	private ID id;

	@Override
	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public boolean isNew() {
		return getCreateTime() == null;
	}
}
