package com.jikezhiji.rest.feature;

import com.jikezhiji.rest.provider.FastJsonMessageConverter;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;
import java.util.Map;

public class FastJsonFeature implements Feature {


    @Override
    public boolean configure(FeatureContext context) {
        Configuration config = context.getConfiguration();
        if(!config.isRegistered(FastJsonFeature.class)) {
            Map<Class<?>, Integer> contracts = new HashMap<>();
            contracts.put(MessageBodyReader.class, Priorities.AUTHENTICATION);
            contracts.put(MessageBodyWriter.class, Priorities.AUTHENTICATION);
            context.register(FastJsonMessageConverter.class, contracts);
        }

        return true;
    }
}