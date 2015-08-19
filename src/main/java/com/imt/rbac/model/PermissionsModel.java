package com.imt.rbac.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PermissionsModel {
	private Integer permissionId;
	private Integer roleId;
	private Integer resourceId;
	private ResourcesModel resourceModel;
	
	public PermissionsModel(){}
	
	public PermissionsModel(Integer roleId){
		this.roleId = roleId;
	}
	public ResourcesModel getResourceModel() {
		return resourceModel;
	}
	public void setResourceModel(ResourcesModel resourceModel) {
		this.resourceModel = resourceModel;
	}
	public Integer getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	} 
	
	@Override
	  public boolean equals(Object obj) {
		if( ((PermissionsModel)obj).getPermissionId().intValue()==this.permissionId )
			return true;
		else
			return false;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
