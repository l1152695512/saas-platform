package com.jikezhiji.domain.query.mongo;

import com.jikezhiji.domain.query.Projection;
import com.mongodb.DBObject;

/**
 * Created by E355 on 2016/9/22.
 */
public abstract class MongoProjection implements Projection {

    public MongoProjection(DBObject dbo) {
        this.createInstance(dbo);
    }

    public abstract void createInstance(DBObject dbo);
}
