package com.jikezhiji.domain.command.converters;

import org.springframework.data.mapping.model.SimpleTypeHolder;

import java.net.URI;
import java.net.URL;
import java.nio.Buffer;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/28.
 */
public class EnhancedSimpleTypeHolder extends SimpleTypeHolder{
    private static Set<Class<?>> DEFAULT_SIMPLE_TYPES=  new HashSet<>(
            Arrays.asList(Temporal.class,CharSequence.class,Number.class,
                    Buffer.class, Pattern.class, UUID.class,URI.class,URL.class));

    private static Set<Class<?>> getDefaultTypes(Class<?> ... classes) {
        Set<Class<?>> defaultTypes =  new HashSet<>(DEFAULT_SIMPLE_TYPES);
        defaultTypes.addAll(Arrays.asList(classes));
        return defaultTypes;
    }
    public EnhancedSimpleTypeHolder(Class<?> ... classes){
        super(getDefaultTypes(classes), true);
    }

    public final static EnhancedSimpleTypeHolder holder = new EnhancedSimpleTypeHolder();
}
