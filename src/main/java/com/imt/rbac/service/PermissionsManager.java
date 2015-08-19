package com.imt.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.common.Global;
import com.imt.framework.system.Page;
import com.imt.rbac.dao.PermissionsDAO;
import com.imt.rbac.dao.UserAndRoleDAO;
import com.imt.rbac.model.GroupAndRoleModel;
import com.imt.rbac.model.GroupAndUserModel;
import com.imt.rbac.model.MenusModel;
import com.imt.rbac.model.PermissionsModel;
import com.imt.rbac.model.UserAndRoleModel;

@Service("tbPermissionsManager")
public class PermissionsManager {
	@Autowired 
	private PermissionsDAO tbPermissionsDAO;
	@Autowired 
	private UserAndRoleDAO tbUserAndRoleManager;
	@Autowired 
	private GroupAndRoleManager tbGroupAndRoleManager;
	
	public void add(PermissionsModel model)  {
		tbPermissionsDAO.add(model);
	}
	
	public void update(PermissionsModel model)  {
		tbPermissionsDAO.update(model);
	}
	
	public PermissionsModel queryById(Integer singerId) {
		return tbPermissionsDAO.queryById(singerId);
	}
	
	public int queryForCount(PermissionsModel model){
		return tbPermissionsDAO.queryForCount(model);
	}
	
	public List<PermissionsModel> queryForList(PermissionsModel model) {
		return tbPermissionsDAO.queryForList(model);
	}
	
	public List<PermissionsModel> queryForList(PermissionsModel model,Page page) {
		return tbPermissionsDAO.queryForList(model,page);
	}
	
	public List<PermissionsModel> queryByRoleId(PermissionsModel model) {
		return ((PermissionsDAO)tbPermissionsDAO).queryByRoleId(model);
	}
	
	
	public void remove(Integer id)  {
		tbPermissionsDAO.delete(id);
	}
	
	public void deleteForResId(PermissionsModel model)  {
		tbPermissionsDAO.deleteForResId(model);
	}
	
    /**
     * 登录缓存初始化
     * @param userId
     */
/*	public void permissionInit(Integer userId){
		List<Integer> menuIds = new ArrayList<Integer>();
		String url = null;
		String name = null;
//		HashMap<String, String> perMap = new HashMap<String, String>();
		HashMap<String, String> perMap = Global.userPermissionsMap.get(userId);
		if(perMap==null)
			perMap = new HashMap<String, String>();
		
		UserAndRoleModel urQueryModel = new UserAndRoleModel(userId);
		List<UserAndRoleModel> urList = tbUserAndRoleManager.queryForList(urQueryModel);
		for( UserAndRoleModel urModel:urList ){
			List<PermissionsModel> perList = this.queryByRoleId(new PermissionsModel(urModel.getRoleId()));
			for(PermissionsModel perModel:perList){
				url = perModel.getResourceModel().getUrl();
				name = perModel.getResourceModel().getName();

				if( perMap.get(url)==null ){//去重
					perMap.put(url, name);
					Integer menuId = perModel.getResourceModel().getMenuId();
					if(!menuIds.contains(menuId))
						menuIds.add(menuId);
				}
			}
		}
		
		List<Integer> rstList = new ArrayList<Integer>();
		for(Integer menuId:menuIds){
			rstList.add(menuId);
			getShowMenuIds( menuId, rstList);
		}
		
		Global.userMenusMap.put(userId, rstList);
		Global.userPermissionsMap.put(userId, perMap);
	}*/
	
	public void permissionInit(Integer userId){
		permissionInit(userId,true);
	}
	
	public void permissionInit(Integer userId,boolean isReload){
		if(isReload){
			Global.userMenusMap.remove(userId);
			Global.userPermissionsMap.remove(userId);
		}
		//临时的cache，避免循环查找带有menuId的list，用内存换cpu
		HashMap<Integer, String> tmpMap = new HashMap<Integer, String>();
		this.userPermissionInit(userId,tmpMap);
		this.groupPermissionInit(userId,tmpMap);
	}

	/**
	 * 群组权限初始化
	 * @param userId
	 */
	private void groupPermissionInit(Integer userId,HashMap<Integer, String> tmpMap){
		List<Integer> menuIds = new ArrayList<Integer>();
		String url = null;
		String name = null;
		HashMap<String, String> perMap = Global.userPermissionsMap.get(userId);
		if(perMap==null)
			perMap = new HashMap<String, String>();
		
		GroupAndUserModel guModel = new GroupAndUserModel();
		guModel.setUserId(userId);
		List<GroupAndRoleModel> grList = tbGroupAndRoleManager.queryListForUID(userId);
		
		for(GroupAndRoleModel grModel:grList){
			List<PermissionsModel> perList = this.queryByRoleId(new PermissionsModel(grModel.getRoleId()));
			for(PermissionsModel perModel:perList){
				url = perModel.getResourceModel().getUrl();
				name = perModel.getResourceModel().getName();

				if( perMap.get(url)==null ){//去重
					perMap.put(url, name);
					Integer menuId = perModel.getResourceModel().getMenuId();
					if(!menuIds.contains(menuId))
						menuIds.add(menuId);
				}
			}
		}

		List<Integer> rstList =  Global.userMenusMap.get(userId);
		if(rstList==null)
			rstList = new ArrayList<Integer>();
		
		for(Integer menuId:menuIds){
			if(!rstList.contains(menuId)){
				rstList.add(menuId);
				getShowMenuIds( menuId, rstList,tmpMap);
			}
		}
		
		Global.userMenusMap.put(userId, rstList);
		Global.userPermissionsMap.put(userId, perMap);
	}
	
	/**
	 * 用户权限初始化
	 * @param userId
	 */
	private void userPermissionInit(Integer userId,HashMap<Integer, String> tmpMap){
		List<Integer> menuIds = new ArrayList<Integer>();
		String url = null;
		String name = null;
		HashMap<String, String> perMap = new HashMap<String, String>();
		
		UserAndRoleModel urQueryModel = new UserAndRoleModel(userId);
		List<UserAndRoleModel> urList = tbUserAndRoleManager.queryForList(urQueryModel);
		for( UserAndRoleModel urModel:urList ){
			List<PermissionsModel> perList = this.queryByRoleId(new PermissionsModel(urModel.getRoleId()));
			for(PermissionsModel perModel:perList){
				url = perModel.getResourceModel().getUrl();
				name = perModel.getResourceModel().getName();

				if( perMap.get(url)==null ){//去重
					perMap.put(url, name);
					Integer menuId = perModel.getResourceModel().getMenuId();
					if(!menuIds.contains(menuId))
						menuIds.add(menuId);
				}
			}
		}
		
		List<Integer> rstList = new ArrayList<Integer>();
		for(Integer menuId:menuIds){
			rstList.add(menuId);
			getShowMenuIds( menuId, rstList,tmpMap);
		}
		
		Global.userMenusMap.put(userId, rstList);
		Global.userPermissionsMap.put(userId, perMap);
	}

	
	//递归查找菜单
	private void getShowMenuIds(Integer menuId, List<Integer> rstList,HashMap<Integer, String> tmpMap) {
		if(tmpMap.get(menuId)!=null) return;//去重
		
		MenusModel menuModel = Global.menusMap.get(menuId);
		Integer parentId = menuModel.getParentId();
		
		if (!rstList.contains(parentId)&&parentId!=MenusModel.DEFAULT_PARENT_ID ) {
			rstList.add(parentId);
			this.getShowMenuIds(parentId, rstList ,tmpMap);
		}
	}
}
