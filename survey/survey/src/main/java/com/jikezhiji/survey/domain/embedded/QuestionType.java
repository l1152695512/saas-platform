/*
  * Copyright (c) 2009-2013 上海通路快建网络外包服务有限公司 All rights reserved.
 * @(#) QuestionType.java 2013-05-28 19:58
 */

package com.jikezhiji.survey.domain.embedded;


import com.jikezhiji.survey.domain.Question;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.validator.constraints.Email;
import org.springframework.util.PatternMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 表单字段类型。
 *
 * @author E557
 * @version $Id: FieldType.java 81538 2015-05-22 05:35:00Z E557 $
 * @since 1.0
 */
public enum QuestionType  {

	LIST_RADIO("LR", "单选"),
	LIST_DROPDOWN("LD", "下拉", LIST_RADIO),
	SECONDARY_DROPDOWN("SD", "二级下拉", LIST_DROPDOWN),
	MULTIPLE_CHOICE("MC", "多选题", LIST_RADIO),
	
	LONG_FREE_TEXT("LT","多行文字"),
	SHORT_FREE_TEXT("ST","单行文字"),
	
	NUMERICAL_INPUT("NI","数字",SHORT_FREE_TEXT),
	EMAIL_INPUT("EI","邮箱",SHORT_FREE_TEXT),
	WEBSITE_INPUT("WI","网址",SHORT_FREE_TEXT),
	TELEPHONE_INPUT("TI","电话",SHORT_FREE_TEXT),
	CELLPHONE_INPUT("CI","手机",SHORT_FREE_TEXT),
	
	DATE_TIME("DT","日期/时间"),
	RANKING("RK","评分"),
	
	SECTION("SC","分段"),
	TEXT_DISPLAY("TD","文本显示"),
	IMAGE_DISPLAY("ID","图片显示",TEXT_DISPLAY),
	VIDEO_DISPLAY("VD","视频播放",TEXT_DISPLAY),

	FILE_UPLOAD("FU","文件上传"),
	IMAGE_UPLOAD("IU","图片上传",FILE_UPLOAD),
	AUDIO_UPLOAD("AU","音频上传",FILE_UPLOAD),
	
	ARRAY("AY","矩阵"),
	ARRAY_NUMBERS("AN","矩阵数字",ARRAY),
	ARRAY_TEXTS("AT","矩阵文本",ARRAY),
	ARRAY_RADIO("AR","矩阵单选",ARRAY),
	
	GEO("GE", "地理位置"),
	WEB("WB", "WEB页面"),
	ADDRESS("AD", "地址"),
	MICROBLOG("MB", "微博转发");
	final String value;
	final String name;
	final QuestionType parent;

	static {
		Set<String> values = new HashSet<>();
		for (QuestionType type : values()) {
			if (values.contains(type.getValue())) {
				throw new IllegalStateException("Duplicate field type value \"" + type.getValue() + "\"");
			}
			values.add(type.getValue());
		}
	}

	QuestionType(String value, String name) {
		this(value, name, null);
	}

	QuestionType(String value, String name, QuestionType parent) {
		this.value = value;
		this.name = name;
		this.parent = parent;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}


	public boolean isAssignableFrom(QuestionType other) {
		for (QuestionType p = other; p != null; p = p.parent) {
			if (p.equals(this)) {
				return true;
			}
		}
		return false;
	}

	public QuestionType of(String value) {
		for (QuestionType qt : QuestionType.values()) {
			if(qt.value.equals(value)){
				return qt;
			}
		}
		throw new IllegalArgumentException("无法实例化QuestionType，value:"+value);
	}

	public static boolean validate(Question question,String value){
		switch (question.getType()) {
			case NUMERICAL_INPUT:
				return NumberUtils.isNumber(value);
			case EMAIL_INPUT:
				return value.matches("^[\\w_.]{3,50}@\\w{1,50}\\.[a-z]{1,10}$");
			case CELLPHONE_INPUT:
				return value.matches("^0[0-9]{2,3}-[0-9]{7,8}$");
			case TELEPHONE_INPUT:
				return value.matches("^1[0-9]{10}$");
			case WEBSITE_INPUT:
				return value.matches("^https?://\\w+\\.[a-z]{1,50}\\S*$");
			default:
				return true;
		}
	}
	
}
