package com.imt.rbac.controller.rbac.dictionary;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imt.common.Global;
import com.imt.framework.system.Page;
import com.imt.framework.system.ResponseObjManager;
import com.imt.rbac.model.DictionaryModel;
import com.imt.rbac.service.DictionaryManager;

/**
 * 字典管理
 */

@Controller  
@RequestMapping("/rbac/dictionary")
public class DictionaryAction {
	private final String CONTEXT_URL = "/rbac/dictionary/";
	@Autowired
	private DictionaryManager dictionaryManager;
	
    @RequestMapping(value="/getDictionaryList",method = {RequestMethod.POST,RequestMethod.GET} )   
    @ResponseBody
    public Map<String, Object> getDictionaryList(HttpServletRequest request,Model model,DictionaryModel dic){
		int pageNo = 1;
		String no = request.getParameter("pno");
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		
		int counter = dictionaryManager.queryForCount(dic);
		Page page = new Page(counter, Global.PAGE_SIZE);
		page.setPage(pageNo);
		List<DictionaryModel> list = dictionaryManager.queryForList(dic, page);
		return ResponseObjManager.getRspList(counter, list);
    }


	@RequestMapping(value = "/dictionaryList", method = { RequestMethod.GET})
	public String dictionaryList(Model model) {
        return CONTEXT_URL+"dictionaryList";
	}
	
    @RequestMapping(value="/addDictionary",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addDictionary(Model model,DictionaryModel dic){
    	dictionaryManager.add(dic);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addDictionary",method = {RequestMethod.GET} )   
    public String addDictionaryGet(HttpServletRequest request,Model model){
        return CONTEXT_URL+"addDictionary";
    }
    
    @RequestMapping(value="/deleteDictionary",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> delDictionary(String dicValue,Model model){
    	dictionaryManager.remove(dicValue);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/flushDictionary",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> flushDictionary(Model model){
    	dictionaryManager.dictionaryInit();
    	return ResponseObjManager.getRspMessage();
    }
}
