package com.jikezhiji.rest.provider;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.internal.TemplateHelper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

/**
 * Created by E355 on 2016/9/9.
 */
@Provider
public class JerseyTemplateWriterInterceptor implements WriterInterceptor {
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        MediaType mediaType = context.getMediaType();
        if(mediaType.getSubtype().contains("html")) {
            final Template template = TemplateHelper.getTemplateAnnotation(context.getAnnotations());
            final Object entity = context.getEntity();

            if (template != null && !(entity instanceof Viewable)) {
                context.setType(Viewable.class);
                context.setEntity(new Viewable(template.name(), entity));
            }
        }
        context.proceed();
    }
}
