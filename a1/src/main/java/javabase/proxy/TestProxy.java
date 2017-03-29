package javabase.proxy;

import javabase.proxy.cglib.CGLibSubject;
import javabase.proxy.dyn.DynamicSubject;
import javabase.proxy.stt.Subject;
import javabase.proxy.stt.SubjectImpl;
import javabase.proxy.stt.SubjectProxy;

public class TestProxy {

	public static void testStaticProxy(){
		Subject proxy = new SubjectProxy(new SubjectImpl());
		proxy.operate(); 
	}
	
	public static void testDynamicProxy(){
		Subject proxy = (Subject)new DynamicSubject().bind(new SubjectImpl());
		proxy.operate();
	}
	
	public static void cglibProxy(){
		Subject proxy =(Subject)new CGLibSubject().getInstance( new SubjectImpl() );
		proxy.operate();
	}
	
	public static void main(String[] args) {
		//testStaticProxy();
		//testDynamicProxy();
		cglibProxy();
	}

	 
}
