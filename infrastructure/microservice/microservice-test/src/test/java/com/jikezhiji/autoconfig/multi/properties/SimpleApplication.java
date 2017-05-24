package com.jikezhiji.autoconfig.multi.properties;

import com.jikezhiji.autoconfig.SimpleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.java2d.pipe.SpanShapeRenderer;

/**
 * Created by E355 on 2016/9/27.
 */
@SpringBootApplication
public class SimpleApplication implements CommandLineRunner {
    @Autowired
    private SimpleProperties dev;

    @Autowired
    private SimpleProperties test;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("dev -> "+dev.getName());
        System.out.println("test -> "+test.getName());
    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class,args);
    }
}
