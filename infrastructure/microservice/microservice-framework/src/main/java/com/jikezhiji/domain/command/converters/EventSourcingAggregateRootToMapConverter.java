package com.jikezhiji.domain.command.converters;

import com.jikezhiji.domain.AggregateRootException;
import com.jikezhiji.domain.command.EventSourcingAggregateRoot;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mapping.model.SimpleTypeHolder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by E355 on 2016/9/23.
 */
public class EventSourcingAggregateRootToMapConverter implements Converter<EventSourcingAggregateRoot<?>,Map<String,Object>> {
    private SimpleTypeHolder holder ;
    public EventSourcingAggregateRootToMapConverter(){
        Collection<Class<?>> simpleTypes = Arrays.asList(Temporal.class,CharSequence.class,Number.class,Buffer.class,
                Pattern.class, UUID.class,URI.class,URL.class);
        holder = new SimpleTypeHolder(new HashSet<>(simpleTypes), true);
    }

    @Override
    public Map<String, Object> convert(EventSourcingAggregateRoot<?> source) {
        if(source == null) {
            return new HashMap<>();
        }
        return convertBeanToMap(source);
    }

    private Map<String,Object> convertBeanToMap(Object source) {
        Map<String, Object> dynamic = new HashMap<>();
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                dynamic.put(field.getName(),convertValue(value));
            } catch (IllegalAccessException e) {
                throw new AggregateRootException("无法访问field,type:"+source.getClass()+"+,field:"+field);
            }
        }
        return dynamic;
    }
    private boolean isContainerType(Class<?> type) {
        return Optional.class == type || Map.class.isAssignableFrom(type) ||
                Collection.class.isAssignableFrom(type) || type.isArray();
    }
    private Object convertValue(Object value){
        if( value == null ) return null;
        Class<?> type = value.getClass();
        if(isContainerType(type) || holder.isSimpleType(value.getClass())) {
            if(Optional.class == type) {
                return convertValue(((Optional)value).get());
            }else if(Map.class.isAssignableFrom(type)) {
                Map map = (Map)value;
                map.forEach((k,v)->map.put(k,convertValue(v)));
            } else if(Collection.class.isAssignableFrom(type)) {
                return ((Collection<?>)value).stream().map(s->convertValue(s)).collect(Collectors.toList());
            } else if (type.isArray()) {
                int length = Array.getLength(value);
                for (int i=0; i<length;i++) {
                    Array.set(value,i,convertValue(Array.get(value,i)));
                }
            }
            return value;
        }
        return convertBeanToMap(value);
    }
}