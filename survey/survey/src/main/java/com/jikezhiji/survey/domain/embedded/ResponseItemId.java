package com.jikezhiji.survey.domain.embedded;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseItemId that = (ResponseItemId) o;

        if (!responseId.equals(that.responseId)) return false;
        return questionId.equals(that.questionId);
    }

    @Override
    public int hashCode() {
        int result = responseId.hashCode();
        result = 31 * result + questionId.hashCode();
        return result;
    }
}
