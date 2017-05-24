package com.jikezhiji.rest.feature;

import com.jikezhiji.rest.provider.JerseyTemplateWriterInterceptor;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by E355 on 2016/9/9.
 */
public class IsomorphicFeature implements Feature {
    @Override
    public boolean configure(FeatureContext context) {
        final Configuration config = context.getConfiguration();
        if (!config.isRegistered(FreemarkerMvcFeature.class)) {
            context.register(FreemarkerMvcFeature.class);
            context.register(JerseyTemplateWriterInterceptor.class);
            return true;
        }
        return false;

    }
}
