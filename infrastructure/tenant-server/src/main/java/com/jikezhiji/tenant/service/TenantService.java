package com.jikezhiji.tenant.service;

import com.jikezhiji.tenant.domain.Tenant;
import com.jikezhiji.tenant.enumeration.EntityStatus;
import com.jikezhiji.tenant.repository.TenantApplicationRepository;
import com.jikezhiji.tenant.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liusizuo on 2017/5/8.
 */
@Service
@Transactional
public class TenantService {

    private final TenantRepository repository;
    private final TenantApplicationRepository tenantApplicationRepository;
    @Autowired
    public TenantService(TenantRepository repository,TenantApplicationRepository tenantApplicationRepository) {
        this.repository = repository;
        this.tenantApplicationRepository = tenantApplicationRepository;
    }

    @Transactional(readOnly = true)
    public List<Tenant> getAllTenants(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Tenant getTenantById(String tenantId){
        return repository.findOne(tenantId);
    }
    public Tenant saveOrUpdateTenant(Tenant tenant){
        if(tenant.getStatus() == null) {
            tenant.setStatus(EntityStatus.AVAILABLE);
        }
        return repository.save(tenant);
    }

    public void disable(String id) {
        repository.updateStatus(id, EntityStatus.UNAVAILABLE);
        tenantApplicationRepository.updateStatusByTenantId(id,EntityStatus.UNAVAILABLE);
    }

    public void enable(String id) {
        repository.updateStatus(id, EntityStatus.AVAILABLE);
        tenantApplicationRepository.updateStatusByTenantId(id,EntityStatus.AVAILABLE);
    }
}
