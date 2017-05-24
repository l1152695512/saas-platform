package com.jikezhiji.domain.command;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * AutoIncrementEsarMetadata is a Querydsl query type for AutoIncrementEsar
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class AutoIncrementEsarMetadata extends BeanPath<AutoIncrementEsar> {

    private static final long serialVersionUID = 618979308L;

    public static final AutoIncrementEsarMetadata autoIncrementEsar = new AutoIncrementEsarMetadata("autoIncrementEsar");

    public final EventSourcingAggregateRootMetadata _super = new EventSourcingAggregateRootMetadata(this);

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public AutoIncrementEsarMetadata(String variable) {
        super(AutoIncrementEsar.class, forVariable(variable));
    }

    public AutoIncrementEsarMetadata(Path<? extends AutoIncrementEsar> path) {
        super(path.getType(), path.getMetadata());
    }

    public AutoIncrementEsarMetadata(PathMetadata metadata) {
        super(AutoIncrementEsar.class, metadata);
    }

}

