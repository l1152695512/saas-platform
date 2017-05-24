package com.jikezhiji.domain.query;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * 标记接口，标识为一个读模型实体
 * Created by E355 on 2016/9/21.
 */
public interface ReadingEntity<ID extends Serializable> extends Persistable<ID> {
}
