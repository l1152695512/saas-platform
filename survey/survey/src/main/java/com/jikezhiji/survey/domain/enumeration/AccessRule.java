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
 * 表单填写权限控制
 *
 * @author E557
 * @version $Id: ShareRule.java 80820 2015-04-20 06:36:42Z E557 $
 * @since 1.0
 */
public enum AccessRule {

	/**公开的，不做限制*/
	PUBLIC(0,"不做限制"),
	/**每个用户限填一次*/
	ONCE_PER_USER(1,"每个用户限填一次"),
	/**每台设备限填一次*/
	ONCE_PER_DEVICE(2,"每台电脑/手机限填一次"),
	/**每个IP限填一次*/
	ONCE_PER_IP(3,"每个IP限填一次"),
	/**凭令牌填写*/
	TOKEN(4,"凭令牌填写");
	
	final int value;
	final String name;

	AccessRule(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public AccessRule of(Integer value) {
		return of(value, PUBLIC);
	}

	public static AccessRule of(Integer value, AccessRule defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		for (AccessRule format : values()) {
			if (Integer.valueOf(format.value).equals(value)) {
				return format;
			}
		}
		return defaultValue;
	}

	public static List<String> listNames() {
		List<String> list = Lists.newLinkedList();
		for (AccessRule format : values()) {
			list.add(format.getName());
		}
		return list;
	}

	public static List<Integer> listValues() {
		List<Integer> list = Lists.newLinkedList();
		for (AccessRule format : values()) {
			list.add(format.getValue());
		}
		return list;
	}

	public static Map<Integer, String> map() {
		Map<Integer, String> values = Maps.newLinkedHashMap();
		for (AccessRule format : values()) {
			values.put(format.getValue(), format.getName());
		}
		return values;
	}
}
