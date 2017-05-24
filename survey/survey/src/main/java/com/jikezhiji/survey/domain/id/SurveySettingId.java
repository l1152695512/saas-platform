package com.jikezhiji.survey.domain.id;

import java.io.Serializable;

/**
 * Created by liusizuo on 2017/5/24.
 */
public class SurveySettingId implements Serializable {
    private Long surveyId;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }
}
