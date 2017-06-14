package com.jikezhiji.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jikezhiji.commons.domain.entity.IdIncrementEntity;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="QUOTA")
public class Quota extends IdIncrementEntity {

	/** 表单id */
	@Column(name="SURVEY_ID")
	private Long surveyId;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SURVEY_ID",insertable = false,updatable = false,foreignKey = @ForeignKey(name="FK_QUOTA_SURVEY_ID"))
	@RestResource(exported = false)
	@JsonIgnore
	private Survey survey;

	@Column(name="NAME")
	private String name;
	/**
	 * 限制数量
	 */
	@Column(name="QUANTITY")
	private int quantity;

	@Column(name="ACTIVE")
	private boolean active;

	@Column(name="MESSAGE",columnDefinition = "TEXT DEFAULT NULL")
	private String message;

	@Column(name="AUTO_LOAD_URL")
	private boolean autoLoadUrl;

	@Column(name="URL",length = 512)
	private String url;

	@Column(name="URL_DESCRIPTION")
	private String urlDescription;

	@OneToMany(mappedBy="quota",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<MemberQuota> members = new HashSet<>();

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAutoLoadUrl() {
		return autoLoadUrl;
	}

	public void setAutoLoadUrl(boolean autoLoadUrl) {
		this.autoLoadUrl = autoLoadUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlDescription() {
		return urlDescription;
	}

	public void setUrlDescription(String urlDescription) {
		this.urlDescription = urlDescription;
	}


	public Set<MemberQuota> getMembers() {
		return members;
	}

	public void setMembers(Set<MemberQuota> members) {
		this.members = members;
	}

	public Quota(){

	}

	public Quota(Long surveyId) {
		this.surveyId = surveyId;
	}
}