package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.GroupAndRoleDAO;
import com.imt.rbac.model.GroupAndRoleModel;

@Service("tbGroupAndRoleManager")
public class GroupAndRoleManager {
	@Autowired 
	private GroupAndRoleDAO tbGroupAndRoleDAO;
	public void add(GroupAndRoleModel model)  {
		tbGroupAndRoleDAO.add(model);
	}
	
	public void update(GroupAndRoleModel model)  {
		tbGroupAndRoleDAO.update(model);
	}
	
	public GroupAndRoleModel queryById(Integer singerId) {
		return tbGroupAndRoleDAO.queryById(singerId);
	}
	
	public int queryForCount(GroupAndRoleModel model){
		return tbGroupAndRoleDAO.queryForCount(model);
	}
	
	public List<GroupAndRoleModel> queryForList(GroupAndRoleModel model) {
		return tbGroupAndRoleDAO.queryForList(model);
	}
	
	public List<GroupAndRoleModel> queryForList(GroupAndRoleModel model,Page page) {
		return tbGroupAndRoleDAO.queryForList(model,page);
	}
	
	public void remove(Integer id)  {
		tbGroupAndRoleDAO.delete(id);
	}
	
	public void deleteForGroupId(Integer groupId)  {
		tbGroupAndRoleDAO.deleteForGroupId(groupId);
	}
	
	public List<GroupAndRoleModel> queryListForUID(Integer userId) {
		return tbGroupAndRoleDAO.queryListForUID(userId);
	}
}
