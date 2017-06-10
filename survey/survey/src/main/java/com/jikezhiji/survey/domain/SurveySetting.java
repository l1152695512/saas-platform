package com.jikezhiji.survey.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jikezhiji.commons.domain.entity.JacksonSerializable;
import com.jikezhiji.survey.persistence.converter.LocaleConverter;
import com.jikezhiji.survey.domain.embedded.AccessRule;
import com.jikezhiji.survey.domain.embedded.SurveyFormat;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Locale;

@Table(name="SURVEY_SETTING")
@javax.persistence.Entity
public class SurveySetting implements JacksonSerializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SURVEY_ID")
	private Long surveyId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SURVEY_ID", foreignKey = @ForeignKey(name = "FK_SURVEY_SETTING_SURVEY_ID"))
	@RestResource(exported = false)
	@JsonIgnore
	private Survey survey;

	/**
	 * 参与份数限制
	 */
	@Column(name = "RESPONSE_LIMIT")
	private int responseLimit;

	/**
	 * 外观模版，保留
	 */
	@Column(name = "TEMPLATE", length = 32)
	private String template;

	/**
	 * 展示格式
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "FORMAT", length = 32)
	private SurveyFormat format = SurveyFormat.QUESTION_BY_QUESTION;

	/**
	 * 默认的发布语言
	 */
	@Column(name = "LOCALE", length = 32)
	@Convert(converter = LocaleConverter.class)
	private Locale locale = Locale.CHINA;

	/**
	 * 访问权限
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "ACCESS_RULE", length = 32)
	private AccessRule accessRule = AccessRule.PUBLIC;

	/**
	 * 是否提交后自动跳转
	 */
	@Column(name = "AUTO_REDIRECT")
	private boolean autoRedirect = true;

	/**
	 * 是否显示欢迎信息
	 */
	@Column(name = "SHOW_WELCOME")
	private boolean showWelcome = true;

	/**
	 * 是否显示答题进度
	 */
	@Column(name = "SHOW_PROGRESS")
	private boolean showProgress = true;

	/**
	 * 是否显示分组信息
	 */
	@Column(name = "SHOW_GROUP_INFO")
	private boolean showGroupInfo = true;

	/**
	 * 是否显示问题序号
	 */
	@Column(name = "SHOW_QUESTION_INDEX")
	private boolean showQuestionIndex = true;

	/**
	 * 是否公开结果
	 */
	@Column(name = "SHOW_RESPONSE")
	private boolean showResponse;

	/**
	 * 是否允许上一步
	 */
	@Column(name = "ALLOW_PREV")
	private boolean allowPrev;

	/**
	 * 是否允许中断
	 */
	@Column(name = "ALLOW_SUSPEND")
	private boolean allowSuspend;

	/**
	 * 是否允许完成后修改
	 */
	@Column(name = "ALLOW_EDIT_AFTER_COMPLETION")
	private boolean allowEditAfterCompletion;

	/**
	 * 是否使用验证码，在提交时
	 */
	@Column(name = "ENABLE_CAPTCHA")
	private boolean enableCaptcha;

	/**
	 * 是否启用评价模式
	 */
	@Column(name = "ENABLE_ASSESSMENT")
	private boolean enableAssessment;

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

	public int getResponseLimit() {
		return responseLimit;
	}

	public void setResponseLimit(int responseLimit) {
		this.responseLimit = responseLimit;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public SurveyFormat getFormat() {
		return format;
	}

	public void setFormat(SurveyFormat format) {
		this.format = format;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public AccessRule getAccessRule() {
		return accessRule;
	}

	public void setAccessRule(AccessRule accessRule) {
		this.accessRule = accessRule;
	}

	public boolean isAutoRedirect() {
		return autoRedirect;
	}

	public void setAutoRedirect(boolean autoRedirect) {
		this.autoRedirect = autoRedirect;
	}

	public boolean isShowWelcome() {
		return showWelcome;
	}

	public void setShowWelcome(boolean showWelcome) {
		this.showWelcome = showWelcome;
	}

	public boolean isShowProgress() {
		return showProgress;
	}

	public void setShowProgress(boolean showProgress) {
		this.showProgress = showProgress;
	}

	public boolean isShowGroupInfo() {
		return showGroupInfo;
	}


	public void setShowGroupInfo(boolean showGroupInfo) {
		this.showGroupInfo = showGroupInfo;
	}

	public boolean isShowQuestionIndex() {
		return showQuestionIndex;
	}

	public void setShowQuestionIndex(boolean showQuestionIndex) {
		this.showQuestionIndex = showQuestionIndex;
	}

	public boolean isShowResponse() {
		return showResponse;
	}

	public void setShowResponse(boolean showResponse) {
		this.showResponse = showResponse;
	}

	public boolean isAllowPrev() {
		return allowPrev;
	}

	public void setAllowPrev(boolean allowPrev) {
		this.allowPrev = allowPrev;
	}

	public boolean isAllowSuspend() {
		return allowSuspend;
	}

	public void setAllowSuspend(boolean allowSuspend) {
		this.allowSuspend = allowSuspend;
	}

	public boolean isAllowEditAfterCompletion() {
		return allowEditAfterCompletion;
	}

	public void setAllowEditAfterCompletion(boolean allowEditAfterCompletion) {
		this.allowEditAfterCompletion = allowEditAfterCompletion;
	}

	public boolean isEnableCaptcha() {
		return enableCaptcha;
	}

	public void setEnableCaptcha(boolean enableCaptcha) {
		this.enableCaptcha = enableCaptcha;
	}

	public boolean isEnableAssessment() {
		return enableAssessment;
	}

	public void setEnableAssessment(boolean enableAssessment) {
		this.enableAssessment = enableAssessment;
	}
}
