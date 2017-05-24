package com.jikezhiji.survey.domain;


import com.jikezhiji.survey.domain.converter.LogicPredicateCollectionConverter;
import com.jikezhiji.survey.domain.enumeration.LogicOperator;
import com.jikezhiji.survey.domain.enumeration.QuestionAction;
import com.jikezhiji.survey.domain.id.QuestionLogicId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@javax.persistence.Entity
@Table(name="QUESTION_LOGIC")
@IdClass(QuestionLogicId.class)
public class QuestionLogic implements Serializable{


	@Id
	@ManyToOne(targetEntity = Question.class)
	@JoinColumn(name="SOURCE_QUESTION_ID",foreignKey = @ForeignKey(name="FK_QUESTION_LOGIC_SOURCE_QUESTION_ID"))
	private Long questionId;

	@Column(name="ACTION")
	@Enumerated(EnumType.STRING)
	private QuestionAction action;

	@Id
	@ManyToOne(targetEntity = Question.class)
	@JoinColumn(name="TARGET_QUESTION_ID",foreignKey = @ForeignKey(name="FK_QUESTION_LOGIC_TARGET_QUESTION_ID"))
	private Long targetQuestionId;

	@Column(name="CONDITIONS",columnDefinition = "TEXT DEFAULT NULL")
	@Convert(converter=LogicPredicateCollectionConverter.class)
	private List<LogicPredicate> conditions;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public QuestionAction getAction() {
		return action;
	}

	public void setAction(QuestionAction action) {
		this.action = action;
	}

	public Long getTargetQuestionId() {
		return targetQuestionId;
	}

	public void setTargetQuestionId(Long targetQuestionId) {
		this.targetQuestionId = targetQuestionId;
	}

	public List<LogicPredicate> getConditions() {
		return conditions;
	}

	public void setConditions(List<LogicPredicate> conditions) {
		this.conditions = conditions;
	}

	public static class LogicPredicate {

		private LogicOperator operator;

		private String value;

		public LogicOperator getOperator() {
			return operator;
		}

		public void setOperator(LogicOperator operator) {
			this.operator = operator;
		}


		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}

}
