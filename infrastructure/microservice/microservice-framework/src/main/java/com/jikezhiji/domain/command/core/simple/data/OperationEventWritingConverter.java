package com.jikezhiji.domain.command.core.simple.data;

import com.jikezhiji.domain.AggregateRootException;
import com.jikezhiji.domain.command.converters.EnhancedSimpleTypeHolder;
import com.jikezhiji.domain.command.converters.ConverterHolder;
import com.jikezhiji.domain.command.converters.MongoWritingConverter;
import com.jikezhiji.domain.command.core.simple.OperationEvent;
import com.jikezhiji.domain.command.event.Event;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import org.springframework.data.convert.WritingConverter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.SerializationUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by E355 on 2016/9/27.
 */
@WritingConverter
public class OperationEventWritingConverter implements MongoWritingConverter<OperationEvent> {

    @Override
    public BasicDBObject convert(OperationEvent source) {
        BasicDBObject obj = new BasicDBObject();
        obj.append(OperationEvent.ID,source.getId());
        obj.append(OperationEvent.AGGREGATE_ROOT_ID,source.getAggregateRootId());
        obj.append(OperationEvent.TRIGGERED_BY,source.getTriggeredBy());
        obj.append(OperationEvent.TRIGGERED_TIME,source.getTriggeredTime());
        obj.append(OperationEvent.PAYLOAD,SerializationUtils.serialize(source.getPayload()));
        obj.append(OperationEvent.ATTRIBUTES,SerializationUtils.serialize(source.getAttributes()));
        return obj;
    }
    static {
        ConverterHolder.addMongoConverter(new OperationEventWritingConverter());
    }

    private OperationEventWritingConverter(){

    }
}
