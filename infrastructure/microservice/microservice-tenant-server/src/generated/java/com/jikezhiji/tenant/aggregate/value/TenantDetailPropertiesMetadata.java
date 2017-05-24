package com.jikezhiji.tenant.aggregate.value;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * TenantDetailPropertiesMetadata is a Querydsl query type for TenantDetailProperties
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class TenantDetailPropertiesMetadata extends BeanPath<TenantDetailProperties> {

    private static final long serialVersionUID = 259667876L;

    public static final TenantDetailPropertiesMetadata tenantDetailProperties = new TenantDetailPropertiesMetadata("tenantDetailProperties");

    public final MapPath<String, Object, SimplePath<Object>> bean = this.<String, Object, SimplePath<Object>>createMap("bean", String.class, Object.class, SimplePath.class);

    public final StringPath beanName = createString("beanName");

    public final StringPath beanType = createString("beanType");

    public TenantDetailPropertiesMetadata(String variable) {
        super(TenantDetailProperties.class, forVariable(variable));
    }

    public TenantDetailPropertiesMetadata(Path<? extends TenantDetailProperties> path) {
        super(path.getType(), path.getMetadata());
    }

    public TenantDetailPropertiesMetadata(PathMetadata metadata) {
        super(TenantDetailProperties.class, metadata);
    }

}

