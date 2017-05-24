package com.jikezhiji.domain.command.converters;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E355 on 2016/9/2.
 */
public final class ConverterHolder {

    public static List<Converter<?,?>> mongoConverters = new ArrayList<>();

    public static final void addMongoConverter(Converter<?,?> converter) {
        mongoConverters.add(converter);
    }

    public static final List<Converter<?,?>> extraMongoConverters(){
        return mongoConverters;
    }

    private static CustomConversions conversions;
    private static DefaultConversionService defaultConversionService;

    public static void initCustomConversions(CustomConversions conversions,DefaultConversionService defaultConversionService){
        ConverterHolder.conversions = conversions;
        ConverterHolder.defaultConversionService = defaultConversionService;

    }

    public static boolean hasCustomReadTarget(Class<?> sourceType, Class<?> requestedTargetType) {
        return conversions.hasCustomReadTarget(sourceType,requestedTargetType);
    }

    public static Object convert(Object source, Class<?> targetType){
        return defaultConversionService.convert(source,targetType);
    }
}


