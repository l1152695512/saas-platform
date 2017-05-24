package com.jikezhiji.tenant.resource.value.in;

import javax.ws.rs.FormParam;

/**
 * Created by Administrator on 2016/10/25.
 */
public class TenantProperty {
    @FormParam("key")
    private String key;

    @FormParam("value")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
