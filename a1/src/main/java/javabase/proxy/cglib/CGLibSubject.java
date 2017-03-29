package javabase.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLibSubject implements MethodInterceptor  {
	private Object target;
	
	/**
	 * 创建代理对象
	 * @return
	 */
	public Object getInstance(Object target){
		this.target = target;
		Enhancer enhancer = new Enhancer();  
		enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);  
        // 创建代理对象  
        return enhancer.create();
	}
	
	@Override
	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("cglib before real operation");  
        proxy.invokeSuper(object, args);  
        System.out.println("cglib after real operation");  
		return null;
	}


}
