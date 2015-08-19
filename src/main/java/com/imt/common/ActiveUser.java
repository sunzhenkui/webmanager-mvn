package com.imt.common;

import javax.servlet.http.HttpServletRequest;

import com.imt.rbac.model.UsersModel;

public class ActiveUser{
	public static UsersModel getActiveUser(HttpServletRequest request){
		return (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
	}
}
