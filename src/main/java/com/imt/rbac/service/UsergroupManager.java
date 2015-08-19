package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.BaseDAO;
import com.imt.rbac.model.UserGroupsModel;

@Service("tbUsergroupManager")
public class UsergroupManager {
	@Autowired 
	private BaseDAO<UserGroupsModel> tbUsergroupDAO;
	public void add(UserGroupsModel model)  {
		tbUsergroupDAO.add(model);
	}
	
	public void update(UserGroupsModel model)  {
		tbUsergroupDAO.update(model);
	}
	
	public UserGroupsModel queryById(Integer singerId) {
		return tbUsergroupDAO.queryById(singerId);
	}
	
	public int queryForCount(UserGroupsModel model){
		return tbUsergroupDAO.queryForCount(model);
	}
	
	public List<UserGroupsModel> queryForList(UserGroupsModel model) {
		return tbUsergroupDAO.queryForList(model);
	}
	
	public List<UserGroupsModel> queryForList(UserGroupsModel model,Page page) {
		return tbUsergroupDAO.queryForList(model,page);
	}
	
	public void remove(Integer id)  {
		tbUsergroupDAO.delete(id);
	}
}
