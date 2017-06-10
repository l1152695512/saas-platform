package com.jikezhiji.survey.rest.value;

import com.jikezhiji.survey.domain.Survey;
import com.jikezhiji.survey.domain.SurveySetting;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by liusizuo on 2017/5/26.
 */
@Projection(name = "detail", types = { Survey.class })
public interface SurveyDetailInfo extends SurveyBasicInfo{
    SurveySetting getSetting();
}
