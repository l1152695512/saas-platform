package com.jikezhiji.survey.domain;


import com.jikezhiji.commons.domain.entity.JacksonSerializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "SURVEY_ACCESS_TOKEN")
public class SurveyAccessToken implements JacksonSerializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TOKEN_ID",length = 32)
	private String tokenId;

	/**
	 * 调查Id
	 */
	@Column(name="SURVEY_ID")
	private Long surveyId;

	/**
	 * 是否列入黑名单
	 */
	@Column(name = "BLACKLISTED")
	private boolean blacklisted;

	/**
	 * 是否完成
	 */
	@Column(name = "COMPLETED")
	private boolean completed;

	/**
	 * 可用次数
	 */
	@Column(name = "AVAILABLE_LIMIT")
	private int availableLimit;

	/**
	 * 有效期起始时间
	 */
	@Column(name = "START_TIME")
	private Date startTime;

	/**
	 * 有效期截止时间
	 */
	@Column(name = "END_TIME")
	private Date endTime;



	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public boolean isBlacklisted() {
		return blacklisted;
	}

	public void setBlacklisted(boolean blacklisted) {
		this.blacklisted = blacklisted;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public int getAvailableLimit() {
		return availableLimit;
	}

	public void setAvailableLimit(int availableLimit) {
		this.availableLimit = availableLimit;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Boolean isEffective(){
		long current = System.currentTimeMillis();
		return !completed && !blacklisted && (startTime.getTime() <= current && endTime.getTime() >= current);
	}
}
