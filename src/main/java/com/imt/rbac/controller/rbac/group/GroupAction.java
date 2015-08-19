package com.imt.rbac.controller.rbac.group;

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
import com.imt.rbac.model.UserGroupsModel;
import com.imt.rbac.service.UsergroupManager;

@Controller  
@RequestMapping("/rbac/group")
public class GroupAction {
	private final String CONTEXT_URL = "/rbac/group/";
	
	@Autowired 
	private UsergroupManager tbUsergroupManager;

	@RequestMapping(value = "/getGroupList", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
	public Map<String, Object> getGroupList(HttpServletRequest request,Model model, UserGroupsModel ug) {
		int pageNo = 1;
		String no = request.getParameter("pno");
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		int counter = tbUsergroupManager.queryForCount(ug);
		Page page = new Page(counter, Global.PAGE_SIZE);
		page.setPage(pageNo);
		List<UserGroupsModel> list = tbUsergroupManager.queryForList(ug, page);
		return ResponseObjManager.getRspList(counter, list);
	}

	@RequestMapping(value = "/groupList", method = { RequestMethod.GET})
	public String groupList(Model model) {
        return CONTEXT_URL+"groupList";
	}
	
	
    @RequestMapping(value="/addGroup",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addGroup(Model model,UserGroupsModel ug){
    	tbUsergroupManager.add(ug);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addGroup",method = {RequestMethod.GET} )   
    public String addGroup(HttpServletRequest request,Model model){
        return CONTEXT_URL+"addGroup";
    }
    
    
    @RequestMapping(value="/modifyGroup",method = {RequestMethod.GET} )   
    public String modifyGroup(Integer id,Model model){
    	UserGroupsModel ug = tbUsergroupManager.queryById(id);
    	model.addAttribute("model", ug);
        return CONTEXT_URL+"modifyGroup";
    }
    
    @RequestMapping(value="/modifyGroup",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> modifyGroup(Model model,UserGroupsModel ug){
    	tbUsergroupManager.update(ug);
    	return ResponseObjManager.getRspMessage();
    }
    
    
    @RequestMapping(value="/getGroupById",method = {RequestMethod.GET} ) 
    @ResponseBody
    public UserGroupsModel getGroupById(Integer id,Model model){
    	return tbUsergroupManager.queryById(id);
    }

    @RequestMapping(value="/deleteGroup",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> delGroup(Integer id,Model model){
    	tbUsergroupManager.remove(id);
    	return ResponseObjManager.getRspMessage();
    }
    
}
