package com.jikezhiji.tenant.repository;

import com.jikezhiji.tenant.domain.ApplicationDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by liusizuo on 2017/5/6.
 */
public interface ApplicationDependencyRepository extends JpaRepository<ApplicationDependency,ApplicationDependency.ApplicationDependencyPK>{
    int deleteByApplicationId(String applicationId);
}
