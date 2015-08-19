package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.RolesModel;

@Repository("tbRolesDAO")
public class RolesDAO extends BaseDAO<RolesModel>{
	private String namespace = "TbRoles";
	@Override
	protected String getNamespace() {
		return namespace;
	}
}
