package com.jikezhiji.commons.domain.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public abstract class AbstractEntityListener<T> {
    protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@PrePersist
	public void prePersist(T entity) {
		
	}
	
	@PreUpdate
	public void preUpdate(T entity){
		
	}
	
	@PostUpdate
	public void postUpdate(T entity){
		
	}
	
	@PostRemove
	public void postRemove(T entity){
		
	}
}
