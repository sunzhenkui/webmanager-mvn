package com.imt.rbac.controller.rbac;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.imt.common.ActiveUser;
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.MenusManager;

@Controller  
public class IndexAction {
	@Autowired
	private MenusManager tbMenusManager;
	
    @RequestMapping(value="/index",method = RequestMethod.GET)   
    public String index(HttpServletRequest request,Model model) {
    	//初始化菜单
//    	Integer userId = request.getSession().get
    	UsersModel sessionObj = ActiveUser.getActiveUser(request);
    	Integer userId = sessionObj.getUserId();
    	boolean isAdmin = sessionObj.isAdmin();
    	String html = tbMenusManager.getTreeHtml(userId, isAdmin);
    	model.addAttribute("treeHtml", html);
    	return "index";
    }
}
