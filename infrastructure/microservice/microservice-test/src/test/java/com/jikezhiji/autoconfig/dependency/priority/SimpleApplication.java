package com.jikezhiji.autoconfig.dependency.priority;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by E355 on 2016/9/27.
 */
@SpringBootApplication
public class SimpleApplication implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SimpleApplication.class,args);
        Object o = ctx.getBean("dev1");
        System.out.println(o);
    }
}
