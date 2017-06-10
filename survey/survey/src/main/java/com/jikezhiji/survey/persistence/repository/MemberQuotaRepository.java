package com.jikezhiji.survey.persistence.repository;

import com.jikezhiji.survey.domain.MemberQuota;
import com.jikezhiji.survey.domain.embedded.MemberQuotaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by E355 on 2016/11/27.
 */
@RepositoryRestResource(exported = false)
public interface MemberQuotaRepository extends JpaRepository<MemberQuota,MemberQuotaId>{
}
