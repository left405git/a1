package javabase.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorCompletionTest {

	public static void main(String[] args) throws Exception{
		ExecutorService es = Executors.newFixedThreadPool(4);
		CompletionService<String> cs = new ExecutorCompletionService<String> (es) ;
		
		for(int i=0; i<10; i++){
			final int task = i;
			cs.submit(new Callable<String>(){
				@Override
				public String call() throws Exception {
					return " thread:"+Thread.currentThread().getName()+" task:"+task;
				}
			});
		}
		for(int i=0; i<10; i++){
			System.out.println("take"+cs.take().get()); 
		}
		es.shutdown();
	}

}
