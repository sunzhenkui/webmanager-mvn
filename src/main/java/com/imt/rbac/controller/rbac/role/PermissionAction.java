package com.imt.rbac.controller.rbac.role;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imt.framework.system.ResponseObjManager;
import com.imt.rbac.model.PermissionsModel;
import com.imt.rbac.model.RolesModel;
import com.imt.rbac.service.PermissionsManager;
import com.imt.rbac.service.RolesManager;

@Controller  
@RequestMapping("/rbac/role")
public class PermissionAction {
	private final String CONTEXT_URL = "/rbac/role/";
	@Autowired
	private PermissionsManager tbPermissionsManager;
	@Autowired
	private RolesManager tbRolesManager;

	
	//选择一个资源的时候，ajax提交入库
    @RequestMapping(value="/addPermissions",method = {RequestMethod.POST} )   
    @ResponseBody
	public Map<String, Object> addPermissionsPost(Integer roleId, Integer resourceId, Model model) {
		if (resourceId == null && roleId == null)
			return ResponseObjManager.getRspErrorMessage("操作失败！");
		PermissionsModel perModel = new PermissionsModel();
		perModel.setRoleId(roleId);
		perModel.setResourceId(new Integer(resourceId));
		tbPermissionsManager.add(perModel);
		return ResponseObjManager.getRspMessage();
	}
    
	//选择一个资源的时候，ajax提交入库
    @RequestMapping(value="/removePermissions",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> removePermissions(Integer roleId,Integer resourceId,Model model){
    	PermissionsModel perModel = new PermissionsModel();
    	perModel.setRoleId(roleId);
    	perModel.setResourceId(resourceId);
    	tbPermissionsManager.deleteForResId(perModel);
//    	tbPermissionsManager.add(perModel);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addPermissions",method = {RequestMethod.GET} )   
    public String addPermissions(HttpServletRequest request,Model model){
    	Integer roleId = new Integer(request.getParameter("roleId"));
    	RolesModel role = tbRolesManager.queryById(roleId);
    	model.addAttribute("role", role);
        return CONTEXT_URL+"addPermissions";
    }
    
    
    @RequestMapping(value="/getPermissionList",method = {RequestMethod.GET} )   
    @ResponseBody
    public Map<String, Object> getPermissionList(HttpServletRequest request,Model model){
    	Integer roleId = new Integer(request.getParameter("roleId"));
    	PermissionsModel perModel = new PermissionsModel();
    	perModel.setRoleId(roleId);
    	List<PermissionsModel> list = tbPermissionsManager.queryForList(perModel);
    	int total = tbPermissionsManager.queryForCount(perModel);
    	return ResponseObjManager.getRspList(total, list);
    }
    

}
