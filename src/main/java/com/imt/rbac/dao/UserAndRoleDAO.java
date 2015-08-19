package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.UserAndRoleModel;

@Repository("tbUserAndRoleDAO")
public class UserAndRoleDAO extends BaseDAO<UserAndRoleModel>{
	private String namespace = "TbUserAndRole";
	@Override
	protected String getNamespace() {
		return namespace;
	}
	
	public void deleteForUserId(Integer userId) {
		try {
			sqlSession.delete(getNamespace() + "."+"deleteForUserId", userId) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
