package com.jikezhiji.survey.domain.id;

import java.io.Serializable;

/**
 * Created by E355 on 2016/12/11.
 */
public class ResponseItemId implements Serializable{

    private Long responseId;
    private Long questionId;

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
