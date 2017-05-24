package com.jikezhiji.tenant.repository;

import com.jikezhiji.tenant.domain.TenantApplication;
import com.jikezhiji.tenant.enumeration.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * Created by liusizuo on 2017/5/6.
 */
public interface TenantApplicationRepository extends JpaRepository<TenantApplication,TenantApplication.TenantApplicationPK> {

    @Modifying
    @Query("update TenantApplication set status=:status where tenantId=:tenantId")
    int updateStatusByTenantId(@NotNull @Param("tenantId") String tenantId, @Param("status") EntityStatus status);

    @Modifying
    @Query("update TenantApplication set status=:status where tenantId=:tenantId and applicationId=:applicationId")
    int updateStatus(@NotNull @Param("tenantId") String tenantId, @Param("applicationId") @NotNull String applicationId, @Param("status") EntityStatus status);
}
