package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.UserGroupsModel;

@Repository("tbUsergroupDAO")
public class UsergroupDAO extends BaseDAO<UserGroupsModel>{
	private String namespace = "TbUsergroups";
	@Override
	protected String getNamespace() {
		return namespace;
	}
}
