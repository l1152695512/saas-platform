package com.jikezhiji.domain.command.converters;

import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Created by E355 on 2016/9/27.
 */
@ReadingConverter
public interface MongoReadingConverter<O> extends Converter<DBObject,O> {
}
