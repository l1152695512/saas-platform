package com.jikezhiji.survey.persistence.repository;

import com.jikezhiji.survey.domain.SurveyAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by liusizuo on 2017/6/6.
 */
@RepositoryRestResource
public interface SurveyAccessTokenRepository extends JpaRepository<SurveyAccessToken,String> {

}
