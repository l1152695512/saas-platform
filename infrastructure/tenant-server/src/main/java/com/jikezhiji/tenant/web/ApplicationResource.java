package com.jikezhiji.tenant.web;

import com.jikezhiji.commons.DiscoveryClients;
import com.jikezhiji.tenant.domain.Application;
import com.jikezhiji.tenant.domain.TenantApplication;
import com.jikezhiji.tenant.service.ApplicationService;
import com.jikezhiji.tenant.service.TenantApplicationService;
import com.jikezhiji.tenant.service.TenantService;
import com.jikezhiji.tenant.view.TenantApplicationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by liusizuo on 2017/5/10.
 */
@RestController
public class ApplicationResource {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private TenantApplicationService tenantApplicationService;


    @GetMapping("/applications")
    public List<Application> getApplications(){
        return applicationService.getAllApplications();
    }

    @GetMapping("/applications/{id}/tenants")
    public List<TenantApplicationVo> getListByApplicationId(@PathVariable("id") String id){
        return tenantApplicationService.getListByApplicationId(id);
    }
    @GetMapping("/applications/{applicationId}/required-properties")
    public Map<String,Object> getRequiredProperties(@PathVariable("applicationId") String applicationId){
        return DiscoveryClients.getRequiredProperties(applicationId);
    }
}
