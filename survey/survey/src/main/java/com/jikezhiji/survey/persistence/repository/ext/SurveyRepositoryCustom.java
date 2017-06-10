package com.jikezhiji.survey.persistence.repository.ext;

import com.jikezhiji.survey.domain.Survey;

/**
 * Created by liusizuo on 2017/5/27.
 */
public interface SurveyRepositoryCustom {
    Survey findOne(Long surveyId);

    Survey save(Survey entity);
}
