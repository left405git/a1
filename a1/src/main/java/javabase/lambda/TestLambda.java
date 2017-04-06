package javabase.lambda;

public class TestLambda {

	public static void runTest( TestI ti){
		ti.test1(); 
	}
	
	public static void main(String[] args){
		
		runTest(  ()-> System.out.println("lalla")  );
	}
}
