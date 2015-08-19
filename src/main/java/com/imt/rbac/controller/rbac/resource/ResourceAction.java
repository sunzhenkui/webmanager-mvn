package com.imt.rbac.controller.rbac.resource;

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
import com.imt.rbac.model.ResourcesModel;
import com.imt.rbac.service.ResourcesManager;

@Controller  
@RequestMapping("/rbac/resource")
public class ResourceAction {
	private final String CONTEXT_URL = "/rbac/resource/";
	@Autowired
	private ResourcesManager tbResourcesManager;
	
    @RequestMapping(value="/getResourceList",method = {RequestMethod.POST,RequestMethod.GET} )   
    @ResponseBody
    public Map<String, Object> getResourceList(HttpServletRequest request,Model model,ResourcesModel res){
		int pageNo = 1;
		String no = request.getParameter("pno");
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		int counter = tbResourcesManager.queryForCount(res);
		Page page = new Page(counter, Global.PAGE_SIZE);
		page.setPage(pageNo);
		List<ResourcesModel> list = tbResourcesManager.queryForList(res, page);
		return ResponseObjManager.getRspList(counter, list);
    }

	@RequestMapping(value = "/resourceList", method = { RequestMethod.GET})
	public String resourceList(Model model) {
        return CONTEXT_URL+"resourceList";
	}
	
    @RequestMapping(value="/addResource",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addResource(Model model,ResourcesModel res){
    	tbResourcesManager.add(res);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addResource",method = {RequestMethod.GET} )   
    public String addUserGet(HttpServletRequest request,Model model){
		String menuId = request.getParameter("menuId");
		model.addAttribute("menuId",menuId);
        return CONTEXT_URL+"addResource";
    }
    
    @RequestMapping(value="/modifyResource",method = {RequestMethod.GET} )   
    public String modifyResource(Integer resourceId,Model model){
    	ResourcesModel res = tbResourcesManager.queryById(resourceId);
    	model.addAttribute("res", res);
        return CONTEXT_URL+"modifyResource";
    }
    
    @RequestMapping(value="/modifyResource",method = {RequestMethod.POST} ) 
    @ResponseBody
    public Map<String, Object> modifyResource(Model model,ResourcesModel res){
    	tbResourcesManager.add(res);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/getResourceById",method = {RequestMethod.GET} ) 
    @ResponseBody
    public ResourcesModel getResourceById(Integer id,Model model){
    	return tbResourcesManager.queryById(id);
    }
    
    @RequestMapping(value="/deleteResource",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> delResource(Integer id,Model model){
    	tbResourcesManager.remove(id);
    	return ResponseObjManager.getRspMessage();
    }
}
