package javabase.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {

	public static void main(String[] args) throws Exception{
		ExecutorService es = Executors.newSingleThreadExecutor();
		Future<String> ft = es.submit(new Callable<String>(){
			@Override
			public String call() throws Exception {
				return " test ...";
			}
		});
		
		es.submit(new Runnable(){
			public void run(){
				System.out.println(" run ..."); 
			}
		});
		System.out.println(ft.get());
		es.shutdown();
	}

}
