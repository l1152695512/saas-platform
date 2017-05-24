package com.jikezhiji.survey.domain;


import com.jikezhiji.commons.domain.entity.IdIncrementEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "SURVEY_RESPONSE")
public class SurveyResponse extends IdIncrementEntity {

	@ManyToOne(targetEntity = Survey.class)
	@JoinColumn(name="SURVEY_ID",foreignKey = @ForeignKey(name="FK_SURVEY_RESPONSE_SURVEY_ID"))
	private Long surveyId;

	@Column(name="USER_ID",length = 32)
	private String userId;

	@Column(name="SERVICE_ID",length = 32)
	private String serviceId;

	@Column(name="DEVICE_ID",length = 32)
	private String deviceId;

	@Column(name = "IP_ADDRESS",length = 32)
	private String ipAddress;

	@Column(name = "LAST_QUESTION_ID")
	private Long lastQuestionId;

	/**
	 * 是否提交
	 */
	@Column(name = "SUBMITTED")
	private Boolean submitted;

	/**
	 * 开始时间
	 */
	@Column(name = "START_TIME")
	private Date startTime;

	/**
	 * 提交时间
	 */
	@Column(name = "SUBMIT_TIME")
	private Date submitTime;

	/**
	 * 终止时间（当被逻辑终止时填充该时间）
	 */
	@Column(name = "TERMINATION_TIME")
	private Date terminationTime;

	/**
	 * 参与所花费的时间 interview（采访，访问）
	 */
	@Column(name = "INTERVIEW_TIME")
	private Long interviewTime;

	/**
	 * orphanRemoval: 从当前对象中删除和关联对象的关系时，是否删除关联对象？ 默认不删除
	 * 	orphanRemoval=true //表示会删除关联对象
	 * 		transaction.begin();
	 * 		this.items.remove(item);
	 * 		transaction.commit();  这个操作之后，被关联的对象将会从数据库中删除。
	 * 	orphanRemoval=false 则不会
	 * cascade = CascadeType.REMOVE: 删除当前对象的时候，将会删除关联对象。
	 * @return
	 */
	@OneToMany(mappedBy = "responseId", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ResponseItem> items;


	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}


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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	public Long getLastQuestionId() {
		return lastQuestionId;
	}

	public void setLastQuestionId(Long lastQuestionId) {
		this.lastQuestionId = lastQuestionId;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getTerminationTime() {
		return terminationTime;
	}

	public void setTerminationTime(Date terminationTime) {
		this.terminationTime = terminationTime;
	}

	public Long getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Long interviewTime) {
		this.interviewTime = interviewTime;
	}

	public Set<ResponseItem> getItems() {
		return items;
	}

	public void setItems(Set<ResponseItem> items) {
		this.items = items;
	}

}
