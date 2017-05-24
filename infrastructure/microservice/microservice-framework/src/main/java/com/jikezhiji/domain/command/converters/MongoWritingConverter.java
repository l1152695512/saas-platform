package com.jikezhiji.domain.command.converters;

import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Created by E355 on 2016/9/27.
 */
@WritingConverter
public interface MongoWritingConverter<O> extends Converter<O,DBObject> {
}
