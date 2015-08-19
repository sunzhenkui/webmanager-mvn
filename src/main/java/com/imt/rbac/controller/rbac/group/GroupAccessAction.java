package com.imt.rbac.controller.rbac.group;

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
import com.imt.rbac.model.GroupAndRoleModel;
import com.imt.rbac.model.RolesModel;
import com.imt.rbac.model.UserGroupsModel;
import com.imt.rbac.service.GroupAndRoleManager;
import com.imt.rbac.service.RolesManager;
import com.imt.rbac.service.UsergroupManager;

/**
 * 权限管理
 * @author 
 */

@Controller  
@RequestMapping("/rbac/group")
public class GroupAccessAction {
	private final String CONTEXT_URL = "/rbac/group/";
	@Autowired
	private GroupAndRoleManager tbGroupAndRoleManager;
	@Autowired
	private RolesManager tbRolesManager;
	@Autowired
	private UsergroupManager tbUsergroupManager;

    @RequestMapping(value="/addGAccess",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addAccess(HttpServletRequest request,Model model){
    	Integer groupId = new Integer(request.getParameter("groupId"));
    	String[] rs = request.getParameterValues("roleId");
    	tbGroupAndRoleManager.deleteForGroupId(groupId);
    	if(rs!=null){
	    	for(int i=0;i<rs.length;i++){
	    		Integer roleId = new Integer(rs[i]);
	    		GroupAndRoleModel grModel = new GroupAndRoleModel();
	    		grModel.setRoleId(roleId);
	    		grModel.setGroupId(groupId);
	    		tbGroupAndRoleManager.add(grModel);
	    	}
    	}
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addGAccess",method = {RequestMethod.GET} )   
    public String addAccessGet(HttpServletRequest request,Model model){
    	
    	Integer groupId = new Integer(request.getParameter("groupId"));
    	List<RolesModel> roleList = tbRolesManager.queryForList(new RolesModel());
    	GroupAndRoleModel um = new GroupAndRoleModel();
    	um.setGroupId(groupId);
    	List<GroupAndRoleModel> groupRoleList = tbGroupAndRoleManager.queryForList(um);
    	
    	UserGroupsModel groupModel = tbUsergroupManager.queryById(groupId);
    	
    	model.addAttribute("group", groupModel);
    	model.addAttribute("roleList", roleList);
    	model.addAttribute("groupRoleList", groupRoleList);
    	
        return CONTEXT_URL+"addGAccess";
    }

}
