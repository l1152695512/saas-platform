package com.jikezhiji.jaxb;

import com.sun.xml.internal.bind.Util;
import com.sun.xml.internal.bind.api.JAXBRIContext;
import com.sun.xml.internal.bind.v2.Messages;
import com.sun.xml.internal.bind.v2.model.annotation.RuntimeAnnotationReader;
import com.sun.xml.internal.bind.v2.runtime.JAXBContextImpl;
import com.sun.xml.internal.bind.v2.util.TypeCast;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Neo on 2016/4/22.
 */
public class ContextFactory {
    public static final String USE_JAXB_PROPERTIES = "_useJAXBProperties";

    public ContextFactory() {
    }

    public static JAXBContext createContext(Class[] classes, Map<String, Object> properties) throws JAXBException {
        Object properties1;
        if (properties == null) {
            properties1 = Collections.emptyMap();
        } else {
            properties1 = new HashMap(properties);
        }

        String defaultNsUri = (String) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.defaultNamespaceRemap", String.class);
        Boolean c14nSupport = (Boolean) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.c14n", Boolean.class);
        if (c14nSupport == null) {
            c14nSupport = Boolean.valueOf(false);
        }

        Boolean disablesecurityProcessing = (Boolean) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.disableXmlSecurity", Boolean.class);
        if (disablesecurityProcessing == null) {
            disablesecurityProcessing = Boolean.valueOf(false);
        }

        Boolean allNillable = (Boolean) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.treatEverythingNillable", Boolean.class);
        if (allNillable == null) {
            allNillable = Boolean.valueOf(false);
        }

        Boolean retainPropertyInfo = (Boolean) getPropertyValue((Map) properties1, "retainReferenceToInfo", Boolean.class);
        if (retainPropertyInfo == null) {
            retainPropertyInfo = Boolean.valueOf(false);
        }

        Boolean supressAccessorWarnings = (Boolean) getPropertyValue((Map) properties1, "supressAccessorWarnings", Boolean.class);
        if (supressAccessorWarnings == null) {
            supressAccessorWarnings = Boolean.valueOf(false);
        }

        Boolean improvedXsiTypeHandling = (Boolean) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.improvedXsiTypeHandling", Boolean.class);
        if (improvedXsiTypeHandling == null) {
            String xmlAccessorFactorySupport = Util.getSystemProperty("com.sun.xml.internal.bind.improvedXsiTypeHandling");
            if (xmlAccessorFactorySupport == null) {
                improvedXsiTypeHandling = Boolean.valueOf(true);
            } else {
                improvedXsiTypeHandling = Boolean.valueOf(xmlAccessorFactorySupport);
            }
        }

        Boolean xmlAccessorFactorySupport1 = (Boolean) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.XmlAccessorFactory", Boolean.class);
        if (xmlAccessorFactorySupport1 == null) {
            xmlAccessorFactorySupport1 = Boolean.valueOf(false);
            Util.getClassLogger().log(Level.FINE, "Property com.sun.xml.internal.bind.XmlAccessorFactoryis not active.  Using JAXB\'s implementation");
        }

        RuntimeAnnotationReader ar = (RuntimeAnnotationReader) getPropertyValue((Map) properties1,
                JAXBRIContext.ANNOTATION_READER, RuntimeAnnotationReader.class);
        Object tr = (Collection) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.typeReferences", Collection.class);
        if (tr == null) {
            tr = Collections.emptyList();
        }

        Map subclassReplacements;
        try {
            subclassReplacements = TypeCast.checkedCast((Map) getPropertyValue((Map) properties1, "com.sun.xml.internal.bind.subclassReplacements", Map.class), Class.class, Class.class);
        } catch (ClassCastException var14) {
            throw new JAXBException(Messages.INVALID_TYPE_IN_MAP.format(new Object[0]), var14);
        }

        if (!((Map) properties1).isEmpty()) {
            throw new JAXBException(Messages.UNSUPPORTED_PROPERTY.format(new Object[]{((Map) properties1).keySet().iterator().next()}));
        } else {
            JAXBContextImpl.JAXBContextBuilder builder = new JAXBContextImpl.JAXBContextBuilder();
            builder.setClasses(classes);
            builder.setTypeRefs((Collection) tr);
            builder.setSubclassReplacements(subclassReplacements);
            builder.setDefaultNsUri(defaultNsUri);
            builder.setC14NSupport(c14nSupport.booleanValue());
            builder.setAnnotationReader(ar);
            builder.setXmlAccessorFactorySupport(xmlAccessorFactorySupport1.booleanValue());
            builder.setAllNillable(allNillable.booleanValue());
            builder.setRetainPropertyInfo(retainPropertyInfo.booleanValue());
            builder.setSupressAccessorWarnings(supressAccessorWarnings.booleanValue());
            builder.setImprovedXsiTypeHandling(improvedXsiTypeHandling.booleanValue());
            builder.setDisableSecurityProcessing(disablesecurityProcessing.booleanValue());
            return builder.build();
        }
    }

    private static <T> T getPropertyValue(Map<String, Object> properties, String keyName, Class<T> type) throws JAXBException {
        Object o = properties.get(keyName);
        if (o == null) {
            return null;
        } else {
            properties.remove(keyName);
            if (!type.isInstance(o)) {
                throw new JAXBException(Messages.INVALID_PROPERTY_VALUE.format(new Object[]{keyName, o}));
            } else {
                return type.cast(o);
            }
        }
    }
}