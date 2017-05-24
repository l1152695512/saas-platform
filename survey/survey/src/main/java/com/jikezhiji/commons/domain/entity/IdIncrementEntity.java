package com.jikezhiji.commons.domain.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class IdIncrementEntity extends Entity<Long> {

	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}