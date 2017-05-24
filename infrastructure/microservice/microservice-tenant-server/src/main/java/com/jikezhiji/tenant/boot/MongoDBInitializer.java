package com.jikezhiji.tenant.boot;

import com.jikezhiji.core.Tenant;
import com.jikezhiji.core.boot.ApplicationInitializeEvent;
import com.jikezhiji.core.boot.ApplicationInitializer;
import com.jikezhiji.tenant.aggregate.Service;
import com.jikezhiji.tenant.aggregate.TenantService;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.DbCallback;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by E355 on 2016/10/17.
 */
public class MongoDBInitializer implements ApplicationInitializer<ApplicationInitializeEvent<?>> {

    @Autowired
    private MongoTemplate commandTemplate;

    @Autowired
    private MongoTemplate queryTemplate;

    @Override
    public int getVersion() {
        return Version.FIRST.value;
    }


    @Override
    public void onApplicationEvent(ApplicationInitializeEvent<?> event) {
        createCommandCollection();
        createQueryCollection();
    }

    public void createCommandCollection(){
        if(commandTemplate.collectionExists(Tenant.class)) {
            commandTemplate.createCollection(Tenant.class);
        }

        if(commandTemplate.collectionExists(Service.class)) {
            commandTemplate.createCollection(Service.class);
        }

        if(commandTemplate.collectionExists(TenantService.class)) {
            commandTemplate.createCollection(TenantService.class);
        }
    }

    public void createQueryCollection(){
        if(queryTemplate.collectionExists(Tenant.class)) {
            queryTemplate.createCollection(Tenant.class);
        }

        if(queryTemplate.collectionExists(Service.class)) {
            queryTemplate.createCollection(Service.class);
        }

        if(queryTemplate.collectionExists(TenantService.class)) {
            queryTemplate.createCollection(TenantService.class);
        }
    }
}
