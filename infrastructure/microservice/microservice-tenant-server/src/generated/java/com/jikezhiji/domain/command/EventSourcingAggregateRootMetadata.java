package com.jikezhiji.domain.command;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * EventSourcingAggregateRootMetadata is a Querydsl query type for EventSourcingAggregateRoot
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class EventSourcingAggregateRootMetadata extends BeanPath<EventSourcingAggregateRoot<? extends java.io.Serializable>> {

    private static final long serialVersionUID = -1168859070L;

    public static final EventSourcingAggregateRootMetadata eventSourcingAggregateRoot = new EventSourcingAggregateRootMetadata("eventSourcingAggregateRoot");

    public final com.jikezhiji.domain.AggregateRootMetadata _super = new com.jikezhiji.domain.AggregateRootMetadata(this);

    public final BooleanPath deleted = createBoolean("deleted");

    //inherited
    public final SimplePath<java.io.Serializable> id = _super.id;

    //inherited
    public final NumberPath<Integer> version = _super.version;

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public EventSourcingAggregateRootMetadata(String variable) {
        super((Class) EventSourcingAggregateRoot.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public EventSourcingAggregateRootMetadata(Path<? extends EventSourcingAggregateRoot> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public EventSourcingAggregateRootMetadata(PathMetadata metadata) {
        super((Class) EventSourcingAggregateRoot.class, metadata);
    }

}

