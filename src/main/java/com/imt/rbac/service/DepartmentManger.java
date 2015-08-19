package com.imt.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imt.framework.system.Page;
import com.imt.rbac.dao.DepartmentDAO;
import com.imt.rbac.model.DepartmentModel;
import com.imt.rbac.model.TreeNode;

@Service("departmentManger")
public class DepartmentManger {
	@Autowired 
	private DepartmentDAO departmentDAO;
	public void add(DepartmentModel model)  {
		departmentDAO.add(model);
	}
	
	public void update(DepartmentModel model)  {
		departmentDAO.update(model);
	}
	
	public DepartmentModel queryById(Integer id) {
		return departmentDAO.queryById(id);
	}
	
	public int queryForCount(DepartmentModel model){
		return departmentDAO.queryForCount(model);
	}
	
	public List<DepartmentModel> queryForList(DepartmentModel model,Page page) {
		return departmentDAO.queryForList(model,page);
	}
	
	public List<DepartmentModel> queryForList(DepartmentModel model) {
		return departmentDAO.queryForList(model);
	}
	public void remove(Integer id)  {
		departmentDAO.delete(id);
	}
	
	public List<TreeNode> getTreeNodes(){
		HashMap<String, TreeNode> map = new HashMap<String, TreeNode>();
		
		List<TreeNode> nodeList = new ArrayList<TreeNode>();  

		List<DepartmentModel> list = this.queryForList(new DepartmentModel());
		for(DepartmentModel model:list){
			TreeNode node = new TreeNode();
			node.setText(model.getName());
			node.setId(model.getDepId().toString());
			map.put(model.getDepId().toString(), node);
		}
		
		for(DepartmentModel model:list){
			int parentId = model.getParentId().intValue();
			int id = model.getDepId().intValue();
			
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
}
