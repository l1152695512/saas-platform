package com.jikezhiji.rest.provider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {
    private Log log = LogFactory.getLog(ContextResolver.class);
    
    private Map<Class<?>,JAXBContext> jaxbCaches = new HashMap<>();
    
    @Override
    public JAXBContext getContext(Class<?> type) {
        try {
            if(!jaxbCaches.containsKey(type)) {
                if(type.getAnnotation(XmlRootElement.class) != null) {
                    jaxbCaches.put(type,JAXBContext.newInstance(type));
                }
            }
        } catch (JAXBException e){
            log.warn("JAXBContext无法初始化，type:"+type.getName());
        }
        return jaxbCaches.get(type);
    }
}