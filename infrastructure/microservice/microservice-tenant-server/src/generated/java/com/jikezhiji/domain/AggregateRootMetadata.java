package com.jikezhiji.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * AggregateRootMetadata is a Querydsl query type for AggregateRoot
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class AggregateRootMetadata extends BeanPath<AggregateRoot<? extends java.io.Serializable>> {

    private static final long serialVersionUID = -1509936367L;

    public static final AggregateRootMetadata aggregateRoot = new AggregateRootMetadata("aggregateRoot");

    public final SimplePath<java.io.Serializable> id = createSimple("id", java.io.Serializable.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public AggregateRootMetadata(String variable) {
        super((Class) AggregateRoot.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public AggregateRootMetadata(Path<? extends AggregateRoot> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public AggregateRootMetadata(PathMetadata metadata) {
        super((Class) AggregateRoot.class, metadata);
    }

}

