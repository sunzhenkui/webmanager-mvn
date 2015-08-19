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
import com.imt.rbac.model.GroupAndUserModel;
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.GroupAndUserManager;
import com.imt.rbac.service.UserManager;

@Controller  
@RequestMapping("/rbac/group")

public class GroupAndUserAction {
	private final String CONTEXT_URL = "/rbac/group/";
	@Autowired 
	private GroupAndUserManager tbGroupAndUserManager;
	@Autowired
	private UserManager tbUserManager;
	
	@RequestMapping(value = "/getUserList", method = { RequestMethod.POST,RequestMethod.GET })
    @ResponseBody
	public Map<String, Object> getUserList(HttpServletRequest request,Model model, GroupAndUserModel gu) {
		int pageNo = 1;
		String no = request.getParameter("pno");
		Integer groupId = new Integer(request.getParameter("groupId"));
		gu.setGroupId(groupId);
		
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		int counter = tbGroupAndUserManager.queryForCount(gu);
		Page page = new Page(counter, Global.PAGE_SIZE);
		page.setPage(pageNo);
		List<GroupAndUserModel> list = tbGroupAndUserManager.queryForList(gu,page);
		return ResponseObjManager.getRspList(counter, list);
	}
	
	
	@RequestMapping(value = "/userList", method = { RequestMethod.GET})
	public String groupList(HttpServletRequest request,Model model) {
		String groupId = request.getParameter("groupId");
		model.addAttribute("groupId", groupId);
        return CONTEXT_URL+"userList";
	}
	
    @RequestMapping(value="/addUser",method = {RequestMethod.GET} )   
	public String addUser(HttpServletRequest request,Model model) {
		String groupId = request.getParameter("groupId");
		model.addAttribute("groupId", groupId);
        return CONTEXT_URL+"addUser";
	}
    
    @RequestMapping(value="/addUser",method = {RequestMethod.POST} )   
    @ResponseBody
	public Map<String, Object> addUser(HttpServletRequest request,Model model,GroupAndUserModel gu) {
    	String username = request.getParameter("username");
    	UsersModel userModel = tbUserManager.queryByName(username);
    	if(userModel==null){
        	return ResponseObjManager.getRspErrorMessage("未找到此用户");
    	}
    	else{
    		gu.setUserId(userModel.getUserId());
        	int counter = tbGroupAndUserManager.queryForCount(gu);
        	if(counter>0)
        		return ResponseObjManager.getRspErrorMessage("已经添加过此用户");
        	tbGroupAndUserManager.add(gu);
        	return ResponseObjManager.getRspMessage();
    	}
	}
    
    @RequestMapping(value="/deleteUser",method = {RequestMethod.POST} )   
    @ResponseBody
	public Map<String, Object> deleteUser(Integer id,Model model) {
    	tbGroupAndUserManager.remove(id);
    	return ResponseObjManager.getRspMessage();
	}
}
