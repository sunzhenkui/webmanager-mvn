package com.imt.rbac.controller.rbac.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imt.rbac.controller.helper.Interceptor.NeedNotLogon;
import com.imt.rbac.model.UsersModel;


@Controller
@RequestMapping("/test/demo")
public class TestAction {
    @RequestMapping(value="/test",method = RequestMethod.GET)   
    public String uploadOk(Model model) {
        return "/test/demo/test";
    }
    
    @RequestMapping(value = "/getUsers", method = { RequestMethod.POST,RequestMethod.GET })
    @NeedNotLogon
    @ResponseBody
    public List<UsersModel> getUsers(HttpServletRequest request,Model model) {
    	List<UsersModel> list = new ArrayList<UsersModel>();
    	UsersModel user = new UsersModel();
    	user.setUserId(1);
    	user.setName("test1");
    	user.setMobile("139101111134");
    	list.add(user);
    	
    	user = new UsersModel();
    	user.setUserId(2);
    	user.setName("test2");
    	user.setMobile("13910113561");
    	list.add(user);

    	return list;
    }
    
    @RequestMapping(value = "/getUserById", method = { RequestMethod.POST,RequestMethod.GET })
    @NeedNotLogon
    @ResponseBody
    public UsersModel getUserById(HttpServletRequest request,Model model) {
    	String id = request.getParameter("1");
    	UsersModel user = new UsersModel();
    	user.setUserId(new Integer(id));
    	user.setName("test1");
    	user.setMobile("139101111134");
    	return user;
    }
    
    @RequestMapping(value = "/login", method = { RequestMethod.POST,RequestMethod.GET })
    @NeedNotLogon
    @ResponseBody
    public HashMap<String,Boolean> login(){
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	HashMap<String,Boolean> map = new HashMap<String,Boolean>();
    	map.put("retInfo", true);
    	return map;
    }
}
