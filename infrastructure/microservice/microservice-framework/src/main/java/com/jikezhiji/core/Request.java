package com.jikezhiji.core;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E355 on 2016/10/12.
 */
public abstract class Request {
    private URI requestURI;
    private LocalDateTime requestTime;
    private String requestThreadId;
    private Map<String,Object> properties = new HashMap<>();
    public Request(URI requestURI) {
        this(requestURI,String.valueOf(Thread.currentThread().getId()));
    }

    public Request(URI requestURI,String requestThreadId) {
        this.requestURI = requestURI;
        this.requestThreadId = requestThreadId;
        this.requestTime = LocalDateTime.now();

        RequestContext.addRequest(this);
    }

    public abstract String getParameter(String key);
    public abstract String getHeader(String key);

    public URI getRequestURI() {
        return requestURI;
    }

    public LocalDateTime getRequestTime(){
        return requestTime;
    }

    public String getRequestThreadId(){
        return requestThreadId;
    }


    public <V> V  getProperty(String key){
        return (V) properties.get(key);
    }

    public void setProperty(String key, Object value) {
        properties.put(key,value);
    }

}
