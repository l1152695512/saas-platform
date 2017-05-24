package com.jikezhiji.rest.provider;

import javax.ws.rs.*;
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by E355 on 2016/9/12.
 */
@Provider
public class SimpleExceptionMapper implements ExceptionMapper<Exception>{

    @Override
    public Response toResponse(Exception exception) {
        if(exception instanceof ClientErrorException) {
            if(exception instanceof BadRequestException) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else if(exception instanceof NotAcceptableException) {

            } else if(exception instanceof NotFoundException){

            } else if(exception instanceof NotSupportedException){

            } else if(exception instanceof NotAllowedException){

            } else if(exception instanceof NotAuthorizedException){

            } else if(exception instanceof ForbiddenException){

            }
        } else if(exception instanceof ServerErrorException) {
            if( exception instanceof InternalServerErrorException) {

            } else if(exception instanceof ServiceUnavailableException) {

            }
        } else if(exception instanceof RedirectionException) {

        } else if(exception instanceof  ProcessingException) {

        } else if(exception instanceof UriBuilderException) {

        } else if(exception instanceof NoContentException) {

        }
        return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
    }
}
