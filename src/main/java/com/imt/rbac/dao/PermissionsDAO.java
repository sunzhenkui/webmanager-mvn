package com.imt.rbac.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.PermissionsModel;

@Repository("tbPermissionsDAO")
public class PermissionsDAO extends BaseDAO<PermissionsModel>{
	private String namespace = "TbPermissions";
	@Override
	protected String getNamespace() {
		return namespace;
	}
	
	public List<PermissionsModel> queryByRoleId(PermissionsModel t) {
		List<PermissionsModel> list = null;
		try {
			list = sqlSession.selectList(getNamespace() + "."+"queryByRoleId",t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void deleteForResId(PermissionsModel t) {
		try {
			sqlSession.delete(getNamespace() + "."+"deleteByResId", t) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
