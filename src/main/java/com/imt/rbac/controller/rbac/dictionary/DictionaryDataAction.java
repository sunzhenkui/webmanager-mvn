package com.imt.rbac.controller.rbac.dictionary;

import com.imt.framework.system.ResponseObjManager;
import com.imt.rbac.model.DictionaryDataModel;
import com.imt.rbac.service.DicitonaryDataManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller  
@RequestMapping("/rbac/dictionary")
public class DictionaryDataAction {
	private final String CONTEXT_URL = "/rbac/dictionary/";
	@Autowired
	private DicitonaryDataManager dictionaryDataManager;
	
    @RequestMapping(value="/getDictionaryDataList",method = {RequestMethod.POST,RequestMethod.GET} )   
    @ResponseBody
    public Map<String, Object> getDictionaryDataList(HttpServletRequest request,Model model){
    	String dicValue = request.getParameter("dicValue");
    	DictionaryDataModel dataModel = new DictionaryDataModel();
    	dataModel.setDicValue(dicValue);
		int counter = dictionaryDataManager.queryForCount(dataModel);
		List<DictionaryDataModel> list = dictionaryDataManager.queryForList(dataModel);
		return ResponseObjManager.getRspList(counter, list);
    }


	@RequestMapping(value = "/dictionaryDataList", method = { RequestMethod.GET})
	public String dictionaryDataList(HttpServletRequest request,Model model) {
		String dicValue = request.getParameter("dicValue");
		model.addAttribute("dicValue", dicValue);
        return CONTEXT_URL+"dictionaryDataList";
	}
	
    @RequestMapping(value="/addDictionaryData",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addDictionaryData(Model model,DictionaryDataModel dic){
    	dictionaryDataManager.add(dic);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addDictionaryData",method = {RequestMethod.GET} )   
    public String addDictionaryDataGet(HttpServletRequest request,Model model){
		String dicValue = request.getParameter("dicValue");
		model.addAttribute("dicValue", dicValue);
        return CONTEXT_URL+"addDictionaryData";
    }
    
    @RequestMapping(value="/deleteDictionaryData",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> deleteDictionaryData(HttpServletRequest request,Model model){
    	String ids = request.getParameter("ids");
    	if(StringUtils.isNotBlank(ids)){
    		String[] gids = StringUtils.split(ids,",");
    		for(String id:gids){
    			DictionaryDataModel dicData = dictionaryDataManager.queryById(new Integer(id));
    	    	if(dicData==null )
    	        	return ResponseObjManager.getRspMessage(ResponseObjManager.RSP_ERROR,"删除失败！");
    	    	dictionaryDataManager.remove(new Integer(id));
    		}
    	}
    	return ResponseObjManager.getRspMessage();
    }
}
