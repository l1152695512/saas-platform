package com.jikezhiji.tenant.repository;

import com.jikezhiji.tenant.domain.Tenant;
import com.jikezhiji.tenant.enumeration.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by liusizuo on 2017/5/6.
 */
public interface TenantRepository extends JpaRepository<Tenant,String> {

    @Modifying
    @Query("update Tenant set status=?2 where id=?1")
    int updateStatus(String id, EntityStatus status);

}
