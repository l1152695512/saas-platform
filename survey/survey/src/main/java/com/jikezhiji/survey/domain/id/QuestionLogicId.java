package com.jikezhiji.survey.domain.id;

import java.io.Serializable;

/**
 * Created by E355 on 2016/12/11.
 */
public class QuestionLogicId implements Serializable{
    private Long questionId;
    private Long targetQuestionId;

    public Long getQuestion() {
        return questionId;
    }

    public void setQuestion(Long questionId) {
        this.questionId = questionId;
    }

    public Long getTargetQuestionId() {
        return targetQuestionId;
    }

    public void setTargetQuestionId(Long targetQuestionId) {
        this.targetQuestionId = targetQuestionId;
    }
}
