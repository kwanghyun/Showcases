package concurrency.showcases;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchExample {

	// Simple framework for timing concurrent execution
	public static long performanceTest(Executor executor, int concurrency,
			final Runnable action) throws InterruptedException {
		final CountDownLatch ready = new CountDownLatch(concurrency);
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch done = new CountDownLatch(concurrency);
		for (int i = 0; i < concurrency; i++) {
			executor.execute(new Runnable() {
				public void run() {
					ready.countDown(); // Tell timer we're ready
					try {
						start.await(); // Wait till peers are ready
						action.run();
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					} finally {
						done.countDown(); // Tell timer we're done
					}
				}
			});
		}
		ready.await(); // Wait for all workers to be ready
		long startNanos = System.nanoTime();
		start.countDown(); // And they're off!
		done.await(); // Wait for all workers to finish
		return System.nanoTime() - startNanos;
	}
	
	
	public class TaskUnit implements Runnable {

		public void run(){
			
			Random rnd = new Random();
			int time = rnd.nextInt(1000);
			System.out.println("[" + Thread.currentThread().getName() + "] Processing time : " +  time);
			try {
				Thread.sleep(rnd.nextInt(time));
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		try {
			long elapsedTime = performanceTest(executor, 10, new CountDownLatchExample().new TaskUnit());
			System.out.println("FINAL(Milliseconds) : " + elapsedTime/1000000);
			System.out.println("FINAL(Milliseconds) : " + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS));
			
		} catch (InterruptedException e) {}
		executor.shutdown();
	}

}
