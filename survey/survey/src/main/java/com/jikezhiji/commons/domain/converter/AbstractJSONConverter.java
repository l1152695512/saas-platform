package com.jikezhiji.commons.domain.converter;

import com.alibaba.fastjson.JSON;
import org.springframework.core.GenericTypeResolver;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;

public abstract class AbstractJSONConverter<X, Y> implements AttributeConverter<X, Y> {

    protected Type javaType;
	protected Type databaseType;
	public AbstractJSONConverter(){

		Class<?>[] genericTypes = GenericTypeResolver.resolveTypeArguments(getClass(), AttributeConverter.class);
		javaType = genericTypes[0];
		databaseType = genericTypes[1];
	}
	
	
	public X convertToEntityAttribute(Y data) {
		return JSON.parseObject(String.valueOf(data), javaType);
	}
}
