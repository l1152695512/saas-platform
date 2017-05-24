package com.jikezhiji.rest.security;

import com.jikezhiji.core.Request;
import com.jikezhiji.core.SubjectContext;
import com.jikezhiji.core.Subject;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by E355 on 2016/9/12.
 */
public class SubjectSecurityContext implements SecurityContext {

    private final Request request;
    private final SubjectFactory<String> factory;
    private Subject subject;

    public SubjectSecurityContext(Request request,SubjectFactory<String> factory){
        this.request = request;
        this.factory = factory;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public Principal getUserPrincipal() {
        if(subject == null) {
            subject = factory.createSubject(request.getHeader(HttpHeaders.AUTHORIZATION));
            SubjectContext.addSubject(subject);
        }
        return subject.getPrimary();
    }



    @Override
    public boolean isUserInRole(String role) {
        return subject.hasRole(role);
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return request.getRequestURI().getScheme();
    }


}
