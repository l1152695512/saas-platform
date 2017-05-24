package com.jikezhiji.tenant.aggregate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * ServiceMetadata is a Querydsl query type for Service
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class ServiceMetadata extends EntityPathBase<Service> {

    private static final long serialVersionUID = -840830020L;

    public static final ServiceMetadata service = new ServiceMetadata("service");

    public final com.jikezhiji.domain.command.AutoIncrementEsarMetadata _super = new com.jikezhiji.domain.command.AutoIncrementEsarMetadata(this);

    public final StringPath apiEndpoint = createString("apiEndpoint");

    public final StringPath code = createString("code");

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath description = createString("description");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath initTenantEndpoint = createString("initTenantEndpoint");

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public ServiceMetadata(String variable) {
        super(Service.class, forVariable(variable));
    }

    public ServiceMetadata(Path<? extends Service> path) {
        super(path.getType(), path.getMetadata());
    }

    public ServiceMetadata(PathMetadata metadata) {
        super(Service.class, metadata);
    }

}

