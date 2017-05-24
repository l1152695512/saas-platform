package com.jikezhiji.tenant.aggregate.value;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * TenantMetadataMetadata is a Querydsl query type for TenantMetadata
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class TenantMetadataMetadata extends BeanPath<TenantDetailProperties> {

    private static final long serialVersionUID = 926605935L;

    public static final TenantMetadataMetadata tenantMetadata = new TenantMetadataMetadata("tenantMetadata");

    public final MapPath<String, Object, SimplePath<Object>> bean = this.<String, Object, SimplePath<Object>>createMap("scope", String.class, Object.class, SimplePath.class);

    public final StringPath beanName = createString("beanName");

    public final StringPath beanType = createString("beanType");

    public TenantMetadataMetadata(String variable) {
        super(TenantDetailProperties.class, forVariable(variable));
    }

    public TenantMetadataMetadata(Path<? extends TenantDetailProperties> path) {
        super(path.getType(), path.getMetadata());
    }

    public TenantMetadataMetadata(PathMetadata metadata) {
        super(TenantDetailProperties.class, metadata);
    }

}

