package com.jikezhiji.survey.rest.value;

import com.jikezhiji.survey.domain.Question;
import com.jikezhiji.survey.domain.Quota;
import com.jikezhiji.survey.domain.Survey;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * Created by liusizuo on 2017/5/27.
 */
@Projection(name = "full", types = { Survey.class })
public interface SurveyFullInfo extends SurveyDetailInfo{
    List<Quota> getQuotas();
    List<Question> getQuestions();
}
