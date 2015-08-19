package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.BaseDAO;
import com.imt.rbac.model.RolesModel;

@Service("tbRolesManager")
public class RolesManager {
	@Autowired 
	private BaseDAO<RolesModel> tbRolesDAO;
	public void add(RolesModel model)  {
		tbRolesDAO.add(model);
	}
	
	public void update(RolesModel model)  {
		tbRolesDAO.update(model);
	}
	
	public RolesModel queryById(Integer roleId) {
		return tbRolesDAO.queryById(roleId);
	}
	
	public int queryForCount(RolesModel model){
		return tbRolesDAO.queryForCount(model);
	}
	
	public List<RolesModel> queryForList(RolesModel model,Page page) {
		return tbRolesDAO.queryForList(model,page);
	}
	
	public List<RolesModel> queryForList(RolesModel model) {
		return tbRolesDAO.queryForList(model);
	}
	
	public void remove(Integer id)  {
		tbRolesDAO.delete(id);
	}

}
