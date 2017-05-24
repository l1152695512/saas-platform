package com.jikezhiji.eureka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liusizuo on 2017/5/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EurekaApplicationTest.EurekaApplication.class,  value = {"spring.profiles.active=eureka", "debug=true" })
public class EurekaApplicationTest {

    @Test
    public void testStartEureka(){
    }

    @EnableEurekaServer
    @SpringBootApplication
    protected static class EurekaApplication {


    }
}
