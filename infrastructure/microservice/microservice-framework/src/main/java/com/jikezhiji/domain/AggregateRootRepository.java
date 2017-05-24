package com.jikezhiji.domain;

import java.io.Serializable;

/**
 * Created by E355 on 2016/8/25.
 */
public interface AggregateRootRepository {

    <ID extends Serializable,A extends AggregateRoot<ID>> A findById(Class<A> aggregateRootClass, ID id);

    <ID extends Serializable,A extends AggregateRoot<ID>> A findByIdIfAbsent(Class<A> aggregateRootClass, ID id);
}
