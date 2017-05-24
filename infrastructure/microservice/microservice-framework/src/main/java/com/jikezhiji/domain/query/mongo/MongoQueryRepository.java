package com.jikezhiji.domain.query.mongo;

import com.jikezhiji.domain.query.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by E355 on 2016/9/20.
 */
public class MongoQueryRepository implements QueryRepositorySupport {

    protected ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    protected MongoTemplate template;
    protected MongoConverter converter;
    protected MappingContext<? extends MongoPersistentEntity<?>, ?> mapping;

    private Map<String, MongoPersistentEntity<?>> collectionMapping = new HashMap<>();

    private Map<Class<?>, QueryDslMongoRepository<?,?>> repositoryMapping = new HashMap<>();



    public MongoQueryRepository(MongoTemplate template) {
        Assert.notNull(template);
        Assert.notNull(mapping);
        this.template = template;
        this.converter = template.getConverter();
        this.mapping = template.getConverter().getMappingContext();
        buildMappings(mapping);
    }

    private void buildMappings(MappingContext<? extends MongoPersistentEntity<?>, ?> mapping){
        mapping.getPersistentEntities().forEach(bmp -> collectionMapping.put(bmp.getCollection(),bmp));
    }

    public <ID extends Serializable> void remove(ID id,String tableOrCollection) {
        Query byId = Query.query(Criteria.where(ChangeSetPersister.ID_KEY).is(id));
        template.remove(byId, tableOrCollection);
    }

    public <ID extends Serializable> void remove(ID id,Class<? extends ReadingEntity<ID>> type) {
        MongoPersistentEntity<?> bmp = mapping.getPersistentEntity(type);
        String idFieldName = bmp.getIdProperty().getFieldName();
        Query byId = Query.query(Criteria.where(idFieldName).is(id));
        template.remove(byId,type);
    }

    public void saveOrUpdate(DynamicReadingEntity<?> entity) {
        DynamicReadingEntity obj = (DynamicReadingEntity)entity;
        String collection = mapping.getPersistentEntity(entity.getEntityType()).getCollection();
        if(obj.isNew()) {
            template.insert(obj,collection);
        } else {
            template.updateFirst(Query.query(Criteria.where(obj.getIdKey()).is(obj.getId())),
                    Update.fromDBObject(new BasicDBObject(obj)),collection);
        }
    }

    public void saveOrUpdate(StaticReadingEntity<?> entity) {
        Query byId = byId(entity);
        StaticReadingEntity persistedEntity = template.findOne(byId,entity.getClass());
        if(persistedEntity != null){
            entity.setCreatedBy(persistedEntity.getCreatedBy());
            entity.setCreatedTime(persistedEntity.getCreatedTime());
        }
        saveOrUpdate(byId,entity,persistedEntity != null);
    }

    public void saveOrUpdate(ReadingEntity<?> entity) {
        Query byId = byId(entity);
        saveOrUpdate(byId,entity,template.exists(byId,entity.getClass()));
    }


    public <T> T findById(Class<T> projectionType,Serializable id) {
        Object collectionOrEntityType = getCollectionOrEntityType(projectionType);
        final Object source;
        // 1、查询出dbo对象
        if(collectionOrEntityType instanceof Class) {
            source = template.findById(id,(Class<Persistable<?>>)collectionOrEntityType);
        } else {
            source = template.execute((String)collectionOrEntityType,collection->collection.findOne(id));
        }
        return convertToProjection(projectionType,source);
    }



    public <T> T findOne(Class<T> projectionType, Predicate predicate) {
        return convertToProjection(projectionType, repository(projectionType).findOne(predicate));
    }

    @Override
    public <T> List<T> findAll(Class<T> projectionType, Predicate predicate) {
        return createProjectionList(projectionType, repository(projectionType).findAll(predicate));
    }

    @Override
    public <T> List<T> findAll(Class<T> projectionType, Predicate predicate, Sort sort) {
        return createProjectionList(projectionType,repository(projectionType).findAll(predicate,sort));
    }

    @Override
    public <T> List<T> findAll(Class<T> projectionType, Predicate predicate, OrderSpecifier<?>... orders) {
        return createProjectionList(projectionType,repository(projectionType).findAll(predicate,orders));
    }

