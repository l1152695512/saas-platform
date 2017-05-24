package com.jikezhiji.domain.query;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by E355 on 2016/9/23.
 */
public interface QueryRepositorySupport {
    void saveOrUpdate(DynamicReadingEntity<?> entity);
    void saveOrUpdate(StaticReadingEntity<?> entity);
    void saveOrUpdate(ReadingEntity<?> entity);
    <ID extends Serializable> void remove(ID id,String tableOrCollection);
    <ID extends Serializable> void remove(ID id,Class<? extends ReadingEntity<ID>> type);
    <T> T findById(Class<T> projectionType,Serializable id);
    <T> T findOne(Class<T> projectionType,Predicate predicate);

    <T> List<T> findAll(Class<T> projectionType,Predicate predicate);
    <T> List<T> findAll(Class<T> projectionType,Predicate predicate, Sort sort);
    <T> List<T> findAll(Class<T> projectionType, Predicate predicate, OrderSpecifier<?>... orders);
    <T> Iterable<T> findAll(Class<T> projectionType,OrderSpecifier<?>... orders);
    <T> Page<T> findAll(Class<T> projectionType,Predicate predicate, Pageable pageable);
    <T> Page<T> findAll(Class<T> projectionType,Pageable pageable);
    long count(Class<?> projectionType, Predicate predicate);
    boolean exists(Class<?> projectionType, Predicate predicate);
}
