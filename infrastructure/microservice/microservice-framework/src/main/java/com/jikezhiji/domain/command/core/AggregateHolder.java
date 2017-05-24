package com.jikezhiji.domain.command.core;

import com.jikezhiji.domain.AggregateRoot;
import com.jikezhiji.domain.command.EventSourcingAggregateRoot;
import io.jsonwebtoken.lang.Assert;

import java.io.Serializable;

/**
 * Created by E355 on 2016/8/31.
 */
public class AggregateHolder {
    private Serializable id;
    private Class<? extends AggregateRoot> type;
    private EventSourcingAggregateRoot<?> aggregateRoot;

    public AggregateHolder(EventSourcingAggregateRoot<?> aggregateRoot) {
        Assert.notNull(aggregateRoot);
        this.aggregateRoot = aggregateRoot;
        this.id = aggregateRoot.getId();
        this.type = aggregateRoot.getClass();
    }
    public AggregateHolder(Class<? extends AggregateRoot> type) {
        Assert.notNull(type);
        this.type = type;
    }
    public AggregateHolder(Class<? extends AggregateRoot> type, Serializable id) {
        Assert.notNull(type);
        this.type = type;
        this.id = id;
    }

    @Override
    public AggregateHolder clone() {
        return this.clone();
    }

    public <A extends EventSourcingAggregateRoot<ID>, ID extends Serializable> Class<A> getType() {
        return (Class<A>) type;
    }

    public <ID extends Serializable> ID getId() {
        return (ID) id;
    }

    public <ID extends Serializable> AggregateHolder setId(ID id) {
        this.id = id;
        return this;
    }

    public <A extends EventSourcingAggregateRoot<ID>, ID extends Serializable> A getAggregateRoot() {
        return (A) aggregateRoot;
    }

    public <A extends EventSourcingAggregateRoot<ID>, ID extends Serializable> AggregateHolder setAggregateRoot(A aggregateRoot) {
        this.aggregateRoot = aggregateRoot;
        return this;
    }
}
