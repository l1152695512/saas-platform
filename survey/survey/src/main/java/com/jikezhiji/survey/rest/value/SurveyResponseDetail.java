package com.jikezhiji.survey.rest.value;

import com.jikezhiji.survey.domain.SurveyResponse;

import java.util.List;

/**
 * Created by liusizuo on 2017/6/8.
 */
public class SurveyResponseDetail
{
    private SurveyResponse response;
    private List<QuestionAndAnswer> questionAndAnswers;

    public SurveyResponse getResponse() {
        return response;
    }

    public void setResponse(SurveyResponse response) {
        this.response = response;
    }

    public List<QuestionAndAnswer> getQuestionAndAnswers() {
        return questionAndAnswers;
    }

    public void setQuestionAndAnswers(List<QuestionAndAnswer> questionAndAnswers) {
        this.questionAndAnswers = questionAndAnswers;
    }
}
