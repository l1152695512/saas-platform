package com.jikezhiji.survey.rest.value;

import com.jikezhiji.survey.domain.Question;
import com.jikezhiji.survey.domain.ResponseItem;

/**
 * Created by liusizuo on 2017/6/8.
 */
public class QuestionAndAnswer {
    private Question question;
    private ResponseItem responseItem;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    public QuestionAndAnswer(Question question, ResponseItem responseItem) {
        this.question = question;
        this.responseItem = responseItem;
    }
}
