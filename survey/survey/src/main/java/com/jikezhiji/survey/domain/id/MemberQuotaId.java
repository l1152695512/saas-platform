package com.jikezhiji.survey.domain.id;

import java.io.Serializable;

/**
 * Created by E355 on 2016/12/11.
 */
public class MemberQuotaId implements Serializable {
    private Long quotaId;
    private Long questionId;
    private String code;

    public Long getQuotaId() {
        return quotaId;
    }

    public void setQuotaId(Long quotaId) {
        this.quotaId = quotaId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
