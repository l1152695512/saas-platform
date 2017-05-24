package com.jikezhiji.tenant.resource;

import com.jikezhiji.domain.command.core.Command;
import com.jikezhiji.domain.command.core.AggregateHolder;
import com.jikezhiji.domain.query.QueryRepositorySupport;
import com.jikezhiji.tenant.aggregate.Service;
import com.jikezhiji.tenant.aggregate.Tenant;
import com.jikezhiji.tenant.aggregate.TenantService;
import com.jikezhiji.tenant.aggregate.ServiceMetadata;
import com.jikezhiji.tenant.aggregate.TenantServiceMetadata;
import com.jikezhiji.tenant.resource.value.out.ServiceInfo;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by E355 on 2016/9/19.
 */
@Path("/tenants")
public class TenantResource {

    @Autowired
    private Command command;

    @Autowired
    private QueryRepositorySupport support;


    @GET
    @Path("/")
    public Iterable<Tenant> getTenants(Pageable pageable){
        return support.findAll(Tenant.class,pageable);
    }

    @GET
    @Path("/{tenantId}/services")
    public List<ServiceInfo> getTenants(@PathParam("tenantId") Long tenantId){
        Predicate predicate = TenantServiceMetadata.tenantService.tenantId.eq(tenantId);
        return support.findAll(TenantService.class, predicate).stream()
                .map(this::getServiceInfo).collect(Collectors.toList());
    }

    private ServiceInfo getServiceInfo(TenantService tenantService) {
        Predicate predicate = ServiceMetadata.service.code.eq(tenantService.getServiceId());
        Service service = support.findOne(Service.class, predicate);
        return new ServiceInfo(service,tenantService);
    }

    @POST
    @Path("/")
    public void addTenant(@HeaderParam("commandId")String commandId,
                          @FormParam("code") String code,
                          @FormParam("name") String name,
                          @FormParam("hosts") Set<String> hosts){
        AggregateHolder changeSet = new AggregateHolder(Tenant.class);
        command.execute(commandId,changeSet,(changes) -> new Tenant(changes.getId(),code,name));
    }

    @PUT
    @Path("/{tenantId}")
    public void addTenant(@HeaderParam("commandId")String commandId, @PathParam("tenantId") Long tenantId,
                          @FormParam("name") String name, @FormParam("hosts") Set<String> hosts){
        AggregateHolder changeSet = new AggregateHolder(Tenant.class,tenantId);
        command.execute(commandId,changeSet,(changes) -> changes.<Tenant,Long>getAggregateRoot().updateName(name).resetHosts(hosts));
    }

}
