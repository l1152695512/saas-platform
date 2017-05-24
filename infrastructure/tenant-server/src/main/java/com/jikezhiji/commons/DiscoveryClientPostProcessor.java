package com.jikezhiji.commons;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;

import java.util.List;

/**
 * Created by liusizuo on 2017/5/8.
 */
public class DiscoveryClientPostProcessor implements BeanPostProcessor{
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DiscoveryClient) {
            final DiscoveryClient delegate = (DiscoveryClient)bean;
            DiscoveryClient client = new DiscoveryClient(){
                @Override
                public String description() {
                    return delegate.description();
                }

                @Override
                public ServiceInstance getLocalServiceInstance() {
                    return delegate.getLocalServiceInstance();
                }

                @Override
                public List<ServiceInstance> getInstances(String serviceId) {
                    List<ServiceInstance> instances = delegate.getInstances(serviceId);
                    instances.forEach(instance -> {
                        if(instance instanceof EurekaDiscoveryClient.EurekaServiceInstance) {
                            EurekaDiscoveryClient.EurekaServiceInstance  serviceInstance
                                    = (EurekaDiscoveryClient.EurekaServiceInstance)instance;
                            InstanceInfo info = serviceInstance.getInstanceInfo();
                            serviceInstance.getMetadata().put(DiscoveryConstants.STATUS,info.getStatus().name());
                            serviceInstance.getMetadata().put(DiscoveryConstants.APP_NAME,info.getAppName());
                            serviceInstance.getMetadata().put(DiscoveryConstants.APP_GROUP_NAME,info.getAppGroupName());
                        }
                    });
                    return instances;
                }

                @Override
                public List<String> getServices() {
                    return delegate.getServices();
                }
            };
            DiscoveryClients.setDiscoveryClient(client);
            return client;
        }
        return bean;
    }
}
