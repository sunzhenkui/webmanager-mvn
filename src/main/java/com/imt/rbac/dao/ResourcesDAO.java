package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.ResourcesModel;

@Repository("tbResourcesDAO")
public class ResourcesDAO extends BaseDAO<ResourcesModel>{
	private String namespace = "TbResources";
	@Override
	protected String getNamespace() {
		return namespace;
	}
}
