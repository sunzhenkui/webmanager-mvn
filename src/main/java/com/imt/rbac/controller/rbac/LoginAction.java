package com.imt.rbac.controller.rbac;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imt.common.Global;
import com.imt.common.util.CryptUtil;
import com.imt.framework.system.ResponseObjManager;
import com.imt.rbac.controller.helper.Interceptor.NeedNotLogon;
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.PermissionsManager;
import com.imt.rbac.service.UserAndRoleManager;
import com.imt.rbac.service.UserManager;

@Controller
public class LoginAction {
	@Autowired
	private UserManager tbUserManager;
	@Autowired
	private PermissionsManager tbPermissionsManager;
	@Autowired
	private UserAndRoleManager tbUserAndRoleManager;
	
    @NeedNotLogon
    @RequestMapping(value="/login",method = RequestMethod.GET)   
    public String login( HttpServletRequest request ){
    	return "login";
    }
    
    //登录入口
    @NeedNotLogon
    @RequestMapping(value="/login",method = RequestMethod.POST)   
    public String login(HttpServletRequest request,UsersModel reqModel,Model model) {   
    	//校验用户名密码
    	reqModel.setPassword( CryptUtil.md5Encoder(reqModel.getPassword()) );
    	List<UsersModel> list = tbUserManager.queryForList(reqModel);
    	if(list!=null &&list.size()>0){
        	UsersModel userModel = (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
        	userModel = list.get(0);
        	//查找用户是否是admins
        	boolean isAdmin = tbUserAndRoleManager.isAdmin(userModel.getUserId());
        	userModel.setAdmin(isAdmin);
        	request.getSession().setAttribute(Global.LOGON_SESSION_KEY,userModel);
        	
        	if(!isAdmin){
//	        	if(Global.userPermissionsMap.get(userModel.getUserId())==null){
	        		//登录缓存初始化，每次登陆都重新更新缓存 ，具体方法见PermissionsManager
	        		tbPermissionsManager.permissionInit(userModel.getUserId());
//	        	}
        	}
            return "redirect:/index";
    	}
    	else{//登录错误
    		model.addAttribute("logonInfo", "登录错误，请重试！");
            return "login";
    	}
    }
    
    //登录入口
    @NeedNotLogon
    @ResponseBody
    @RequestMapping(value="/ulogin",method = RequestMethod.POST)   
    public Map<String, Object> ulogin(HttpServletRequest request,UsersModel reqModel,Model model) {   
    	//校验用户名密码
    	reqModel.setPassword( CryptUtil.md5Encoder(reqModel.getPassword()) );
    	List<UsersModel> list = tbUserManager.queryForList(reqModel);
    	if(list!=null &&list.size()>0){
        	UsersModel userModel = (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
        	userModel = list.get(0);
        	//查找用户是否是admins
        	boolean isAdmin = tbUserAndRoleManager.isAdmin(userModel.getUserId());
        	userModel.setAdmin(isAdmin);
        	request.getSession().setAttribute(Global.LOGON_SESSION_KEY,userModel);
        	
        	if(!isAdmin){
//	        	if(Global.userPermissionsMap.get(userModel.getUserId())==null){
	        		//登录缓存初始化，每次登陆都重新更新缓存 ，具体方法见PermissionsManager
	        		tbPermissionsManager.permissionInit(userModel.getUserId());
//	        	}
        	}
	    	return ResponseObjManager.getRspMessage();
    	}
    	else{//登录错误
	    	return ResponseObjManager.getRspErrorMessage("登录错误，请重试！");
    	}
    }
    
    //登录入口
    @NeedNotLogon
    @ResponseBody
    @RequestMapping(value="/logout",method = RequestMethod.POST)   
    public Map<String, Object> logoff(HttpServletRequest request,Model model){
    	request.getSession().removeAttribute(Global.LOGON_SESSION_KEY);
    	return ResponseObjManager.getRspMessage();
//    	UsersModel userModel = (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
//        return "redirect:/login";
    }
}
