package com.jikezhiji.domain.query.mongo;

import com.jikezhiji.domain.query.Projection;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created by E355 on 2016/9/23.
 */
public class QueryProjectionEntityInformation<T extends Projection, ID extends Serializable> implements MongoEntityInformation<T, ID> {
    private String collection;
    private String idKey = ChangeSetPersister.ID_KEY;
    private Class<T> javaType;
    public QueryProjectionEntityInformation(String collection, String idKey, Class<T> javaType) {
        this.collection = collection;
        if(!StringUtils.isEmpty(idKey)) {
            this.idKey = idKey;
        }
        this.javaType = javaType;
    }

    @Override
    public String getCollectionName() {
        return collection;
    }

    @Override
    public String getIdAttribute() {
        return idKey;
    }

    @Override
    public Class<T> getJavaType() {
        return javaType;
    }

    @Override
    public boolean isNew(T entity) {
        return false;
    }

    @Override
    public ID getId(T entity) {
        return entity.getValue(getIdAttribute());
    }

    @Override
    public Class<ID> getIdType() {
        return (Class<ID>)Serializable.class;
    }

}
