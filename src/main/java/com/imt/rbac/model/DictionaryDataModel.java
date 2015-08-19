package com.imt.rbac.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DictionaryDataModel {
	private Integer id;
	private String dicDataName;
	private String dicDataValue;
	private String dicValue;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDicDataName() {
		return dicDataName;
	}

	public void setDicDataName(String dicDataName) {
		this.dicDataName = dicDataName;
	}

	public String getDicDataValue() {
		return dicDataValue;
	}

	public void setDicDataValue(String dicDataValue) {
		this.dicDataValue = dicDataValue;
	}

	public String getDicValue() {
		return dicValue;
	}

	public void setDicValue(String dicValue) {
		this.dicValue = dicValue;
	}

	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
