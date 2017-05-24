package com.jikezhiji.domain.command.core.simple;

import com.jikezhiji.domain.command.EventSourcingAggregateRoot;
import com.jikezhiji.domain.command.core.simple.OperationEvent;
import com.jikezhiji.domain.command.event.GenericEventListener;
import com.jikezhiji.domain.query.DynamicReadingEntity;
import com.jikezhiji.domain.query.ReadingEntity;
import com.jikezhiji.domain.query.QueryRepositorySupport;
import org.springframework.core.convert.ConversionService;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by E355 on 2016/9/20.
 */
public class OperationEventSynchronizationListener extends GenericEventListener<OperationEvent> {

    private QueryRepositorySupport querySupport;

    protected ConversionService converter;

    public OperationEventSynchronizationListener(QueryRepositorySupport querySupport, ConversionService converter) {
        this.querySupport = querySupport;
        this.converter = converter;
    }


    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public void handleEvent(OperationEvent event) {
        EventSourcingAggregateRoot domain = event.getAggregateRoot();
        DynamicReadingEntity<Serializable> entity = new DynamicReadingEntity<>(
                event.getAggregateRootClass(),
                converter.convert(domain,Map.class));
        if(domain.isDeleted()) {
            this.querySupport.remove(event.getAggregateRootId(), (Class<? extends ReadingEntity<Serializable>>) domain.getClass());
        } else {
            if(domain.isNew()) {
                entity.put(DynamicReadingEntity.CREATED_BY,event.getTriggeredBy());
                entity.put(DynamicReadingEntity.CREATED_TIME,event.getTriggeredTime());
            } else {
                entity.put(DynamicReadingEntity.LAST_MODIFIED_BY,event.getTriggeredBy());
                entity.put(DynamicReadingEntity.LAST_MODIFIED_TIME,event.getTriggeredTime());
            }
            this.querySupport.saveOrUpdate(entity);
        }
    }

}
