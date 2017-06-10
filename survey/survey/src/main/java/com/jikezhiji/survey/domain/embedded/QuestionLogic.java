package com.jikezhiji.survey.domain.embedded;


import java.io.Serializable;
import java.util.List;

public class QuestionLogic implements Serializable {


    private Long questionId;

    private QuestionAction action;

    private Long targetQuestionId;

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

    public boolean isMatch(String value){
        return conditions.stream().anyMatch(predicate -> {
            switch (predicate.getOperator()) {
                case EQUAL:
                        return value.equals(predicate.value);
                case CHECKED:
                        return value.equals(predicate.value);
                case UNEQUAL:
                        return !value.equals(predicate.value);
                case UNCHECKED:
                    return !value.equals(predicate.value);
                case CONTAINS:
                    return value.contains(predicate.value);
                case LESS_EQUAL:
                    return value.compareTo(predicate.value) <= 0;
                case LESS_THAN:
                    return value.compareTo(predicate.value) < 0;
                case GREATER_EQUAL:
                    return value.compareTo(predicate.value) >= 0;
                case GREATER_THAN:
                    return value.compareTo(predicate.value) > 0;
                default:
                    return true;
            }
        });
    }
}
