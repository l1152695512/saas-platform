package com.jikezhiji.survey.persistence.repository;

import com.jikezhiji.survey.domain.Survey;
import com.jikezhiji.survey.persistence.repository.ext.SurveyRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by E355 on 2016/11/27.
 */
@RepositoryRestResource
public interface SurveyRepository extends JpaRepository<Survey,Long> ,SurveyRepositoryCustom {


    @Override
    @RestResource(exported = false)
    void delete(Long id);

    @Override
    Survey save(Survey entity);

    @Override
    Survey findOne(Long id);

}

