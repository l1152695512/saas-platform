package com.jikezhiji.survey.domain;


import com.jikezhiji.commons.domain.entity.IdIncrementEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@javax.persistence.Entity
@Table(name="SURVEY")
public class Survey extends IdIncrementEntity {

	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID",length = 32)
	private String userId;

	@Column(name="SERVICE_ID",length = 32)
	private String serviceId;

	@Column(name="TITLE")
	private String title;

	@Column(name="DESCRIPTION",columnDefinition = "TEXT DEFAULT NULL")
	private String description;

	@Column(name="WELCOME_TEXT",columnDefinition = "TEXT DEFAULT NULL")
	private String welcomeText;

	@Column(name="TERMINATION_TEXT",columnDefinition = "TEXT DEFAULT NULL")
	private String terminationText;

	@Column(name="END_TEXT",columnDefinition = "TEXT DEFAULT NULL")
	private String endText;

	@Column(name="END_URL",length = 512)
	private String endUrl;

	@Column(name="END_URL_DESCRIPTION",columnDefinition = "TEXT DEFAULT NULL")
	private String endUrlDescription;

	@Column(name="ACTIVE")
	private boolean active;

	@Column(name="START_TIME")
	private Date startTime;

	@Column(name="EXPIRY_TIME")
	private Date expiryTime;

	@OneToOne(mappedBy="survey",  optional = false, cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//	@PrimaryKeyJoinColumn(name="SURVEY_ID",referencedColumnName="ID") //这个注解也并没有什么用，加不加都一样
	private SurveySetting setting;

	@Column(name="QUESTION_COUNT")
	private int questionCount;

	@OneToMany(mappedBy="survey",cascade=CascadeType.ALL, orphanRemoval = true)
	private List<Question> questions = new ArrayList<>();


	@OneToMany(cascade=CascadeType.ALL, mappedBy="survey",orphanRemoval = true)
	private List<Quota> quotas = new ArrayList<>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWelcomeText() {
		return welcomeText;
	}

	public void setWelcomeText(String welcomeText) {
		this.welcomeText = welcomeText;
	}

	public String getTerminationText() {
		return terminationText;
	}

	public void setTerminationText(String terminationText) {
		this.terminationText = terminationText;
	}

	public String getEndText() {
		return endText;
	}

	public void setEndText(String endText) {
		this.endText = endText;
	}

	public String getEndUrl() {
		return endUrl;
	}

	public void setEndUrl(String endUrl) {
		this.endUrl = endUrl;
	}

	public String getEndUrlDescription() {
		return endUrlDescription;
	}

	public void setEndUrlDescription(String endUrlDescription) {
		this.endUrlDescription = endUrlDescription;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public SurveySetting getSetting() {
		return setting;
	}

	public void setSetting(SurveySetting setting) {
		this.setting = setting;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Quota> getQuotas() {
		return quotas;
	}

	public void setQuotas(List<Quota> quotas) {
		this.quotas = quotas;
	}

	public Question getQuestion(Long questionId) {
		if(questionId == null) {
			return questions.get(0);
		} else {
			return questions.stream().filter(q->q.getId().equals(questionId)).findFirst().orElse(questions.get(0));
		}
	}

	public 	Question getNextQuestionByIndex(Long questionId){
		for(int i = 0; i < questions.size(); i++) {
			if(questions.get(i).getId().equals(questionId)) {
				if(questions.size() > i + 1) {
					return questions.get(i + 1);
				}
			}
		}
		return questions.get(0);
	}
}
