package com.imt.rbac.controller.helper.Interceptor;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;

import com.imt.common.Global;
import com.imt.framework.system.SystemContext;
import com.imt.rbac.model.UsersModel;

public class VisitorInterceptor implements MethodInterceptor {
	public Object invoke(MethodInvocation invocation) throws Throwable {
//        System.out.println("执行了拦截器。。。。");


        Annotation annotation = invocation.getMethod().getAnnotation(NeedNotLogon.class); 
//        Annotation annRspBody = invocation.getMethod().getAnnotation(ResponseBody.class);
        
		if (annotation!=null) { // 有指定注解
		// String value = null;
//			System.out.println("方法有注解");
			return invocation.proceed(); // 直接授权
		} 
		else { // 类方法没有自定义注解，直接执行该方法
//			System.out.println("方法无注解，验证权限");
            HttpServletRequest request = null;
			HttpServletResponse response = null;
			request = SystemContext.getRequest();
			response = SystemContext.getResponse();
			if( request.getSession().getAttribute(Global.LOGON_SESSION_KEY)==null ){
				return "login";
			}else {
				UsersModel userModel = (UsersModel)request.getSession().getAttribute(Global.LOGON_SESSION_KEY);
				if (userModel.isAdmin()) {// 超管不需要验证权限
					return invocation.proceed();
				}
				if(userModel.getIsEnable()==Global.BOOL_FALSE)//不可用
					return null;
				
				//没有权限
				String requestUri = request.getRequestURI();
				String contextPath = request.getContextPath();
				String url = requestUri.substring(contextPath.length());
//				String reqMethod = request.getMethod();//get post
				
				Integer userId = userModel.getUserId();
				HashMap<String,String> perMap = Global.userPermissionsMap.get(userId);

				if ( perMap.get(url)!=null || StringUtils.equals(url, "/index")) {
					return invocation.proceed();
				} else {
					//request.setAttribute("msg", "您没有访问此资源的权限！<br/>请联系超管赋予您<br/>[" + url + "]<br/>的资源访问权限！");
/*					if(StringUtils.equalsIgnoreCase(reqMethod, "GET"))
						request.getRequestDispatcher("/error/noSecurity.jsp").forward(request, response);
					else{
					}
					return invocation.proceed();*/
					return null;
				}
			}
		}
	}
}
