package com.jikezhiji.rest;

import com.jikezhiji.core.Request;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;
import java.util.List;


/**
 * Created by E355 on 2016/9/14.
 */
public class RestWebServiceRequest extends Request {


    private ContainerRequestContext nativeRequest;
    public RestWebServiceRequest(ContainerRequestContext nativeRequest) {
        super(nativeRequest.getUriInfo().getRequestUri());
        this.nativeRequest = nativeRequest;
    }
    public String getParameter(String key) {
        UriInfo info = nativeRequest.getUriInfo();
        if(info.getQueryParameters().containsKey(key)) {
            return info.getPathParameters().getFirst(key);
        } else if(info.getPathSegments() != null && !info.getPathSegments().isEmpty()){
            List<PathSegment> segments = info.getPathSegments();
            for (PathSegment segment : segments) {
                if(segment.getMatrixParameters().containsKey(key)) {
                    return segment.getMatrixParameters().getFirst(key);
                }
            }
        } else if(nativeRequest.getHeaders().containsKey(key)) {
            return getHeader(key);
        } else if(nativeRequest.getCookies().containsKey(key)) {
            return nativeRequest.getCookies().get(key).getValue();
        }
        return getProperty(key);
    }

    @Override
    public String getHeader(String key) {
        return nativeRequest.getHeaderString(key);
    }

}
