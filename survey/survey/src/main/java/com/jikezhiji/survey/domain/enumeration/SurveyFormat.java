/*
 * Copyright (c) 2009-2013 上海通路快建网络外包服务有限公司 All rights reserved.
 * @(#) QuestionType.java 2013-05-28 19:58
 */

package com.jikezhiji.survey.domain.enumeration;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 表单展示格式。
 *
 * @author E557
 * @version $Id: Format.java 80820 2015-04-20 06:36:42Z E557 $
 * @since 1.0
 */
public enum SurveyFormat {

	QUESTION_BY_QUESTION("S","按题分页"),

	GROUP_BY_GROUP("G","按组分页"),

	ALL_IN_ONE("A","全部都在一页");

	final String value;
	final String name;

	SurveyFormat(String value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public SurveyFormat of(String value) {
		return of(value, QUESTION_BY_QUESTION);
	}

	public static SurveyFormat of(String value, SurveyFormat defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		for (SurveyFormat format : values()) {
			if (format.value.equals(value)) {
				return format;
			}
		}
		return defaultValue;
	}

	public static List<String> listNames() {
		List<String> list = Lists.newLinkedList();
		for (SurveyFormat format : values()) {
			list.add(format.getName());
		}
		return list;
	}

	public static List<String> listValues() {
		List<String> list = Lists.newLinkedList();
		for (SurveyFormat format : values()) {
			list.add(format.getValue());
		}
		return list;
	}

	public static Map<String, String> map() {
		Map<String, String> values = Maps.newLinkedHashMap();
		for (SurveyFormat format : values()) {
			values.put(format.getValue(), format.getName());
		}
		return values;
	}
    
}
