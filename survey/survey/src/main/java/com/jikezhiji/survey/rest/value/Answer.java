package com.jikezhiji.survey.rest.value;

/**
 * Created by liusizuo on 2017/6/10.
 */
public class Answer {
    private Long questionId;
    private Long interviewSecs;
    private String value;//答案

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getInterviewSecs() {
        return interviewSecs;
    }

    public void setInterviewSecs(Long interviewSecs) {
        this.interviewSecs = interviewSecs;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
