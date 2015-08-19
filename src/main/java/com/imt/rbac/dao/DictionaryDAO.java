package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.DictionaryModel;

@Repository("dictionaryDAO")
public class DictionaryDAO extends BaseDAO<DictionaryModel>{
	private String namespace = "Dictionary";
	@Override
	protected String getNamespace() {
		return namespace;
	}
}
