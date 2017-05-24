package com.jikezhiji.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by E355 on 2016/9/27.
 */
@ConfigurationProperties("core")
public class SimpleProperties {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void destroySP(){
        System.out.println("invoke destroySP()");
    }

    public void initSP(){
        System.out.println(name +" -> invoke initSP()");
    }
}