    @Override
    public <T> Iterable<T> findAll(Class<T> projectionType, OrderSpecifier<?>... orders) {
        return createProjectionList(projectionType,repository(projectionType).findAll(orders));
    }

    @Override
    public <T> Page<T> findAll(Class<T> projectionType, Predicate predicate, Pageable pageable) {
        return createProjectionPage(projectionType,repository(projectionType).findAll(predicate,pageable));
    }

    @Override
    public <T> Page<T> findAll(Class<T> projectionType, Pageable pageable) {
        return null;
    }

    @Override
    public long count(Class<?> projectionType, Predicate predicate) {
        return repository(projectionType).count(predicate);
    }

    @Override
    public boolean exists(Class<?> projectionType, Predicate predicate) {
        return repository(projectionType).exists(predicate);
    }

    private void saveOrUpdate(Query byId,ReadingEntity<?> entity,boolean updateOpt) {
        if(updateOpt) {
            DBObject dbo = (DBObject) converter.convertToMongoType(entity);
            template.updateFirst(byId,Update.fromDBObject(dbo),entity.getClass());
        } else {
            template.insert(entity);
        }
    }

    private Query byId(ReadingEntity<?> entity) {
        MongoPersistentEntity<?> bmp = mapping.getPersistentEntity(entity.getClass());
        String idFieldName = bmp.getIdProperty().getFieldName();
        return Query.query(Criteria.where(idFieldName).is(entity.getId()));
    }

    private Object getCollectionOrEntityType(Class<?> projectionType){
        QueryProjection projection = projectionType.getAnnotation(QueryProjection.class);
        if(projection.type() != Persistable.class) {
            return projection.type();
        }
        if(!StringUtils.isEmpty(projection.mapping())) {
            return projection.mapping();
        } else {
            throw new ProjectionMappingException("注解@QueryProjection配置错误，必须配置mapping或type");
        }
    }

    private QueryDslMongoRepository<?,?> repository(Class<?> projectionType){
        QueryDslMongoRepository<?,?> repository = repositoryMapping.get(projectionType);
        if(repository == null) {
            MongoEntityInformation<?,?> information;
            QueryProjection projection = projectionType.getAnnotation(QueryProjection.class);
            if(projection.type() != Persistable.class) {
                information = new MappingMongoEntityInformation(mapping.getPersistentEntity(projection.type()));
            } else if(!StringUtils.isEmpty(projection.mapping())){
                String collection = projection.mapping();
                if(collectionMapping.containsKey(collection)) {
                    information = new MappingMongoEntityInformation(collectionMapping.get(collection));
                } else {
                    information = new QueryProjectionEntityInformation(collection,projection.id(),projectionType);
                }
            } else {
                throw new ProjectionMappingException("注解@QueryProjection配置错误，必须配置mapping或type");
            }
            repository = repositoryMapping.put(projectionType,new QueryDslMongoRepository(information,template));
        }
        return repository;
    }

    /**
     * 将源对象转换为projection对象
     * @param projectionType
     * @param source
     * @param <T>
     * @return
     */
    private <T> T convertToProjection(Class<T> projectionType, Object source) {
        if(source instanceof DBObject) {
            if(MongoProjection.class.isAssignableFrom(projectionType)) {
                try{
                    return projectionType.getConstructor(DBObject.class).newInstance(source);
                } catch (Exception e) {
                    throw new ProjectionMappingException("type:"+projectionType.getName()+" 的构造函数调用被阻止",e);
                }
            }
        }
        if(converter.getConversionService().canConvert(source.getClass(),projectionType)){
            return converter.getConversionService().convert(source,projectionType);
        }

        return factory.createProjection(projectionType,source);
    }

    private <T> List<T> createProjectionList(Class<T> projectionType, List<?> sources) {
        return sources.stream()
                .map(source -> convertToProjection(projectionType,source))
                .collect(Collectors.toList());
    }

    private <T> List<T> createProjectionList(Class<T> projectionType, Iterable<?> sources) {
        List<T> content = new ArrayList<>();
        sources.forEach(source -> content.add(convertToProjection(projectionType,source)));
        return content;
    }
    private <T> Page<T> createProjectionPage(Class<T> projectionType, Page<?> sources) {
        return sources.map(source -> convertToProjection(projectionType,source));
    }

}
