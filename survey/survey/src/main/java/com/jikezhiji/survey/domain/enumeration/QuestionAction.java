/*
 * Copyright (c) 2009-2013 上海通路快建网络外包服务有限公司 All rights reserved.
 * @(#) QuestionType.java 2013-05-28 19:58
 */

package com.jikezhiji.survey.domain.enumeration;


/**
 * 表单展示格式。
 *
 * @author E557
 * @version $Id: Format.java 80820 2015-04-20 06:36:42Z E557 $
 * @since 1.0
 */
public enum QuestionAction  {

	JUMP("J", "跳转"),

	TERMINATE("T", "提前结束");

	final String value;
	final String name;

	QuestionAction(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public QuestionAction of(String value) {
		for (QuestionAction qa : QuestionAction.values()) {
			if(qa.value.equals(value)){
				return qa;
			}
		}
		throw new IllegalArgumentException("无法实例化QuestionAction，value:"+value);
	}
}
