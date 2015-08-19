package com.imt.rbac.controller.rbac.user;

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
import com.imt.rbac.model.RolesModel;
import com.imt.rbac.model.UserAndRoleModel;
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.RolesManager;
import com.imt.rbac.service.UserAndRoleManager;
import com.imt.rbac.service.UserManager;

/**
 * 权限管理
 * @author zhenkui
 */

@Controller  
@RequestMapping("/rbac/user")
public class UserAccessAction {
	private final String CONTEXT_URL = "/rbac/user/";
	@Autowired
	private UserAndRoleManager tbUserAndRoleManager;
	@Autowired
	private RolesManager tbRolesManager;
	@Autowired
	private UserManager tbUserManager;

    @RequestMapping(value="/addUAccess",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addAccess(HttpServletRequest request,Model model){
    	Integer userId = new Integer(request.getParameter("userId"));
    	String[] rs = request.getParameterValues("roleId");
    	tbUserAndRoleManager.deleteForUserId(userId);
    	if(rs!=null){

	    	for(int i=0;i<rs.length;i++){
	    		Integer roleId = new Integer(rs[i]);
	    		UserAndRoleModel urModel = new UserAndRoleModel();
	    		urModel.setRoleId(roleId);
	    		urModel.setUserId(userId);
	    		tbUserAndRoleManager.add(urModel);
	    	}
    	}
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addUAccess",method = {RequestMethod.GET} )   
    public String addAccessGet(HttpServletRequest request,Model model){
    	
    	Integer userId = new Integer(request.getParameter("userId"));
    	List<RolesModel> roleList = tbRolesManager.queryForList(new RolesModel());
    	UserAndRoleModel um = new UserAndRoleModel();
    	um.setUserId(userId);
    	List<UserAndRoleModel> userRoleList = tbUserAndRoleManager.queryForList(um);
    	
    	UsersModel userModel = tbUserManager.queryById(userId);
    	
    	model.addAttribute("user", userModel);
    	model.addAttribute("roleList", roleList);
    	model.addAttribute("userRoleList", userRoleList);
    	
        return CONTEXT_URL+"addUAccess";
    }

}
