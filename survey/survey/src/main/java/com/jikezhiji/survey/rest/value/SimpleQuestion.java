package com.jikezhiji.survey.rest.value;

import com.jikezhiji.survey.domain.Question;
import com.jikezhiji.survey.domain.embedded.QuestionLogic;
import com.jikezhiji.survey.domain.embedded.QuestionType;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;
import java.util.Map;

/**
 * Created by liusizuo on 2017/5/26.
 */
@Projection(name = "SimpleQuestion", types = { Question.class })
public interface SimpleQuestion {
    Long getSurveyId();

    Long getParentId();

    String getCode();

    QuestionType getType();

    String getTitle();

    String getHelp();

    String getImage();

    int getIndex();

    boolean isMandatory();

    String getDefaultAnswer();

    Map<String, Object> getContent();

    List<QuestionLogic> getJumpLogic();
}
