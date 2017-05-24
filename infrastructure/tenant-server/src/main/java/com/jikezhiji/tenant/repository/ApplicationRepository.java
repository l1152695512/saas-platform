package com.jikezhiji.tenant.repository;

import com.jikezhiji.tenant.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by liusizuo on 2017/5/6.
 */
public interface ApplicationRepository extends JpaRepository<Application,String> {

    List<Application> findByIdNotIn(Collection<String> id);
}
