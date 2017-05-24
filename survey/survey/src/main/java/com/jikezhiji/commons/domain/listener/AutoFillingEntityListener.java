package com.jikezhiji.commons.domain.listener;

import com.jikezhiji.commons.domain.entity.Entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;



/**
 * 自动填充字段值.
 * 插入、修改自动补全字段
 */
public class AutoFillingEntityListener extends AbstractEntityListener<Entity<?>> {

	public void prePersist(Entity<?> entity) {
		if(entity.getCreateTime() == null) {
			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
		}
	}
	
	public void preUpdate(Entity<?> entity){
		if(entity.getUpdateTime() == null) {
			entity.setUpdateTime(new Date());
		}
	}
}