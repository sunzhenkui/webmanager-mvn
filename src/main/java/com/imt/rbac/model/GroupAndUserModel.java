package com.imt.rbac.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GroupAndUserModel {
	private Integer id;
	private Integer userId;
	private Integer groupId;
	private UserGroupsModel group;
	private UsersModel user;
	
	public UserGroupsModel getGroup() {
		return group;
	}
	public void setGroup(UserGroupsModel group) {
		this.group = group;
	}
	public UsersModel getUser() {
		return user;
	}
	public void setUser(UsersModel user) {
		this.user = user;
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
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
