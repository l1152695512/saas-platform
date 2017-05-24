package com.jikezhiji.commons.domain.converter;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

public class CollectionConverter<T> extends JSONConverter<Collection<T>>{
    protected Class<T> elementType;
	
	public CollectionConverter() {
		elementType = getActualTypeClass(getClass().getGenericSuperclass());
	}
	public <T> Class<T> getActualTypeClass(Type type) {
		if (type instanceof ParameterizedType) {
			return getActualTypeClass(((ParameterizedType)type).getActualTypeArguments()[0]);
		} else if (type instanceof TypeVariable<?>) {
			return getActualTypeClass(((TypeVariable<?>)type).getBounds()[0]);
		} else {
			return (Class<T>)type;
		}
	}
	@Override
	public Collection<T> convertToEntityAttribute(String data) {
		return JSON.parseArray(data, elementType);
	}
}
