package com.jikezhiji.tenant.resource;

import com.jikezhiji.core.structure.Property;
import com.jikezhiji.domain.command.core.Command;
import com.jikezhiji.domain.query.QueryRepositorySupport;
import com.jikezhiji.tenant.TenantInformation;
import com.jikezhiji.tenant.TenantType;
import com.jikezhiji.tenant.aggregate.*;
import com.jikezhiji.tenant.scope.annotation.ENABLE;
import com.jikezhiji.tenant.resource.value.in.TenantProperty;
import com.jikezhiji.tenant.resource.value.out.TenantInfo;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by E355 on 2016/9/19.
 */
@Path("services")
public class ServiceResource {

    @Autowired
    private Command command;

    @Autowired
    private QueryRepositorySupport support;

    @Autowired
    private DiscoveryClient client;

    private RestTemplate template = new RestTemplate();

    @GET
    public List<Service> getServices(){
        List<String> services = client.getServices();
        return services.stream()
                .map(serviceId -> client.getInstances(serviceId))
                .filter(instances -> instances.get(0)
                        .getMetadata().get("tenantType").equals(TenantType.MULTI.name()))
                .map(instances -> {
                    Service service = new Service();
                    ServiceInstance instance = instances.get(0);
                    Map<String, String> map = instance.getMetadata();
                    service.setCode(instance.getServiceId());
                    service.setName(map.get("name"));
                    service.setDescription(map.get("description"));
                    service.setApiEndpoint(map.get("apiEndpoint"));
                    service.setInitTenantEndpoint(map.get("initTenantEndpoint"));
                    return service;
                })
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{serviceId}/tenants")
    public List<TenantInfo> getTenants(@PathParam("serviceId") String serviceId){
        Predicate predicate = TenantServiceMetadata.tenantService.serviceId.eq(serviceId);
        List<TenantService> tenantServices = support.findAll(TenantService.class,predicate);

        return tenantServices.stream().map(this::getTenantInfo).collect(Collectors.toList());
    }

    private TenantInfo getTenantInfo(TenantService tenantService) {
        Tenant service = support.findById(Tenant.class, tenantService.getTenantId());
        return new TenantInfo(service,tenantService);
    }

    @POST
    @Path("/{serviceId}/properties")
    public Map<String,Object> properties(@PathParam("serviceId") String serviceId){
        ServiceInstance instance = getInstance(serviceId,0);
        if(instance.getMetadata().get("tenantType").equals(TenantType.MULTI.name())) {
            String uri = instance.getUri().toString();
            if(uri.trim().endsWith("/")) {
                return template.getForObject(uri+instance.getMetadata().get("propertiesEndpoint"),Map.class);
            } else {
                return template.getForObject(uri +"/" + instance.getMetadata().get("propertiesEndpoint"),Map.class);
            }
        }
        throw new NotSupportedException("当前服务 ->"+serviceId+"不支持多租户");
    }


    @ENABLE
    @Path("/{serviceId}/{tenantId}")
    public void enable(@HeaderParam("commandId") String commandId,@PathParam("serviceId") String serviceId,
                       @PathParam("tenantId") Long tenantId,
                       @BeanParam Set<TenantProperty> properties){
        TenantInformation info = new TenantInformation();
        Tenant tenant = support.findOne(Tenant.class, TenantMetadata.tenant.id.eq(tenantId));
        info.setName(tenant.getName());
        info.setCode(tenant.getCode());
        info.setHosts(tenant.getHosts());
        info.setTenantProperties(properties.stream().map(tp -> new Property<>(
                tp.getKey(),tp.getValue()
        )).collect(Collectors.toSet()));

        ServiceInstance instance = getInstance(serviceId,0);
        if(instance.getMetadata().get("tenantType").equals(TenantType.MULTI.name())) {
            String uri = instance.getUri().toString();
            if(uri.trim().endsWith("/")) {
                template.postForEntity(uri + instance.getMetadata().get("initTenantEndpoint"),info,Set.class);
            } else {
                template.postForEntity(uri + "/" + instance.getMetadata().get("initTenantEndpoint"),info,Set.class);
            }
        }
    }


    private ServiceInstance getInstance(String serviceId,int index) {
        List<ServiceInstance> instances = client.getInstances(serviceId);
        return instances.get(index);
    }

}
