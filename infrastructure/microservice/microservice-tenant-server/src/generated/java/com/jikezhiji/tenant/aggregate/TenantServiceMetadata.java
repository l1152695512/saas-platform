package com.jikezhiji.tenant.aggregate;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * TenantServiceMetadata is a Querydsl query type for TenantService
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class TenantServiceMetadata extends EntityPathBase<TenantService> {

    private static final long serialVersionUID = -615625102L;

    public static final TenantServiceMetadata tenantService = new TenantServiceMetadata("tenantService");

    public final com.jikezhiji.domain.command.AutoIncrementEsarMetadata _super = new com.jikezhiji.domain.command.AutoIncrementEsarMetadata(this);

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final SetPath<com.jikezhiji.tenant.aggregate.value.TenantDetailProperties, com.jikezhiji.tenant.aggregate.value.TenantDetailPropertiesMetadata> metadataSet = this.<com.jikezhiji.tenant.aggregate.value.TenantDetailProperties, com.jikezhiji.tenant.aggregate.value.TenantDetailPropertiesMetadata>createSet("metadataSet", com.jikezhiji.tenant.aggregate.value.TenantDetailProperties.class, com.jikezhiji.tenant.aggregate.value.TenantDetailPropertiesMetadata.class, PathInits.DIRECT2);

    public final StringPath remark = createString("remark");

    public final StringPath serviceId = createString("serviceId");

    public final DateTimePath<java.time.LocalDateTime> startTime = createDateTime("startTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> tenantId = createNumber("tenantId", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public TenantServiceMetadata(String variable) {
        super(TenantService.class, forVariable(variable));
    }

    public TenantServiceMetadata(Path<? extends TenantService> path) {
        super(path.getType(), path.getMetadata());
    }

    public TenantServiceMetadata(PathMetadata metadata) {
        super(TenantService.class, metadata);
    }

}

