package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.common.util.CryptUtil;
import com.imt.framework.system.Page;
import com.imt.rbac.dao.UsersDAO;
import com.imt.rbac.model.UsersModel;

@Service("tbUserManager")
public class UserManager {
	@Autowired 
	private UsersDAO tbUsersDAO;
	public void add(UsersModel model)  {
		model.setPassword( CryptUtil.md5Encoder(model.getPassword()) );
		tbUsersDAO.add(model);
	}
	
	public void update(UsersModel model)  {
		model.setPassword( CryptUtil.md5Encoder(model.getPassword()) );
		tbUsersDAO.update(model);
	}
	
	public void remove(Integer id)  {
		tbUsersDAO.delete(id);
	}
	
	public UsersModel queryById(Integer id) {
		return tbUsersDAO.queryById(id);
	}
	
	public int queryForCount(UsersModel model){
		return tbUsersDAO.queryForCount(model);
	}
	
	public List<UsersModel> queryForList(UsersModel model,Page page) {
		return tbUsersDAO.queryForList(model,page);
	}
	
	public List<UsersModel> queryForList(UsersModel model) {
		return tbUsersDAO.queryForList(model);
	}
	
	public UsersModel queryByName(String name) {
		return tbUsersDAO.queryByName(name);
	}
}
