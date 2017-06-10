package com.jikezhiji.survey.domain;


import com.jikezhiji.commons.domain.entity.JacksonSerializable;
import com.jikezhiji.survey.domain.embedded.MemberQuotaId;

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
public class MemberQuota implements JacksonSerializable {

	private static final long serialVersionUID = 1L;
	@ManyToOne(targetEntity = Quota.class)
	@JoinColumn(name="QUOTA_ID",updatable = false,insertable = false,foreignKey = @ForeignKey(name="FK_MEMBER_QUOTA_QUOTA_ID"))
	private Quota quota;

	@Id
	@Column(name="QUOTA_ID")
	private Long quotaId;

	@Id
	@Column(name="QUESTION_ID",length = 32)
	private Long questionId;

	@Id
	@Column(name="CODE",length = 32)
	private String code;

	public Quota getQuota() {
		return quota;
	}

	public void setQuota(Quota quota) {
		this.quota = quota;
	}

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