package com.jikezhiji.survey.domain.service;

import com.jikezhiji.survey.domain.Question;
import com.jikezhiji.survey.domain.Survey;
import com.jikezhiji.survey.domain.SurveySetting;
import com.jikezhiji.survey.domain.repository.SurveyRepository;
import com.jikezhiji.survey.domain.repository.SurveySettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by E355 on 2017/1/1.
 */
public interface SurveyService {
    Survey createSurvey(Survey survey);
    Survey updateSurvey(Survey survey);
    Survey activeSurvey(Long surveyId);
    Survey closeSurvey(Long surveyId);
    Survey settingSurvey(SurveySetting setting);

    Question addQuestion(Question question);
    Question updateQuestion(Question question);
    Question adjustQuestionOrder(Long questionId,int sequence);
    void deleteQuestion(Long questionId);
}
