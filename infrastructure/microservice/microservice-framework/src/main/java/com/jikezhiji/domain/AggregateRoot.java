package com.jikezhiji.domain;

import org.springframework.data.domain.Persistable;

import java.io.Serializable;

/**
 * Created by E355 on 2016/8/25.
 */
public class AggregateRoot<ID extends Serializable> implements Persistable<ID> {
    public static final String VERSION = "version";

    private ID id;
    private int version;

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public ID getId() {
        return id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version){
        this.version = version;
    }

    @Override
    public boolean isNew() {
        return getVersion() == 0;
    }
}
