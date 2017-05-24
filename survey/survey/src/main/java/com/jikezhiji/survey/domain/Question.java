package com.jikezhiji.survey.domain;


import com.jikezhiji.commons.domain.entity.IdIncrementEntity;
import com.jikezhiji.commons.domain.converter.SimpleMapConverter;
import com.jikezhiji.survey.domain.enumeration.QuestionType;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@javax.persistence.Entity
@Table(name = "QUESTION")
@Converts({
	@Convert(attributeName="content",converter = SimpleMapConverter.class)
})
public class Question extends IdIncrementEntity {

	/**
	 * 调查Id
	 */
	@ManyToOne(targetEntity = Survey.class)
	@JoinColumn(name = "SURVEY_ID",foreignKey = @ForeignKey(name="FK_QUESTION_SURVEY_ID"))
	private Long surveyId;

	/**
	 * 父问题Id
	 */
	@ManyToOne(targetEntity = Question.class)
	@JoinColumn(name = "PARENT_ID",foreignKey = @ForeignKey(name="FK_QUESTION_PARENT_ID"))
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
	@Column(name = "HELP",columnDefinition = "TEXT DEFAULT NULL")
	private String help;

	/**
	 * 标题图片
	 */
	@Column(name = "IMAGE",length = 512)
	private String image;

	/**
	 * 问题序号
	 */
	@Column(name = "SEQUENCE")
	private int sequence;

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


	@OneToMany(cascade=CascadeType.REMOVE, mappedBy="questionId")
	private List<QuestionLogic> questionLogic;



	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getQuestionCode() {
		return code;
	}

	public void setQuestionCode(String code) {
		this.code = code;
	}

	public QuestionType getQuestionType() {
		return type;
	}

	public void setQuestionType(QuestionType type) {
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

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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

	public List<QuestionLogic> getQuestionLogic() {
		return questionLogic;
	}

	public void setQuestionLogic(List<QuestionLogic> questionLogic) {
		this.questionLogic = questionLogic;
	}
}
