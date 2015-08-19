package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.GroupAndUserModel;

@Repository("tbGroupAndUserDAO")
public class GroupAndUserDAO  extends BaseDAO<GroupAndUserModel>{
	private String namespace = "TbGroupAndUser";
	@Override
	protected String getNamespace() {
		return namespace;
	}

}
