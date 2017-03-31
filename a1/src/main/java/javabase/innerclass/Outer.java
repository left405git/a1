package javabase.innerclass;

public class Outer {
	private String msg = "I'm an outer";
	public Outer( ){
		new Inner();
		System.out.println("new a outer");
	}
	private class Inner{
		public Inner(){
			System.out.println("new a inner in "+Outer.this.msg); // 注意Outer.this 指的是 Outer对象
		}
	}
	
	public static void main(String[] args){
		new Outer();
	}
	
}
