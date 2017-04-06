package javabase.generic;

import java.util.List;

public class GenericTest {

	public static void main(String[] args){
		A<String> a = new A<String>();
		a.display("123");
		a.display1(123); 
		a.display1(false); 
	}
}

class T{
	
}

class A<T>{
	T t;
	List<T> getList(){
		
		return null;
	}
	
	<T> List<T> query(){
		return null;
	}
	 public   void display(T t) {
	        System.out.println(t.getClass());
	 }
	 public  <E> void display1(E t) {
	        System.out.println(t.getClass());
	 }
}
