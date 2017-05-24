package com.jikezhiji.domain.command.core.simple.data;

import com.jikezhiji.domain.*;
import com.jikezhiji.domain.command.*;
import com.jikezhiji.domain.command.core.simple.OperationEvent;
import com.jikezhiji.domain.command.event.Event;
import com.jikezhiji.domain.command.repository.CommandRepositorySupport;
import com.mongodb.BasicDBObject;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.BasicMongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;


/**
 * Created by E355 on 2016/8/22.
 */
public class MongoSnapshotRepository implements CommandRepositorySupport {


    protected static final String CURRENT_ID = "currentId";
    protected static final String AGGREGATE_ROOT_NAME = "aggregateRootName";


    protected MongoTemplate template;

    protected MappingContext<? extends MongoPersistentEntity<?>, ?> mapping;

    protected PropertyResolver resolver;

    protected int buildSnapshotInterval = 3;

    public MongoSnapshotRepository(MongoTemplate template,
                                   PropertyResolver resolver,int buildSnapshotInterval){
        Assert.notNull(template);
        Assert.notNull(resolver);
        Assert.isTrue(buildSnapshotInterval > 0);
        this.template = template;
        this.mapping = template.getConverter().getMappingContext();
        this.resolver = resolver;
        this.buildSnapshotInterval = buildSnapshotInterval;
    }

    @Override
    public Long getNextId(Class<? extends Persistable<Long>> aggregateRootClass){
        try {
            String sequenceCollection = getSequenceCollection(aggregateRootClass);
            String aggregateRootName = getCollection(aggregateRootClass);

            Query query = Query.query(Criteria.where(AGGREGATE_ROOT_NAME).is(aggregateRootName));
            Update update = new Update().inc(CURRENT_ID, 1);
            BasicDBObject record = template.findAndModify(query, update, BasicDBObject.class,sequenceCollection);

            if(record == null) {
                record = new BasicDBObject(AGGREGATE_ROOT_NAME,aggregateRootName).append(CURRENT_ID,1L);
                if(!template.collectionExists(sequenceCollection)) {
                    throw new AggregateRootPersistException(aggregateRootClass.getName(),"sequence collection不存在 -> "+sequenceCollection);
                }
                template.getCollection(sequenceCollection).insert(record);
                return record.getLong(CURRENT_ID);
            }
            return record.getLong(CURRENT_ID) + 1;
        } catch (MongoException e) {
            throw new AggregateRootPersistException(aggregateRootClass.getName(),"获取聚合跟的ID序列失败");
        }
    }

    @Override
    public <ID extends Serializable,A extends AggregateRoot<ID>> A findById(Class<A> aggregateRootClass, ID id) {
        return findByIdIfAbsent(aggregateRootClass,id,false);
    }

    @Override
    public <ID extends Serializable, A extends AggregateRoot<ID>> A findByIdIfAbsent(Class<A> aggregateRootClass, ID id) {
        return findByIdIfAbsent(aggregateRootClass,id,true);
    }

    private <ID extends Serializable,A extends AggregateRoot<ID>> A findByIdIfAbsent(Class<A> type, ID id,boolean absent){
        AggregateRoot entity = template.findById(id,type);
        if(EventSourcingAggregateRoot.class.isAssignableFrom(type)) {
            Query findById = Query.query(Criteria.where(Event.AGGREGATE_ROOT_ID).is(id));

            if(absent || template.count(findById,getEventCollection(type)) > 0 ) {
                EventSourcingAggregateRoot<ID> es = (EventSourcingAggregateRoot<ID>)
                        (entity == null ?  newInstance(type,id): type.cast(entity));

                Query skip = findById.skip(entity.getVersion()).with(new Sort(Event.TRIGGERED_TIME));
                List<OperationEvent> events = template.find(skip,OperationEvent.class,getEventCollection(type));
                events.forEach(event -> OperationEventApplicator.apply(es,event));
                if(events.size() > buildSnapshotInterval ) {
                    try {
                        template.save(es,getCollection(type));
                    } catch (MongoException e){
                        throw new AggregateRootVersionException(es,"聚合根保存失败");
                    }
                }
            }
        }
        return (A)entity;
    }

    private <ID extends Serializable,A extends AggregateRoot<ID>> A newInstance(Class<A> type, ID id){
        try {
            A aggregateRoot = type.getConstructor().newInstance();
            PersistentEntity<?,?> entity = mapping.getPersistentEntity(type);
            PersistentProperty<?> idProperty = entity.getIdProperty();
            if(idProperty != null && idProperty.isIdProperty()) {
                if(idProperty.isWritable()) {
                    if(idProperty.getSetter() != null) {
                        idProperty.getSetter().invoke(aggregateRoot,id);
                    } else {
                        Field idField = idProperty.getField();
                        idField.setAccessible(true);
                        idField.set(aggregateRoot,id);
                    }
                    return aggregateRoot;
                }
            }
        } catch (Exception e) {
            throw new AggregateRootException("无法构造一个有效的聚合根对象，type:"+type.getName(),e);
        }
        throw new AggregateRootException("无法构造一个有效的聚合根对象，type:"+type.getName());
    }


    @Override
    public void persistEvent(Event event) {
        Class<? extends EventSourcingAggregateRoot> type = event.getAggregateRootClass();
        String eventCollection = getEventCollection(type);
        //这个应该是一个自动的过程。。不应该交给业务开发人员来维护
        if(!template.collectionExists(eventCollection)) {
            template.createCollection(eventCollection);
            template.getCollection(eventCollection).createIndex(
                    new BasicDBObject(Event.AGGREGATE_ROOT_ID,1).append(EventSourcingAggregateRoot.VERSION,1),
                    eventCollection+"VersionIndex" , true);
        }
        event.setVersion(event.getAggregateRoot().getVersion());
        try {
            template.insert(event,eventCollection);
        }catch (DuplicateKeyException e) {
            throw new AggregateRootVersionException(event,"Event版本冲突");
        }
    }



    protected String getCollection(Class<?> aggregateRootClass){
        return mapping.getPersistentEntity(aggregateRootClass).getCollection();
    }

    protected String getEventCollection(Class<?> aggregateRootClass){
        EnableEventSourcing metadata = mapping.getPersistentEntity(aggregateRootClass).findAnnotation(EnableEventSourcing.class);
        if(StringUtils.isEmpty(metadata.value())) {
            return getCollection(aggregateRootClass)+"Event";
        }
        return metadata.value();
    }

    protected String getSequenceCollection(Class<?> aggregateRootClass){
        IdIncrement aggregateRootSequence = mapping.getPersistentEntity(aggregateRootClass).findAnnotation(IdIncrement.class);
        return resolver.resolvePlaceholders(aggregateRootSequence.sequence());
    }
}
