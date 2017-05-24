package com.jikezhiji.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.IOException;

/**
 * Created by E355 on 2016/9/9.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TenantBootApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TenantBootApplication.class,args);
    }
}
