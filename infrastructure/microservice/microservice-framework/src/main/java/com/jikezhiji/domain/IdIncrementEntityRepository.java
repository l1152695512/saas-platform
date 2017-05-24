package com.jikezhiji.domain;

import org.springframework.data.domain.Persistable;

/**
 * Created by E355 on 2016/8/25.
 */
public interface IdIncrementEntityRepository {
    Long getNextId(Class<? extends Persistable<Long>> entityClass);
}
