package com.imt.rbac.controller.rbac.department;

import java.util.ArrayList;
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
import com.imt.rbac.model.DepartmentModel;
import com.imt.rbac.model.TreeNode;
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.DepartmentManger;

@Controller  
@RequestMapping("/rbac/department")
public class DepartmentAction {
	private final String CONTEXT_URL = "/rbac/user/";
	@Autowired
	private DepartmentManger departmentManger;
	
	@RequestMapping(value = "/getDepList", method = { RequestMethod.POST })
    @ResponseBody
	public Map<String, Object> getDepList(HttpServletRequest request,Model model, DepartmentModel dep) {
		int pageNo = 1;
		String no = request.getParameter("pno");
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		int counter = departmentManger.queryForCount(dep);
		Page page = new Page(counter, Global.PAGE_SIZE);
		page.setPage(pageNo);
		List<DepartmentModel> userList = departmentManger.queryForList(dep, page);
		return ResponseObjManager.getRspList(counter, userList);
	}
	
	@RequestMapping(value = "/depList", method = { RequestMethod.GET})
	public String depList(Model model) {
        return CONTEXT_URL+"depList";
	}
	
    @RequestMapping(value="/getDepMenuData",method = {RequestMethod.GET,RequestMethod.POST})   
    @ResponseBody
    public List<TreeNode> getDepMenusData(HttpServletRequest request){
    	return departmentManger.getTreeNodes();
    }
	
    @RequestMapping(value="/addDep",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addDepPost(HttpServletRequest request,Model model,DepartmentModel dep){
    	departmentManger.add(dep);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addDep",method = {RequestMethod.GET} )   
    public String addDepGet(HttpServletRequest request,Model model,DepartmentModel dep){
        return CONTEXT_URL+"addDep";
    }
    
    @RequestMapping(value="/deleteDep",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> delDep(HttpServletRequest request,Model model){
    	String ids = request.getParameter("ids");
    	if(StringUtils.isNotBlank(ids)){
    		String[] gids = StringUtils.split(ids,",");
    		for(String depId:gids){
    	    	departmentManger.remove(new Integer(depId));
    		}
    	}
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/modifyDep",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> modifyDepPost(Model model,DepartmentModel dep){
    	departmentManger.update(dep);
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/modifyDep",method = {RequestMethod.GET} )   
    public String modifyDepPost(Integer id,Model model){
    	DepartmentModel um = departmentManger.queryById(id);
    	model.addAttribute("model", um);
        return CONTEXT_URL+"modifyDep";
    }
}
