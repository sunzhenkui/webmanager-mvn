package com.imt.rbac.controller.rbac.menu;

import com.imt.common.Global;
import com.imt.framework.system.Page;
import com.imt.framework.system.ResponseObjManager;
import com.imt.rbac.model.MenusModel;
import com.imt.rbac.model.TreeNode;
import com.imt.rbac.model.UsersModel;
import com.imt.rbac.service.MenusManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller  
@RequestMapping("/rbac/menu")
public class MenuAction {
	private final String CONTEXT_URL = "/rbac/menu/";
	@Autowired
	private MenusManager tbMenusManager;
	
    @RequestMapping(value="/getMenuList",method = {RequestMethod.POST,RequestMethod.GET} )   
    @ResponseBody
    public Map<String, Object> getMenuList(HttpServletRequest request,Model model,MenusModel menu){
		int pageNo = 1;
		String no = request.getParameter("pno");
		if(StringUtils.isNotBlank(no))
			pageNo = Integer.parseInt(no);
		
		
		List<MenusModel> list = new ArrayList<MenusModel>();
		int counter = 0;
		if(menu.getParentId()!=null){
			counter = tbMenusManager.queryForCount(menu);
			Page page = new Page(counter, Global.PAGE_SIZE);
			page.setPage(pageNo);
			list = tbMenusManager.queryForList(menu, page);
		}
		return ResponseObjManager.getRspList(counter, list);
    }
    
	@RequestMapping(value = "/menuList", method = { RequestMethod.GET})
	public String menuList(Model model, UsersModel user) {
        return CONTEXT_URL+"menuList";
	}
    
    /**
     * 取得用户菜单
     * @param request
     * @return
     */
    @RequestMapping(value="/getUserMenus",method = {RequestMethod.GET,RequestMethod.POST})   
    @ResponseBody
    public List<TreeNode> getUserMenus(HttpServletRequest request){
    	UsersModel userModel = (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
    	if(userModel!=null)
    		return tbMenusManager.getTreeNodes(userModel.getUserId(), userModel.isAdmin());
    	else{
    		return new ArrayList<TreeNode>();
    	}
    }

    
    @RequestMapping(value="/addMenu",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> addMenuPost(HttpServletRequest request,Model model,MenusModel menu){
    	String parentId = request.getParameter("parentId");
    	if(menu.getParentId()==null) {//添加一个根节点
    		menu.setParentId(MenusModel.DEFAULT_PARENT_ID);
    		menu.setNodeType(MenusModel.ROOT_MENU);
    	}
    	tbMenusManager.add(menu);
    	tbMenusManager.putMenusToCache();
    	return ResponseObjManager.getRspMessage();
    }
    
    @RequestMapping(value="/addMenu",method = {RequestMethod.GET} )   
    public String addMenuGet(HttpServletRequest request,Model model){
    	String parentId = request.getParameter("parentId");
    	model.addAttribute("parentId", parentId);
        return CONTEXT_URL+"addMenu";
    }
    
    
    @RequestMapping(value="/modifyMenu",method = {RequestMethod.GET} )   
    public String modifyMenu(Integer id,HttpServletRequest request,Model model){
    	MenusModel menu = tbMenusManager.queryById(id);
    	model.addAttribute("model", menu);
        return CONTEXT_URL+"modifyMenu";
    }
    
    @RequestMapping(value="/modifyMenu",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> modifyMenu(Model model,MenusModel menu){
    	tbMenusManager.update(menu);
    	tbMenusManager.putMenusToCache();
    	return ResponseObjManager.getRspMessage();
    }
    
    
    @RequestMapping(value="/getMenuById",method = {RequestMethod.GET} ) 
    @ResponseBody
    public MenusModel getMenuById(Integer id,Model model){
    	return tbMenusManager.queryById(id);
    }
    
    @RequestMapping(value="/deleteMenu",method = {RequestMethod.POST} )   
    @ResponseBody
    public Map<String, Object> deleteMenu(Integer id,Model model){
    	MenusModel query = new MenusModel();
    	query.setParentId(id);
    	int counter = tbMenusManager.queryForCount(query);
    	if(counter>0)
        	return ResponseObjManager.getRspErrorMessage("请先删除该目录下的节点！");
    	else{
        	tbMenusManager.remove(id);
        	tbMenusManager.putMenusToCache();
        	return ResponseObjManager.getRspMessage();
    	}
    }
}
