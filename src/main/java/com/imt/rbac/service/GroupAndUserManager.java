package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.BaseDAO;
import com.imt.rbac.model.GroupAndUserModel;

@Service("tbGroupAndUserManager")
public class GroupAndUserManager {
	@Autowired 
	private BaseDAO<GroupAndUserModel> tbGroupAndUserDAO;
	public void add(GroupAndUserModel model)  {
		tbGroupAndUserDAO.add(model);
	}
	
	public void update(GroupAndUserModel model)  {
		tbGroupAndUserDAO.update(model);
	}
	
	public GroupAndUserModel queryById(Integer singerId) {
		return tbGroupAndUserDAO.queryById(singerId);
	}
	
	public int queryForCount(GroupAndUserModel model){
		return tbGroupAndUserDAO.queryForCount(model);
	}
	
	public List<GroupAndUserModel> queryForList(GroupAndUserModel model) {
		return tbGroupAndUserDAO.queryForList(model);
	}
	
	public List<GroupAndUserModel> queryForList(GroupAndUserModel model , Page page) {
		return tbGroupAndUserDAO.queryForList(model , page);
	}
	
	
	public void remove(Integer id)  {
		tbGroupAndUserDAO.delete(id);
	}
}
