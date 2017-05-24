package com.jikezhiji.autoconfig.dependency.priority;

import com.jikezhiji.autoconfig.SimpleProperties;
import com.jikezhiji.autoconfig.multi.properties.SimpleConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.Set;

/**
 * Created by E355 on 2016/9/27.
 */
@Configuration
public class SimpleConfiguration2 implements InitializingBean{

    private SimpleProperties properties;

    private Set<SimpleProperties> prop;

    public SimpleConfiguration2(ObjectProvider<Set<SimpleProperties>> optionsProvider){
        this.prop = optionsProvider.getIfAvailable();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.properties = test1();
    }

    @Bean
    @ConfigurationProperties("core.test")
    public SimpleProperties test1(){
        return new SimpleProperties();
    }

    @Bean
    public SimpleProperties test2(SimpleProperties test1){
        System.out.println("dependency------------------------->"+test1.getName());
        System.out.println("properties------------------------->"+properties.getName());
        System.out.println("prop------------------------->"+prop.size());

        System.out.println(test1 == properties);
        return new SimpleProperties();
    }


}
