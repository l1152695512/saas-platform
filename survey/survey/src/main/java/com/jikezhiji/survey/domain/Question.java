package com.jikezhiji.survey.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jikezhiji.commons.domain.converter.SimpleMapConverter;
import com.jikezhiji.commons.domain.entity.IdIncrementEntity;
import com.jikezhiji.survey.persistence.converter.LogicCollectionConverter;
import com.jikezhiji.survey.domain.embedded.QuestionLogic;
import com.jikezhiji.survey.domain.embedded.QuestionType;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@javax.persistence.Entity
@Table(name = "QUESTION",uniqueConstraints = @UniqueConstraint(name="UK_QUESTION_CODE",columnNames = "QUESTION_CODE"))
@Converts({
	@Convert(attributeName="content",converter = SimpleMapConverter.class),
	@Convert(attributeName="jumpLogic",converter = LogicCollectionConverter.class)
})
public class Question extends IdIncrementEntity {

	/**
	 * 调查Id
	 */
	@Column(name = "SURVEY_ID")
	private Long surveyId;

	@ManyToOne(targetEntity = Survey.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "SURVEY_ID",insertable = false,updatable = false,foreignKey = @ForeignKey(name="FK_QUESTION_SURVEY_ID"))
	@RestResource(exported = false)
	@JsonIgnore
	private Survey survey;


	/**
	 * 父问题Id
	 */
	@Column(name = "PARENT_ID")
	private Long parentId;

	/**
	 * 问题的code
	 */
	@Column(name = "QUESTION_CODE",length = 32, unique = true)
	private String code;

	/**
	 * 问题类型
	 */
	@Column(name="QUESTION_TYPE",length = 32)
	@Enumerated(EnumType.STRING)
	private QuestionType type;

	/**
	 * 标题
	 */
	@Column(name = "TITLE")
	private String title;

	/**
	 * 帮助文字
	 */
	@Column(name = "`HELP`",columnDefinition = "TEXT DEFAULT NULL")
	private String help;

	/**
	 * 标题图片
	 */
	@Column(name = "IMAGE",length = 512)
	private String image;

	/**
	 * 问题序号
	 */
	@Column(name = "`INDEX`")
	private int index;

	/**
	 * 是否必须作答
	 */
	@Column(name = "MANDATORY")
	private boolean mandatory;


	/**
	 * 用户未填写答案时候，填入此默认答案
	 */
	@Column(name = "DEFAULT_ANSWER")
	private String defaultAnswer;

	@Column(name = "CONTENT",columnDefinition = "LONGTEXT DEFAULT NULL")
	private Map<String, Object> content;

	@Column(name = "JUMP_LOGIC")
	private List<QuestionLogic> jumpLogic;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getDefaultAnswer() {
		return defaultAnswer;
	}

	public void setDefaultAnswer(String defaultAnswer) {
		this.defaultAnswer = defaultAnswer;
	}

	public Map<String, Object> getContent() {
		return content;
	}

	public void setContent(Map<String, Object> content) {
		this.content = content;
	}

	public List<QuestionLogic> getJumpLogic() {
		return jumpLogic;
	}

	public void setJumpLogic(List<QuestionLogic> jumpLogic) {
		this.jumpLogic = jumpLogic;
	}

	public Question(){

	}
	public Question(Long id) {
		this.setId(id);
	}
	public Question(Long id,QuestionType type) {
		this.setId(id);
		this.type = type;
	}

	public Long runJumpLogic(String value,Question next){
		if(jumpLogic == null || jumpLogic.isEmpty()) {
			return next.getId();
		}
		QuestionLogic questionLogic = jumpLogic.stream().filter(logic -> logic.isMatch(value)).findFirst().orElse(null);
		if(questionLogic == null) {
			return next.getId();
		} else {
			return questionLogic.getTargetQuestionId();
		}
	}

	public boolean validateAnswer(String value) {
		return QuestionType.validate(this,value);
	}
}
