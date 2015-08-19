package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.DictionaryDataModel;

@Repository("dictionaryDataDAO")
public class DictionaryDataDAO  extends BaseDAO<DictionaryDataModel>{
	private String namespace = "DictionaryData";
	@Override
	protected String getNamespace() {
		return namespace;
	}
}
