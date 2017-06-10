package com.jikezhiji.survey.persistence.converter;

import javax.persistence.AttributeConverter;
import java.util.Locale;

/**
 * Created by liusizuo on 2017/5/26.
 */
public class LocaleConverter implements AttributeConverter<Locale,String> {
    @Override
    public String convertToDatabaseColumn(Locale attribute) {
        return attribute.toString();
    }

    @Override
    public Locale convertToEntityAttribute(String text) {
        String[] lc = text.split("_");
        return new Locale(lc[0],lc[1]);
    }

}
