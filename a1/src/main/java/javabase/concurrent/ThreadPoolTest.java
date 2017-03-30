package javabase.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

	public static void main(String[] args) throws InterruptedException{
		ExecutorService threadPool = Executors.newCachedThreadPool();
		for(int i = 0 ; i<10;  i++){
			Thread.sleep(10);
			final int  task = i;
			threadPool.execute(new Runnable(){
				public void run(){
					System.out.println("SLEEP :THREAD:"+Thread.currentThread().getName()+" :: task:"+task);
				}
			});
		}
		for(int i = 0 ; i<10;  i++){
			final int  task = i;
			threadPool.execute(new Runnable(){
				public void run(){
					System.out.println("THREAD:"+Thread.currentThread().getName()+" :: task:"+task);
				}
			});
		}
		
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		for(int i=0; i<10 ; i++){
			final int task = i;
			fixedThreadPool.execute(new Runnable(){
				public void run(){
					System.out.println("FIXED THREAD:"+Thread.currentThread().getName()+" :: task:"+task);
				}
			});
		}
	}

}
