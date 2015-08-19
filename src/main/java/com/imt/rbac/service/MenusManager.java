package com.imt.rbac.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.common.Global;
import com.imt.framework.system.Page;
import com.imt.rbac.dao.BaseDAO;
import com.imt.rbac.model.MenusModel;
import com.imt.rbac.model.TreeNode;

@Service("tbMenusManager")
public class MenusManager {
	@Autowired 
	private BaseDAO<MenusModel> tbMenuDAO;
	public void add(MenusModel model)  {
		tbMenuDAO.add(model);
	}
	
	public void update(MenusModel model)  {
		tbMenuDAO.update(model);
	}
	
	public MenusModel queryById(Integer menuId) {
		return tbMenuDAO.queryById(menuId);
	}
	
	public int queryForCount(MenusModel model){
		return tbMenuDAO.queryForCount(model);
	}
	
	public List<MenusModel> queryForList(MenusModel model,Page page) {
		return tbMenuDAO.queryForList(model,page);
	}
	
	public List<MenusModel> queryForList(MenusModel model) {
		return tbMenuDAO.queryForList(model);
	}
	
	public void remove(Integer id)  {
		tbMenuDAO.delete(id);
	}
	
	/**
	 * 初始化菜单信息
	 */
	public void putMenusToCache(){
		List<MenusModel> menusList = this.queryForList(new MenusModel());
		Global.menusMap.clear();
		for(MenusModel model:menusList){
			Global.menusMap.put(model.getMenuId(), model);
		}
	}

	/**
	 * 返回节点树模型
	 * @return
	 */
	public List<TreeNode> getTreeNodes(Integer userId,boolean isAdmin){
		List<Integer> allowMenus = Global.userMenusMap.get(userId);
		HashMap<String, TreeNode> map = new HashMap<String, TreeNode>();
		
		List<TreeNode> nodeList = new ArrayList<TreeNode>();  
		for(Entry<Integer, MenusModel> entry:Global.menusMap.entrySet()){
			TreeNode node = new TreeNode();
			node.setId(entry.getValue().getMenuId().toString());
			if(entry.getValue().getParentId().intValue()!=0)
				node.setParentId(entry.getValue().getParentId().toString());
			node.setText(entry.getValue().getName());
			node.setIconCls("");
			node.setNodeType(entry.getValue().getNodeType());
			node.setUrl(entry.getValue().getUrl());
			node.setOrderNo(entry.getValue().getOrderNo().intValue());
			map.put(entry.getValue().getMenuId().toString(), node);
		}
	
		for(Entry<Integer, MenusModel> entry:Global.menusMap.entrySet()){
			MenusModel menu = entry.getValue();
			int parentId = menu.getParentId().intValue();
			int id = menu.getMenuId().intValue();
			
			//不包含在许可url中，则过滤
			if( !isAdmin && allowMenus!=null &&  !allowMenus.contains(new Integer( id )))
				continue;
			
			if( parentId==0 ){
				nodeList.add(map.get(String.valueOf(id)));
			}
			else{
	              String pidString = String.valueOf(parentId);  
                  TreeNode pnode = (TreeNode)map.get(pidString);  
                  TreeNode cnode=(TreeNode)map.get(String.valueOf(id));  
                  pnode.addChild(cnode);  
			}
		}
		return nodeList;
	}
/*	
	public List<TreeNode> getTreeData(Integer userId,boolean isAdmin){
		List<TreeNode> list = getTreeNodes(userId,isAdmin);
		return list;
	}*/
	
	
	//使用输出html代码方式
	public String getTreeHtml( Integer userId,boolean isAdmin ){
		StringBuffer bf = new StringBuffer();
		List<TreeNode> list = getTreeNodes(userId,isAdmin);
		
		//排序
		Collections.sort(list);
		
		if(list.size()==MenusModel.ROOT_MENU) return bf.toString();
		
		for(TreeNode rootNode:list){
			List<TreeNode> childrenList = rootNode.getChildren();
			if(childrenList!=null && childrenList.size()>0)
				Collections.sort(childrenList);
			
			bf.append("<div title='"+rootNode.getText()+"' style='padding:10px;' >" + "\r\n");
			setTree(bf,childrenList);
			bf.append("</div>" + "\r\n");
		}

		return bf.toString();
	}
	
	private void setTree(StringBuffer bf , List<TreeNode> childrenList ){
		if(childrenList==null) return;
		
		bf.append("<ul  class='easyui-tree'  data-options=\"method:'get',animate:true,lines:true\">" + "\r\n");
		for(TreeNode node:childrenList){
/*			String style = "";
			if(node.getNodeType().intValue()==MenusModel.TREE_ROOT_MENU)	
				style = " class='easyui-tree'";*/
//			bf.append("<ul "+style+">" + "\r\n");
			bf.append("<li>" + "\r\n");
			
			if(node.getNodeType()==MenusModel.TREE_NODE_MENU){//叶子节点
				bf.append("<span><a href='#' onclick=\"opentabs('"+node.getText()+"','"+node.getUrl()+"')\">"+node.getText()+"</a></span>" + "\r\n");	
//				bf.append("<span onclick=\"opentabs('"+node.getText()+"','"+node.getUrl()+"')\">"+node.getText()+"</span>" + "\r\n");	
			}
			else{
				bf.append("<span>"+node.getText()+"</span>" + "\r\n");	
			}
			
			bf.append("<span>"+node.getText()+"</span>" + "\r\n");
			
			List<TreeNode> childs = node.getChildren();
			if(childs!=null && childs.size()>0)
				Collections.sort(childs);
			
			setTree(bf,childs);
			bf.append("</li>" + "\r\n");
//			bf.append(" </ul>" + "\r\n");
		}
		bf.append(" </ul>" + "\r\n");
	}
}
