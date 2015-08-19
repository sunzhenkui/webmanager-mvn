package com.imt.framework.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.support.XmlWebApplicationContext;


public class SpringXmlWebApplicationContext extends XmlWebApplicationContext {

	public String[] getDefaultConfigLocations() {
		List<String> configList = new ArrayList<String>();
		try {
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources;
			resources = resolver.getResources("classpath*:config/spring-sys*.xml");
			for(Resource resource:resources){
				configList.add(resource.getURL().toExternalForm());
				System.out.println(resource.getURL().toExternalForm());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return configList.toArray(new String[0]);
/*		String[] returnStringArray = ConfigLocationUtil.getConfigLocationArray(
				"spring", this.getServletContext().getRealPath(""));
		System.out.println(returnStringArray);*/

	}

}
