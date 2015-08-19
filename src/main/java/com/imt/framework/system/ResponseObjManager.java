package com.imt.framework.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseObjManager {
	public static int RSP_SUCESS = 0;
	public static int RSP_ERROR = 1;
	
	public static Map<String, Object> getRspMessage(int code,String msg){
		Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
		 jsonMap.put("code", code);
        jsonMap.put("msg", msg);
        return jsonMap;
	}
	
	public static Map<String, Object> getRspErrorMessage(String errorMsg){
		return getRspMessage(RSP_ERROR, errorMsg);
	}
	
	public static Map<String, Object> getRspMessage(){
		return getRspMessage(0,"操作成功！");
	}
	
	public static Map<String, Object> getRspList(int total,List<?> list){
		Map<String, Object> jsonMap = new HashMap<String, Object>();//定义map  
		 jsonMap.put("total", total);//total键 存放总记录数，必须的  
        jsonMap.put("rows", list);//rows键 存放每页记录 list  
        return jsonMap;
	}
}
