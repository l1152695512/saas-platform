package com.jikezhiji.domain.command.core.simple.data;

import com.jikezhiji.domain.AggregateRootException;
import com.jikezhiji.domain.command.converters.ConverterHolder;
import com.jikezhiji.domain.command.converters.MongoReadingConverter;
import com.jikezhiji.domain.command.core.simple.BusinessOperation;
import com.jikezhiji.domain.command.core.simple.OperationEvent;
import com.jikezhiji.domain.command.event.Event;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.util.SerializationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E355 on 2016/9/27.
 */
@ReadingConverter
public class OperationEventReadingConverter implements MongoReadingConverter<OperationEvent> {
    @Override
    public OperationEvent convert(DBObject object) {
        try {
            BasicDBObject source = (BasicDBObject) object;

            OperationEvent event = new OperationEvent();
            event.setId(source.getString(Event.ID));
            event.setAggregateRootId(source.getLong(Event.AGGREGATE_ROOT_ID));
            event.setTriggeredBy(source.getString(Event.TRIGGERED_BY));
            event.setTriggeredTime(source.getDate(Event.TRIGGERED_TIME));
            event.setPayload(toOperations(source.get(Event.PAYLOAD)));

            BasicDBObject attributes = (BasicDBObject) source.get(Event.ATTRIBUTES);
            event.setAttributes(toAttributes(attributes));

            return event;
        } catch (Exception e) {
            throw new AggregateRootException(e);
        }
    }
    private Map<String,Object> toAttributes(Object attributes){
        if(attributes instanceof byte[]) {
            Object obj = SerializationUtils.deserialize((byte[]) attributes);
            if(obj instanceof Map) {
                Map storedMap = (Map) obj;
                Map<String,Object> values = new HashMap<>();
                for (Object o: storedMap.keySet()) {
                    if(o instanceof String) {
                        values.put((String) o,storedMap.get(o));
                    } else {
                        throw new AggregateRootException("未知的序列化对象 ->"+storedMap);
                    }
                }
                return values;
            }
        }
        throw new AggregateRootException("attributes不是byte[]，无法反序列化 ->"+attributes.getClass().getName());
    }

    private Collection<BusinessOperation> toOperations(Object payload){
        if(payload instanceof byte[]) {
            Object obj = SerializationUtils.deserialize((byte[]) payload);
            if(obj instanceof Collection) {
                Collection collection = (Collection) obj;
                Collection<BusinessOperation> operationList = new ArrayList<>();
                for (Object o: collection) {
                    if(o instanceof BusinessOperation) {
                        operationList.add((BusinessOperation) o);
                    } else {
                        throw new AggregateRootException("未知的序列化对象 ->"+collection);
                    }
                }
                return operationList;
            }
        }
        throw new AggregateRootException("payload不是byte[]，无法反序列化 ->"+payload.getClass().getName());
    }



    static {
        ConverterHolder.addMongoConverter(new OperationEventReadingConverter());
    }
    private OperationEventReadingConverter(){

    }
}
