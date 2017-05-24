package com.jikezhiji.autoconfig.refresh.scope;

import com.jikezhiji.autoconfig.SimpleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by E355 on 2016/9/27.
 */
@SpringBootApplication
public class SimpleApplication implements CommandLineRunner {
    @Autowired
    private SimpleProperties dev;

    @Autowired
    private SimpleProperties test;

    @Autowired
    private SimpleProperties testValue;

    @Autowired
    private RefreshScope scope;

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private ConfigurableApplicationContext context;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("dev -> "+dev.getName());
        System.out.println("test -> "+test.getName());
        Map<String,Object> tenantValues = new HashMap<>();
        tenantValues.put("tenant.name","yideti");
        environment.getPropertySources().addFirst(new MapPropertySource("tenantProperties[yideti]",tenantValues));
        context.publishEvent(new EnvironmentChangeEvent(new HashSet<>()));
        System.out.println("test -> "+test.getName());

        System.out.println("testValue -> "+testValue.getName());
        scope.remove("scopedTarget.testValue");
//        this.scope.refreshAll();

        System.out.println("testValue -> "+testValue.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class,args);
    }
}
