package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.MenusModel;

@Repository("tbMenuDAO")
public class MenusDAO  extends BaseDAO<MenusModel>{
	private String namespace = "TbMenus";
	@Override
	protected String getNamespace() {
		return namespace;
	}
}
