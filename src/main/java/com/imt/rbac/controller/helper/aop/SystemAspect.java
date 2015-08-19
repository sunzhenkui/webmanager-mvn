package com.imt.rbac.controller.helper.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SystemAspect {
//	private static final Log logger = LogFactory.getLog(SystemAspect.class);
/*
//	@Before("execution(! * com.imt.rbac.service..*(..))")
	@Before("execution(* com.imt.rbac.service..*(..))")
	private void test(){
		System.out.println("start AOP1.......");
	}*/

	@Before("@annotation(com.imt.rbac.controller.helper.aop.NeedNotLogon)")
	private void test1(){
		System.out.println("start AOP.......");
	}
}
