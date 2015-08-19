package com.imt.rbac.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.DictionaryDataDAO;
import com.imt.rbac.model.DictionaryDataModel;

@Service("dicitonaryDataManager")
public class DicitonaryDataManager {
	@Autowired 
	private DictionaryDataDAO dictionaryDataDAO;
	public void add(DictionaryDataModel model)  {
		dictionaryDataDAO.add(model);
	}
	
	public void update(DictionaryDataModel model)  {
		dictionaryDataDAO.update(model);
	}
	
	public DictionaryDataModel queryById(Integer id) {
		return dictionaryDataDAO.queryById(id);
	}
	
	public int queryForCount(DictionaryDataModel model){
		return dictionaryDataDAO.queryForCount(model);
	}
	
	public List<DictionaryDataModel> queryForList(DictionaryDataModel model,Page page) {
		return dictionaryDataDAO.queryForList(model,page);
	}
	
	public List<DictionaryDataModel> queryForList(DictionaryDataModel model) {
		return dictionaryDataDAO.queryForList(model);
	}
	public void remove(Integer id)  {
		dictionaryDataDAO.delete(id);
	}
}
