package com.jikezhiji.tenant.service;

import com.jikezhiji.commons.DiscoveryClients;
import com.jikezhiji.commons.TenantInformation;
import com.jikezhiji.tenant.domain.TenantApplication;
import com.jikezhiji.tenant.enumeration.EntityStatus;
import com.jikezhiji.tenant.repository.ApplicationRepository;
import com.jikezhiji.tenant.repository.TenantApplicationRepository;
import com.jikezhiji.tenant.repository.TenantRepository;
import com.jikezhiji.tenant.view.ActionResult;
import com.jikezhiji.tenant.view.TenantApplicationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liusizuo on 2017/5/8.
 */
@Service
@Transactional
public class TenantApplicationService {

    private final TenantApplicationRepository repository;
    private final TenantRepository tenantRepository;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public TenantApplicationService(TenantApplicationRepository repository,
                                    TenantRepository tenantRepository,
                                    ApplicationRepository applicationRepository) {
        this.repository = repository;
        this.tenantRepository = tenantRepository;
        this.applicationRepository = applicationRepository;
    }

    @Transactional(readOnly = true)
    public List<TenantApplicationVo> getListByTenantId(String tenantId){
        TenantApplication tenantApplication = new TenantApplication(tenantId,null);
        return repository.findAll(Example.of(tenantApplication)).stream()
                .map(ta-> new TenantApplicationVo(applicationRepository.getOne(ta.getApplicationId()),ta))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TenantApplicationVo> getListByApplicationId(String applicationId){
        TenantApplication tenantApplication = new TenantApplication(null,applicationId);
        return repository.findAll(Example.of(tenantApplication)).stream()
                .map(ta->new TenantApplicationVo(tenantRepository.getOne(ta.getTenantId()),ta))
                .collect(Collectors.toList());
    }

    public ActionResult<Map<String,Object>> openApplicationForTenant(TenantApplication ta){
        TenantInformation tf = new TenantInformation();
        tf.setCode(ta.getTenantId());
        tf.setName(tenantRepository.findOne(ta.getTenantId()).getName());
        tf.setTenantProperties(ta.getProperties());

        ActionResult<Map<String,Object>> result = DiscoveryClients.openApplicationForTenant(tf,ta.getApplicationId());
        if(result.isSuccess() && ta.getStartTime()!=null) {
            ta.setStatus(EntityStatus.AVAILABLE);
            repository.save(ta);
        }
        return result;
    }

    public void enable(String tenantId,String applicationId) {
        /*
          这里不是很严谨，在极端情况下，执行完64行和未执行65行中的某个时间片段可能会混入一个更新租户状态为UNAVAILABLE的操作，如果发生这种情况则这行判断代码没有意义。
          不过租户管理的更新操作应该是非常非常的少，所以在这种业务背景下几乎可以说是不可能出现。
         */
        if(tenantRepository.findOne(tenantId).getStatus() == EntityStatus.AVAILABLE){
            repository.updateStatus(tenantId,applicationId, EntityStatus.AVAILABLE);
        }
    }

    public void disable(String tenantId,String applicationId) {
        repository.updateStatus(tenantId,applicationId, EntityStatus.UNAVAILABLE);
    }
}
