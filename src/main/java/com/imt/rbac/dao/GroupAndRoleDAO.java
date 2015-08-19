package com.imt.rbac.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.GroupAndRoleModel;

@Repository("tbGroupAndRoleDAO")
public class GroupAndRoleDAO  extends BaseDAO<GroupAndRoleModel>{
	private String namespace = "TbGroupAndRole";
	@Override
	protected String getNamespace() {
		return namespace;
	}
	
	
	public List<GroupAndRoleModel> queryListForUID(Integer userId) {
		List<GroupAndRoleModel> list = null;
		try {
			list = sqlSession.selectList(getNamespace() + "."+"queryListForUID",userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void deleteForGroupId(Integer groupId) {
		try {
			sqlSession.delete(getNamespace() + "."+"deleteForGroupId", groupId) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
