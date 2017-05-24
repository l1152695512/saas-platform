package com.jikezhiji.tenant.web;

import com.jikezhiji.tenant.domain.Tenant;
import com.jikezhiji.tenant.domain.TenantApplication;
import com.jikezhiji.tenant.service.TenantApplicationService;
import com.jikezhiji.tenant.service.TenantService;
import com.jikezhiji.tenant.view.ActionResult;
import com.jikezhiji.tenant.view.TenantApplicationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by liusizuo on 2017/5/10.
 */
@RestController
public class TenantResource {

    private final TenantApplicationService tenantApplicationService;
    private final TenantService tenantService;

    @Autowired
    public TenantResource(TenantApplicationService tenantApplicationService, TenantService tenantService) {
        this.tenantApplicationService = tenantApplicationService;
        this.tenantService = tenantService;
    }


    @GetMapping("/tenants")
    public List<Tenant> getTenants(){
        return tenantService.getAllTenants();
    }

    @GetMapping("/tenants/{id}/applications")
    public List<TenantApplicationVo> getListByTenantId(@PathVariable("id") String id){
        return tenantApplicationService.getListByTenantId(id);
    }


    @PostMapping("/tenants/{id}")
    public Tenant addOrUpdate(@PathVariable("id") String id, @RequestBody Tenant tenant){
        tenant.setId(id);
        return tenantService.saveOrUpdateTenant(tenant);
    }

    @PatchMapping("/tenants/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void addOrUpdate(@PathVariable("id") String id,  @RequestParam("action") String action){
        if(action.equalsIgnoreCase("enable")) {
            tenantService.enable(id);
        } else if(action.equalsIgnoreCase("disable")) {
            tenantService.disable(id);
        }
    }

    @PatchMapping("/tenants/{tenantId}/applications/{applicationId}")
    @ResponseStatus(HttpStatus.OK)
    public void addOrUpdate(@PathVariable("tenantId") String tenantId,
                            @PathVariable("applicationId") String applicationId,
                            @RequestParam("action") String action){
        if(action.equalsIgnoreCase("enable")) {
            tenantApplicationService.enable(tenantId,applicationId);
        } else if(action.equalsIgnoreCase("disable")) {
            tenantApplicationService.disable(tenantId,applicationId);
        }
    }

    @PostMapping("/tenants/{tenantId}/applications/{applicationId}")
    public ActionResult<Map<String,Object>> addApplication(
            @PathVariable("tenantId") String tenantId,
            @PathVariable("applicationId") String applicationId,
            @RequestBody TenantApplication ta){
        ta.setApplicationId(applicationId);
        ta.setTenantId(tenantId);
        return tenantApplicationService.openApplicationForTenant(ta);
    }

}
