package com.jikezhiji.survey.domain;


import com.jikezhiji.survey.domain.id.MemberQuotaId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 配额成员
 * 
 * @author liyongchun
 *
 */
@Entity
@Table(name="MEMBER_QUOTA")
@IdClass(MemberQuotaId.class)
public class MemberQuota implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(targetEntity = Quota.class)
	@JoinColumn(name="QUOTA_ID",foreignKey = @ForeignKey(name="FK_MEMBER_QUOTA_QUOTA_ID"))
	private Long quotaId;

	@Id
	@ManyToOne(targetEntity = Question.class)
	@JoinColumn(name="QUESTION_ID",foreignKey = @ForeignKey(name="FK_MEMBER_QUOTA_QUESTION_ID"))
	private Long questionId;

	@Id
	@Column(name="CODE",length = 32)
	private String code;

	public Long getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(Long quotaId) {
		this.quotaId = quotaId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}