package com.jikezhiji.survey.persistence.repository;

import com.jikezhiji.survey.domain.SurveySetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="survey-settings",collectionResourceRel = "survey settings")
public interface SurveySettingRepository extends JpaRepository<SurveySetting,Long> {


}

