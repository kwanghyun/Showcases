package concurrency.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactory4Test implements ThreadFactory {

	/**
	 * maintains a count of created threads; test cases can then verify the
	 * number of threads created during a test run. TestingThreadFactory could
	 * be extended to return a custom Thread that also records when the thread
	 * terminates, so that test cases can verify that threads are reaped in
	 * accordance with the execution policy.
	 * 
	 * @param args
	 */
	public final AtomicInteger numCreated = new AtomicInteger();

	private final ThreadFactory factory = Executors.defaultThreadFactory();

	public Thread newThread(Runnable r) {
		numCreated.incrementAndGet();
		return factory.newThread(r);
	}
	
	public int getNumOfCreatedThread(){
		return this.numCreated.get();
	}
	
}
