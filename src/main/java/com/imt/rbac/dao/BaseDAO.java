package com.imt.rbac.dao;

import com.imt.framework.system.Page;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseDAO<T> {
	protected abstract String getNamespace();
	@Autowired
	public SqlSessionTemplate sqlSession; 

	public void add(T t)  {
		try {
			sqlSession.insert(getNamespace() + "."+"add", t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(T t)  {
		try {
			sqlSession.update(getNamespace() + "."+"update", t) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void delete(Object id) {
		try {
			sqlSession.delete(getNamespace() + "."+"delete", id) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteByObj(Object obj) {
		try {
			sqlSession.delete(getNamespace() + "."+"deleteByObj", obj) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int queryForCount(T t){
		int counter = 0;
		try {
			counter = sqlSession.selectOne(getNamespace() + "."+"getCount",t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	public List<T> queryForList(T t) {
		List<T> list = null;
		try {
			list = sqlSession.selectList(getNamespace() + "."+"queryList",t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<T> queryForList(T t,Page page) {
		List<T> list = null;
		try {
			RowBounds rowBounds = new RowBounds(page.getFirstItemPos(), (int) page.getMaxItemNum());
			list = sqlSession.selectList(getNamespace() + "."+"queryList",t,rowBounds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public T queryById(Object id) {
		T t = null;
		try {
			t = sqlSession.selectOne(getNamespace() + "."+"get",id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
