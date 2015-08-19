
package com.imt.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imt.framework.system.SystemContext;


/**
 * 通过ThreadLocal方式将session保存，Spring AOP中获得Session对象
 * @author zhenkui
 *
 */

public class SystemContextFilter implements Filter {

	public void destroy() {
	}

    public void doFilter(ServletRequest arg0, ServletResponse arg1,  
            FilterChain arg2) throws IOException, ServletException {  
        SystemContext.setRequest((HttpServletRequest) arg0);  
        SystemContext.setResponse((HttpServletResponse) arg1);  
        arg2.doFilter(arg0, arg1);  
    }  

	public void init(FilterConfig arg0) throws ServletException {
	}

}
