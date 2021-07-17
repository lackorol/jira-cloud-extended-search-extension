package com.mkyong.aop;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

//before a method is invoke
public class HijackBeforeMethod implements MethodBeforeAdvice
{
	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("HijackBeforeMethod : Before method hijacked!");
	}
	
}
