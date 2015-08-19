package com.imt.rbac.dao;

import org.springframework.stereotype.Repository;

import com.imt.rbac.model.DepartmentModel;

@Repository("departmentDAO")
public class DepartmentDAO extends BaseDAO<DepartmentModel>{
	private String namespace = "TbDepartment";
	@Override
	protected String getNamespace() {
		return namespace;
	}
}
