package com.jikezhiji.core;


import java.util.Collection;
import java.util.Map;

/**
 * Created by E355 on 2016/9/29.
 */
public class Tenant {
    private static final String CODE_KEY = "code";
    private static final String NAME_KEY = "name";
    private static final String HOSTS_KEY = "hosts";
    private String keyPrefix;
    private final Map<String,Object> values;

    public Tenant(String keyPrefix,Map<String,Object> values) {
        this.keyPrefix = keyPrefix;
        this.values = values;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public String getCode(){
        return (String)getValue(keyPrefix+CODE_KEY);
    }

    public String getName(){
        return (String)getValue(keyPrefix+NAME_KEY);
    }

    public boolean containsHost(String host){
        Collection<?> hosts = (Collection<?>)getValue(keyPrefix+HOSTS_KEY);
        return hosts.contains(host);
    }

    public Object getValue(String key){
        return values.get(key);
    }


    public Map<String, Object> getValues() {
        return values;
    }


}