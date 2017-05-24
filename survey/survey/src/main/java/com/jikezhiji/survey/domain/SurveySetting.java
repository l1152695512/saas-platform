package com.jikezhiji.survey.domain;


import com.jikezhiji.survey.domain.enumeration.AccessRule;
import com.jikezhiji.survey.domain.enumeration.SurveyFormat;
import com.jikezhiji.survey.domain.id.SurveySettingId;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="SURVEY_SETTING")
@javax.persistence.Entity
@IdClass(SurveySettingId.class)
public class SurveySetting implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@MapsId("id")
	@OneToOne(targetEntity=Survey.class)
	@JoinColumn(name="SURVEY_ID",foreignKey = @ForeignKey(name="FK_SURVEY_SETTING_SURVEY_ID"))
	private Long surveyId;
	
	/** 外观模版，保留 */
	@Column(name="TEMPLATE",length=32)
	private String template;
	
	/** 是否提交后自动跳转 */
	@Column(name="AUTO_REDIRECT")
	private boolean autoRedirect;
	
	/** 是否显示欢迎信息 */
	@Column(name="SHOW_WELCOME")
	private boolean showWelcome;
	
	/** 是否显示答题进度 */
	@Column(name="SHOW_PROGRESS")
	private boolean showProgress;
	
	/** 是否显示分组信息 */
	@Column(name="SHOW_GROUP_INFO")
	private boolean showGroupInfo;
	
	/** 是否显示问题序号 */
	@Column(name="SHOW_QUESTION_SEQ_NO")
	private boolean showQuestionSeqNo;
	
	/** 是否允许上一步 */
	@Column(name="ALLOW_PREV")
	private boolean allowPrev;

	/** 是否允许中断 */
	@Column(name="ALLOW_SUSPEND")
	private boolean allowSuspend;
	
	/** 是否允许完成后修改 */
	@Column(name="ALLOW_EDIT_AFTER_COMPLETION")
	private boolean allowEditAfterCompletion;
	
	/** 展示格式 */
	@Enumerated(EnumType.STRING)
	@Column(name="FORMAT",length = 32)
	private SurveyFormat format = SurveyFormat.QUESTION_BY_QUESTION;

	/** 默认的发布语言 */
	@Column(name="LOCALE",length = 32)
	private String locale;
	
	/** 参与份数限制 */
	@Column(name="RESPONSE_LIMIT")
	private int responseLimit;
	
	/** 访问权限 */
	@Enumerated(EnumType.STRING)
	@Column(name="ACCESS_RULE",length = 32)
	private AccessRule accessRule = AccessRule.PUBLIC;
	
	/** 是否公开答题序号 */
	@Column(name="OPEN_QUESTION_SEQ_NO")
	private boolean openQuestionSeqNo;
	
	/** 是否公开结果 */
	@Column(name="OPEN_STATISTICS")
	private boolean openStatistics;
	
	/** 是否使用验证码，在提交时 */
	@Column(name="USE_CAPTCHA")
	private boolean useCaptcha;
	
	/** 是否启用评价模式 */
	@Column(name="ENABLE_ASSESSMENT")
	private boolean enableAssessment;

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
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

	public boolean getShowQuestionSeqNo() {
		return showQuestionSeqNo;
	}

	public void setShowQuestionSeqNo(boolean showQuestionSeqNo) {
		this.showQuestionSeqNo = showQuestionSeqNo;
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

	public SurveyFormat getFormat() {
		return format;
	}

	public void setFormat(SurveyFormat format) {
		this.format = format;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getResponseLimit() {
		return responseLimit;
	}

	public void setResponseLimit(int responseLimit) {
		this.responseLimit = responseLimit;
	}

	public AccessRule getAccessRule() {
		return accessRule;
	}

	public void setAccessRule(AccessRule accessRule) {
		this.accessRule = accessRule;
	}

	public boolean isOpenQuestionSeqNo() {
		return openQuestionSeqNo;
	}

	public void setOpenQuestionSeqNo(boolean openQuestionSeqNo) {
		this.openQuestionSeqNo = openQuestionSeqNo;
	}

	public boolean isOpenStatistics() {
		return openStatistics;
	}

	public void setOpenStatistics(boolean openStatistics) {
		this.openStatistics = openStatistics;
	}

	public boolean isUseCaptcha() {
		return useCaptcha;
	}

	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public boolean isEnableAssessment() {
		return enableAssessment;
	}

	public void setEnableAssessment(boolean enableAssessment) {
		this.enableAssessment = enableAssessment;
	}
}
