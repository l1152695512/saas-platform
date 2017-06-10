/*
 * Copyright (c) 2009-2013 上海通路快建网络外包服务有限公司 All rights reserved.
 * @(#) QuestionType.java 2013-05-28 19:58
 */

package com.jikezhiji.survey.domain.embedded;


public enum LogicOperator {

	DISPLAYED(0,"显示"),
	CHECKED(1,"选中"),
	UNCHECKED(2,"不选中"),
	EQUAL(3,"等于"),
	UNEQUAL(4,"不等于"),
	CONTAINS(5,"包含"),
	GREATER_THAN(6,"大于"),
	GREATER_EQUAL(7,"大于等于"),
	LESS_THAN(8,"小于"),
	LESS_EQUAL(9,"小于等于");
	
    final int value;
    final String name;

    LogicOperator(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

	public LogicOperator of(Integer value) {
		for (LogicOperator e : LogicOperator.values()) {
			if(Integer.valueOf(e.value).equals(value)) {
				return e;
			}
		}
		throw new IllegalArgumentException("无法实例化LogicOperator，value:"+value);
	}
    
}