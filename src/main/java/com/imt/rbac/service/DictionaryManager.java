package com.imt.rbac.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.common.Global;
import com.imt.framework.system.Page;
import com.imt.rbac.dao.DictionaryDAO;
import com.imt.rbac.dao.DictionaryDataDAO;
import com.imt.rbac.model.DictionaryDataModel;
import com.imt.rbac.model.DictionaryModel;

@Service("dictionaryManager")
public class DictionaryManager {
	@Autowired 
	private DictionaryDAO dictionaryDAO;
	@Autowired 
	private DictionaryDataDAO dictionaryDataDAO;
	public void add(DictionaryModel model)  {
		dictionaryDAO.add(model);
	}
	
	public void update(DictionaryModel model)  {
		dictionaryDAO.update(model);
	}
	
	public DictionaryModel queryById(String dicValue) {
		return dictionaryDAO.queryById(dicValue);
	}
	
	public int queryForCount(DictionaryModel model){
		return dictionaryDAO.queryForCount(model);
	}
	
	public List<DictionaryModel> queryForList(DictionaryModel model,Page page) {
		return dictionaryDAO.queryForList(model,page);
	}
	
	public List<DictionaryModel> queryForList(DictionaryModel model) {
		return dictionaryDAO.queryForList(model);
	}
	public void remove(String dicValue)  {
		dictionaryDAO.delete(dicValue);
	}
	
	public static String getDictionary(String dicValue, String dicDataName) {
		HashMap<String,String> maps = Global.dictionaryMap.get(dicValue);
		if(maps==null) return null;
		String value = maps.get(dicDataName);
		return value;
	}
	
	public static HashMap<String,String> getDictionarys(String dicValue) {
		HashMap<String,String> maps = Global.dictionaryMap.get(dicValue);
		return maps;
	}
	
	public void dictionaryInit(){
		Global.dictionaryMap.clear();
		List<DictionaryModel> dicList = dictionaryDAO.queryForList(new DictionaryModel());
		for(DictionaryModel model :dicList){
			Global.dictionaryMap.put(model.getDicValue(), new HashMap<String, String>());
		}
		
		List<DictionaryDataModel> dataList = dictionaryDataDAO.queryForList(new DictionaryDataModel());
		for(DictionaryDataModel model :dataList){
			String dicValue = model.getDicValue();
			Global.dictionaryMap.get(dicValue).put(model.getDicDataValue(), model.getDicDataName());
		}
	}

}
