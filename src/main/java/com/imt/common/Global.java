package com.imt.common;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

import com.imt.rbac.model.MenusModel;


/**
 * 定义平台里需要的全局变量
 */
public class Global  {
	/** Spring的ApplicationContext，使用的时候要自己设置。 */
	public static ApplicationContext _ctx = null;
	
	public static String LOGON_SESSION_KEY= "USER_SESSION";
	
//	public static ConcurrentHashMap<Integer, List<String>> userPermissionsMap = new ConcurrentHashMap<Integer,  List<String>>();//用户登录资源map
	
	public static ConcurrentHashMap<Integer, HashMap<String,String>> userPermissionsMap = new ConcurrentHashMap<Integer,  HashMap<String,String>>();//用户登录资源map
	
	public static ConcurrentHashMap<Integer, List<Integer>> userMenusMap = new ConcurrentHashMap<Integer,  List<Integer>>();//用户菜单map

	public static ConcurrentHashMap<Integer, MenusModel> menusMap = new ConcurrentHashMap<Integer,  MenusModel>();//所有菜单模型
	
	public static HashMap<String, HashMap<String,String>> dictionaryMap = new HashMap<String, HashMap<String,String>>();//字典模型
	
	public static int PAGE_SIZE = 50;
	
	public static int BOOL_TRUE = 1;
	public static int BOOL_FALSE = 0;
	
	public final static int DEFAULT_PARENT_ID = 0;//默认的parentId的值
}
