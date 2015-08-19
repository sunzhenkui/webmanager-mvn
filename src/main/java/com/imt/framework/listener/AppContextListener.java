/*
 * 文件名： AppContextListener.java
 * 
 * 创建日期： 2009-3-19
 *
 * Copyright(C) 2009, by xiaozhi.
 *
 * 原始作者: <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 */
package com.imt.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.imt.common.Global;
import com.imt.rbac.service.MenusManager;
import com.imt.rbac.service.DictionaryManager;


public class AppContextListener implements ServletContextListener {

	private static final Log logger = LogFactory.getLog(AppContextListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("系统启动");
			}
			Global._ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
			
			//初始化菜单cache
			MenusManager menusManager = (MenusManager)Global._ctx.getBean("tbMenusManager");
			menusManager.putMenusToCache();
			logger.info("menus map cache is : " +Global.dictionaryMap.size());
			
			//初始化字典cache
			DictionaryManager dictonaryManager = (DictionaryManager)Global._ctx.getBean("dictionaryManager");
			dictonaryManager.dictionaryInit();
			logger.info("dictionary map cache is : " +Global.dictionaryMap.size());
			
			logger.info("初始化权限");
			logger.info("系统启动完毕");
			
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isErrorEnabled()) {
				logger.error("初始化权限错误", e);
			}
			if (logger.isInfoEnabled()) {
				logger.info("系统没有初始化数据库");
			}
		}
	}
}
