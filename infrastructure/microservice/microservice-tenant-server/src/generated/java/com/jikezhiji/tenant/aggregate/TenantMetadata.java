package com.jikezhiji.tenant.aggregate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * TenantMetadata is a Querydsl query type for Tenant
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class TenantMetadata extends EntityPathBase<Tenant> {

    private static final long serialVersionUID = 1386839747L;

    public static final TenantMetadata tenant = new TenantMetadata("tenant");

    public final com.jikezhiji.domain.command.AutoIncrementEsarMetadata _super = new com.jikezhiji.domain.command.AutoIncrementEsarMetadata(this);

    public final StringPath code = createString("code");

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final SetPath<String, StringPath> hosts = this.<String, StringPath>createSet("hosts", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public TenantMetadata(String variable) {
        super(Tenant.class, forVariable(variable));
    }

    public TenantMetadata(Path<? extends Tenant> path) {
        super(path.getType(), path.getMetadata());
    }

    public TenantMetadata(PathMetadata metadata) {
        super(Tenant.class, metadata);
    }

}

