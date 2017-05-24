package com.jikezhiji.commons.domain.converter;

import com.alibaba.fastjson.JSON;

public class JSONConverter<X> extends AbstractJSONConverter<X, String> {
    public JSONConverter(){
		if(databaseType != String.class) {
			throw new IllegalArgumentException(" 泛型类型错误： "+databaseType + "，JSONConverter 的泛型类型只支持String类型 ");
		}
	}
	
	public String convertToDatabaseColumn(X attribute) {
		return JSON.toJSONString(attribute);
	}
	
}
