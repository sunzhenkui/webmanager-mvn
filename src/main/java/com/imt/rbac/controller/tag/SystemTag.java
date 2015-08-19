package com.imt.rbac.controller.tag;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

import com.imt.common.Global;
import com.imt.framework.system.SystemContext;
import com.imt.rbac.model.UsersModel;


public class SystemTag extends TagSupport{
	private static final long serialVersionUID = -4454135248513152920L;
    public static boolean permission(String url){ 
    	HttpServletRequest request = SystemContext.getRequest();
		if( request.getSession().getAttribute(Global.LOGON_SESSION_KEY)==null ){
			return false;
		}
    	UsersModel userModel = (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
    	Integer userId = userModel.getUserId();
		if (userModel.isAdmin()) {// 超管不需要验证权限
			return true;
		}
    	HashMap<String,String> perMap = Global.userPermissionsMap.get(userId);
    	if(perMap==null ||  perMap.get(url)==null)
    		return false;
    	else
    		return true;
    }  
}
