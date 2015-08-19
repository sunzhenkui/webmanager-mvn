package com.imt.rbac.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserAndRoleModel {
	private Integer id;
	private Integer userId;
	private Integer roleId;
	private RolesModel role;
	
	public RolesModel getRole() {
		return role;
	}

	public void setRole(RolesModel role) {
		this.role = role;
	}

	public UserAndRoleModel(){}
	
	public UserAndRoleModel(Integer userId){
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
