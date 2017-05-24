package com.jikezhiji.commons;

import com.jikezhiji.tenant.view.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.annotation.Id;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jikezhiji.commons.DiscoveryConstants.APP_GROUP_NAME;
import static com.jikezhiji.commons.DiscoveryConstants.INFRASTRUCTURE_GROUP;
import static com.jikezhiji.commons.DiscoveryConstants.MASTER;

/**
 * Created by liusizuo on 2017/5/12.
 */
public class DiscoveryClients {
    private static Logger log = LoggerFactory.getLogger(DiscoveryClients.class);
    private static DiscoveryClient client;
    private static RestTemplate template = new RestTemplate();
    static void setDiscoveryClient(DiscoveryClient client) {
        DiscoveryClients.client = client;
    }


    public static List<ServiceInstance> getAllBusinessServiceMasterInstances(){
        final List<ServiceInstance> list = new ArrayList<>();
        client.getServices().forEach(id -> {
            ServiceInstance master = getMasterInstance(id);
            if(isBusinessService(master)) {
                list.add(master);
            }
        });
        return list;
    }

    private static ServiceInstance getMasterInstance(String serviceId) {
        List<ServiceInstance> instances = client.getInstances(serviceId);
        Stream<ServiceInstance> stream = instances.stream();
        Predicate<ServiceInstance> predicate = instance -> "true".equals(instance.getMetadata().get(MASTER));
        if(stream.anyMatch(predicate)) {
            return stream.filter(predicate).collect(Collectors.toList()).get(0);
        } else {
            return instances.size() > 0 ? instances.get(0) : null;
        }
    }


    private static boolean isBusinessService(ServiceInstance masterInstance){
        return masterInstance != null && !INFRASTRUCTURE_GROUP.equals(masterInstance.getMetadata().get(APP_GROUP_NAME));
    }


    public static ActionResult<Map<String,Object>> openApplicationForTenant(TenantInformation o,String serviceId){
        try{
            ServiceInstance instance = getMasterInstance(serviceId);
            Assert.notNull(instance, "[Assertion failed] - this argument is required; it must not be null");
            String uri  = instance.getUri()+"/tenants/"+o.getCode();
            RequestEntity<TenantInformation> entity = RequestEntity.post(URI.create(uri)).contentType(MediaType.APPLICATION_JSON).body(o);
            ResponseEntity<ActionResult<Map<String,Object>>> response = template.exchange(entity,
                    new ParameterizedTypeReference<ActionResult<Map<String,Object>>>(){});
            if(response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("为租户("+o.getCode()+")开通服务("+serviceId+")失败");
        }
        return ActionResult.fail("为租户("+o.getCode()+")开通服务("+serviceId+")失败");
    }

    public static Map<String,Object> getRequiredProperties(String serviceId){
        try{
            ServiceInstance instance = getMasterInstance(serviceId);
            Assert.notNull(instance, "[Assertion failed] - this argument is required; it must not be null");
            String uri  = instance.getUri()+"/tenants/required-properties";
            RequestEntity<Void> entity = RequestEntity.get(URI.create(uri)).accept(MediaType.APPLICATION_JSON).build();
            ResponseEntity<Map<String,Object>> response = template.exchange(entity,
                    new ParameterizedTypeReference<Map<String,Object>>(){});
            if(response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("获取服务("+serviceId+")的必须配置项失败");
        }
        return new HashMap<>();
    }
}
