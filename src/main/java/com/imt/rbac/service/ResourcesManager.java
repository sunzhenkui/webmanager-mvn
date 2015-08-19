package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.BaseDAO;
import com.imt.rbac.model.ResourcesModel;

@Service("tbResourcesManager")
public class ResourcesManager {
	@Autowired 
	private BaseDAO<ResourcesModel> tbResourcesDAO;
	public void add(ResourcesModel model)  {
		tbResourcesDAO.add(model);
	}
	
	public void update(ResourcesModel model)  {
		tbResourcesDAO.update(model);
	}
	
	public ResourcesModel queryById(Integer resourceId) {
		return tbResourcesDAO.queryById(resourceId);
	}
	
	public int queryForCount(ResourcesModel model){
		return tbResourcesDAO.queryForCount(model);
	}
	
	public List<ResourcesModel> queryForList(ResourcesModel model,Page page) {
		return tbResourcesDAO.queryForList(model,page);
	}
	
	public List<ResourcesModel> queryForList(ResourcesModel model) {
		return tbResourcesDAO.queryForList(model);
	}
	
	public void remove(Integer id)  {
		tbResourcesDAO.delete(id);
	}
}
