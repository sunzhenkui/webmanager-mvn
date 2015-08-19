package com.imt.rbac.controller.rbac.role;

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
import com.imt.rbac.model.RolesModel;
import com.imt.rbac.service.RolesManager;

@Controller  
@RequestMapping("/rbac/role")
public class RoleAction {
	private final String CONTEXT_URL = "/rbac/role/";
	
	@Autowired
	private RolesManager tbRolesManager;
	
	@RequestMapping(value = "/getRoleList", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
	public Map<String, Object> getRoleList(HttpServletRequest request,Model model, RolesModel role) {
		int pageNo = 1;
		String no = request.getParameter("pno");
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		int counter = tbRolesManager.queryForCount(role);
		Page page = new Page(counter, Global.PAGE_SIZE);
		page.setPage(pageNo);
		List<RolesModel> roleList = tbRolesManager.queryForList(role, page);
		return ResponseObjManager.getRspList(counter, roleList);
	}
	
	@RequestMapping(value = "/roleList", method = { RequestMethod.GET})
	public String roleList(Model model) {
        return CONTEXT_URL+"roleList";
	}
	
    @RequestMapping(value="/addRole",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addRole(Model model,RolesModel role){
    	tbRolesManager.add(role);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addRole",method = {RequestMethod.GET} )   
    public String addRole(HttpServletRequest request,Model model){
        return CONTEXT_URL+"addRole";
    }
    
    
    @RequestMapping(value="/modifyRole",method = {RequestMethod.GET} )   
    public String modifyRole(Integer id,Model model){
    	RolesModel role = tbRolesManager.queryById(id);
    	model.addAttribute("model", role);
        return CONTEXT_URL+"modifyRole";
    }
    
    @RequestMapping(value="/modifyRole",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> modifyRole(Model model,RolesModel role){
    	tbRolesManager.update(role);
    	return ResponseObjManager.getRspMessage();
    }
    
    
    @RequestMapping(value="/getRoleById",method = {RequestMethod.GET} ) 
    @ResponseBody
    public RolesModel getRoleById(Integer id,Model model){
    	return tbRolesManager.queryById(id);
    }

    @RequestMapping(value="/deleteRole",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> delRole(Integer id,Model model){
    	tbRolesManager.remove(id);
    	return ResponseObjManager.getRspMessage();
    }
    
}
