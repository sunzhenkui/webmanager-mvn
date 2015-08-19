package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.UsersModel;

@Repository("tbUsersDAO")
public class UsersDAO extends BaseDAO<UsersModel>{
	private String namespace = "TbUsers";
	@Override
	protected String getNamespace() {
		return namespace;
	}
	
	public UsersModel queryByName(String name) {
		UsersModel model = null;
		try {
			model = sqlSession.selectOne(getNamespace() + "."+"queryByName",name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
