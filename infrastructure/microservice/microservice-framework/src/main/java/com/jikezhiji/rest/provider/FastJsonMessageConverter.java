package com.jikezhiji.rest.provider;

import com.alibaba.fastjson.JSON;
import org.springframework.util.StreamUtils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by E355 on 2016/9/9.
 */
@Provider
public class FastJsonMessageConverter implements MessageBodyReader<Object>, MessageBodyWriter<Object> {
    public static Charset UTF8 = Charset.forName("UTF-8");
    public static Charset getCharset(MediaType m) {
        String name = (m == null) ? null : m.getParameters().get(MediaType.CHARSET_PARAMETER);
        return (name == null) ? UTF8 : Charset.forName(name);
    }

    public boolean isSupported(MediaType m){
        return m.getSubtype().contains("json");
    }

    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isSupported(mediaType);
    }

    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {
        return JSON.parseObject(StreamUtils.copyToString(entityStream,getCharset(mediaType)),genericType);
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return isSupported(mediaType);
    }

    @Override
    public void writeTo(Object o, Class type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(JSON.toJSONString(o).getBytes(getCharset(mediaType)));

    }

    @Override
    public long getSize(Object o, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }




}
