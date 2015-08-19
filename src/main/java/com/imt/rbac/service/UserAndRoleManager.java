package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.UserAndRoleDAO;
import com.imt.rbac.model.GroupAndRoleModel;
import com.imt.rbac.model.RolesModel;
import com.imt.rbac.model.UserAndRoleModel;

@Service("tbUserAndRoleManager")
public class UserAndRoleManager {
	@Autowired 
	private UserAndRoleDAO tbUserAndRoleDAO;
	@Autowired 
	private GroupAndRoleManager tbGroupAndRoleManager;
	
	public void add(UserAndRoleModel model)  {
		tbUserAndRoleDAO.add(model);
	}
	
	public void update(UserAndRoleModel model)  {
		tbUserAndRoleDAO.update(model);
	}
	
	public UserAndRoleModel queryById(Integer singerId) {
		return tbUserAndRoleDAO.queryById(singerId);
	}
	
	public int queryForCount(UserAndRoleModel model){
		return tbUserAndRoleDAO.queryForCount(model);
	}
	
	public List<UserAndRoleModel> queryForList(UserAndRoleModel model) {
		return tbUserAndRoleDAO.queryForList(model);
	}
	
	
	public List<UserAndRoleModel> queryForList(UserAndRoleModel model,Page page) {
		return tbUserAndRoleDAO.queryForList(model,page);
	}
	
	public void remove(Integer id)  {
		tbUserAndRoleDAO.delete(id);
	}
	
	public void deleteForUserId(Integer userId)  {
		tbUserAndRoleDAO.deleteForUserId(userId);
	}
	
	public boolean  isAdmin(Integer userId) {
		boolean isAdmin = false;
		UserAndRoleModel model = new UserAndRoleModel();
		model.setUserId(userId);
		List<UserAndRoleModel> list =  tbUserAndRoleDAO.queryForList(model);
		for(UserAndRoleModel urModel:list){
			if(urModel.getRole().getRoleId().intValue()==RolesModel.ADMIN){
				isAdmin = true;
				break;
			}
		}
		
		if(isAdmin) return isAdmin;
		
		List<GroupAndRoleModel> grList = tbGroupAndRoleManager.queryListForUID(userId);
		for(GroupAndRoleModel grModel:grList){
			if(grModel.getRoleId().intValue()==RolesModel.ADMIN){
				isAdmin = true;
				break;
			}
		}
		return isAdmin;
	}
}
