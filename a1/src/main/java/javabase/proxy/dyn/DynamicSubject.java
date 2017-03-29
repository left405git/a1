package javabase.proxy.dyn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicSubject implements InvocationHandler {

	private Object target;
	
	public DynamicSubject(){}
	
	public Object bind(Object target){
		this.target = target;
		return Proxy.newProxyInstance(target.getClass().getClassLoader()	, target.getClass().getInterfaces(), this);
	}
	
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("dynamic operation before real operation"); 
		method.invoke(target, args);
		System.out.println("dynamic operation after real operation"); 
		return null;
	}

	
	
	
}
