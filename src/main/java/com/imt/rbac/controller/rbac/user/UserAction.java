package com.imt.rbac.controller.rbac.user;

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
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.UserManager;

@Controller  
@RequestMapping("/rbac/user")
//@SessionAttributes("list") //将ModelMap中属性名为currUser的属性，放到Session属性列表中，以便这个属性可以跨请求访问
public class UserAction{
	private final String CONTEXT_URL = "/rbac/user/";
	@Autowired
	private UserManager tbUserManager;

    
	@RequestMapping(value = "/getUserList", method = { RequestMethod.POST })
    @ResponseBody
	public Map<String, Object> getUserList(HttpServletRequest request,Model model, UsersModel user) {
		int pageNo = 1;
		String no = request.getParameter("pno");
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		int counter = tbUserManager.queryForCount(user);
		Page page = new Page(counter, Global.PAGE_SIZE);
		page.setPage(pageNo);
		
		List<UsersModel> userList = tbUserManager.queryForList(user, page);
		
		return ResponseObjManager.getRspList(counter, userList);
	}
	
    
	@RequestMapping(value = "/userList", method = { RequestMethod.GET})
	public String userList(Model model) {
        return CONTEXT_URL+"userList";
	}
	

    /**
     * 添加用户
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value="/addUser",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addUserPost(HttpServletRequest request,Model model,UsersModel user){
    	if(user.getName().equals("root"))
    		return ResponseObjManager.getRspErrorMessage("不能用此用户名创建用户！");
    	UsersModel query = new UsersModel();
    	query.setName(user.getName());
    	
		int counter = tbUserManager.queryForCount(query);
		if(counter>0) {
	    	return ResponseObjManager.getRspErrorMessage("此用户已经存在！");
		}
		else{
	    	tbUserManager.add(user);
	    	return ResponseObjManager.getRspMessage();
		}
    }
    
    /**
     * 删除用户
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value="/deleteUser",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> delUser(HttpServletRequest request,Model model){
    	String ids = request.getParameter("ids");
    	if(StringUtils.isNotBlank(ids)){
    		String[] gids = StringUtils.split(ids,",");
    		for(String userId:gids){
    	    	UsersModel um = tbUserManager.queryById(new Integer(userId));
    	    	if(um==null || um.getName().equals("root"))
    	        	return ResponseObjManager.getRspMessage(ResponseObjManager.RSP_ERROR,"删除失败！");
    	    	tbUserManager.remove(new Integer(userId));
    		}
    	}
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addUser",method = {RequestMethod.GET} )   
    public String addUserGet(HttpServletRequest request,Model model,UsersModel user){
        return CONTEXT_URL+"addUser";
    }
    
    @RequestMapping(value="/getUserById",method = {RequestMethod.GET} ) 
    @ResponseBody
    public UsersModel addUserGet(HttpServletRequest request,Model model){
    	Integer userId = new Integer(request.getParameter("userId"));
    	return tbUserManager.queryById(userId);
    }
    
    
    @RequestMapping(value="/modifyUser",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> modifyUserPost(Model model,UsersModel user){
    	tbUserManager.update(user);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/modifyUser",method = {RequestMethod.GET} )   
    public String modifyUserPost(Integer id,Model model){
    	UsersModel um = tbUserManager.queryById(id);
    	model.addAttribute("model", um);
        return CONTEXT_URL+"modifyUser";
    }
    

/*    
    @RequestMapping(value="/changePsd",method = {RequestMethod.GET} )   
    @ResponseBody
    public Map<String, Object> changePsd(HttpServletRequest request,Model model){
    	Integer userId = new Integer(request.getParameter("userId"));
    	return ResponseObjManager.getRspMessage();
    }*/
}
