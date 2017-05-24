package com.jikezhiji.domain.query;

import org.springframework.data.crossstore.ChangeSetPersister;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E355 on 2016/9/20.
 */
public class DynamicReadingEntity<ID extends Serializable> extends HashMap<String,Object> implements ReadingEntity<ID>,Auditable {


    private String idKey = ChangeSetPersister.ID_KEY;
    private final Class<?> entityType;

    public DynamicReadingEntity(Class<?> entityType, String idKey, Map<String,Object> values){
        this.entityType = entityType;
        this.idKey = idKey;
        this.putAll(values);
    }

    public DynamicReadingEntity(Class<?> entityType, Map<String,Object> values){
        this.entityType = entityType;
        this.putAll(values);
    }

    public Class<?> getEntityType(){
        return entityType;
    }

    public String getIdKey() {
        return idKey;
    }

    @Override
    public ID getId(){
        return getValue(idKey);
    }

    @Override
    public boolean isNew() {
        return containsKey(CREATED_TIME);
    }

    public <V> V getValue(Object key) {
        return (V)super.get(key);
    }




}
