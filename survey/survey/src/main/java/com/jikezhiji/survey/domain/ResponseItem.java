package com.jikezhiji.survey.domain;

import com.jikezhiji.survey.domain.id.ResponseItemId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESPONSE_ITEM")
@IdClass(ResponseItemId.class)
public class ResponseItem implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(targetEntity = SurveyResponse.class)
	@JoinColumn(name = "RESPONSE_ID",foreignKey = @ForeignKey(name = "FK_RESPONSE_ITEM_RESPONSE_ID"))
	private Long responseId;

	@Id
	@Column(name = "QUESTION_ID")
	private Long questionId;

	@Column(name = "CODE",length = 512)
	private String code;

	@Column(name = "SUBMIT_TIME")
	private Date submitTime;

	@Column(name = "INTERVIEW_TIME")
	private Long interviewTime;

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


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Long getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Long interviewTime) {
		this.interviewTime = interviewTime;
	}
}