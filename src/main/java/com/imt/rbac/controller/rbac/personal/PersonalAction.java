package com.imt.rbac.controller.rbac.personal;

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
import com.imt.common.util.CryptUtil;
import com.imt.framework.system.ResponseObjManager;
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.UserManager;

@Controller  
@RequestMapping("/rbac/personal")
public class PersonalAction {
	private final String CONTEXT_URL = "/rbac/personal/";
	@Autowired
	private UserManager tbUserManager;
    /**
     * 修改密码
     * @param request
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value="/changePsd",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> changePsd(HttpServletRequest request,Model model,UsersModel user){
    	UsersModel userModel = (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
    	UsersModel um = tbUserManager.queryById(userModel.getUserId());
    	String userPsd = um.getPassword();
    	String newPsd = request.getParameter("newPsd");
    	String oldPsd = request.getParameter("oldPsd");
    	String vPsd = request.getParameter("vPsd");
    	if(!StringUtils.isNotBlank(newPsd) || !StringUtils.isNotBlank(oldPsd) || !StringUtils.isNotBlank(vPsd)){
        	return ResponseObjManager.getRspErrorMessage("输入错误");
    	}
    	if(!StringUtils.equals(vPsd, newPsd)){
    		
        	return ResponseObjManager.getRspErrorMessage("两次输入的密码不同！");
    	}
    	if(!StringUtils.equals(userPsd, CryptUtil.md5Encoder(oldPsd) )){
        	return ResponseObjManager.getRspErrorMessage("原密码错误！");
    	}
    	um.setPassword(newPsd);
    	tbUserManager.update(um);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/modifyPsd",method = {RequestMethod.GET} )   
    public String modifyPsd(HttpServletRequest request,Model model){
        return CONTEXT_URL+"modifyPsd";
    }
}
