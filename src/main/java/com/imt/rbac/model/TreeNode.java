package com.imt.rbac.model;

import java.util.ArrayList;
import java.util.List;

//@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TreeNode implements Comparable<TreeNode>{
	 private String id;          //要显示的子节点的ID  
	 private String text;        //要显示的子节点的 Text  
	 private String iconCls;     //节点的图标  
	 private String parentId;    //父节点的ID  
	 private List<TreeNode>  children;   //孩子节点的List  
	 private Integer nodeType;
	 private String url;
	 private int orderNo;//排序

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TreeNode(){
		 
	 }
	 
	public TreeNode(String id, String text, String iconCls, String parentId,List<TreeNode>children) {  
	       super();  
	       this.id= id;  
	       this.text= text;  
	       this.iconCls= iconCls;  
	       this.parentId= parentId;  
	       this.children= children;  
	  }  
	  
	 public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	
	  public String getId() {  
	       return id;  
	   }  
	  
	   public void setId(String id) {  
	       this.id= id;  
	   }  
	  
	   public String getText() {  
	       return text;  
	    }  
	  
	    public void setText(String text) {  
	       this.text= text;  
	    }  
	  
	    public String getIconCls() {  
	       return iconCls;  
	    }  
	  
	    public void setIconCls(String iconCls) {  
	       this.iconCls= iconCls;  
	    }  
	  
	    public String getParentId()  {
	       return parentId;  
	    }  
	  
	    public void setParentId(String parentId) {  
	       this.parentId= parentId;  
	    }  
	  
	    public List<TreeNode> getChildren() {  
	       return children;  
	    }  
	  
	    public void setChildren(List<TreeNode> children) {  
	       this.children= children;  
	    }  
	  
	    //添加孩子的方法  
	    public void addChild(TreeNode node){  
	       if(this.children == null){  
	           children= new ArrayList<TreeNode>();  
	           children.add(node);  
	       }else{  
	           children.add(node);  
	       }  
	    }  
	

		@Override
		public int compareTo(TreeNode o) {
			int result = (this.getOrderNo() > o.getOrderNo() ? 0 : -1);  
			return result;
		}
}
