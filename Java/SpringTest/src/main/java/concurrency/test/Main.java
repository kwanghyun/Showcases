package concurrency.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(2);

		Thread.sleep(1000);

		pool.execute(new Runnable() {
			public void run() {
				throw new RuntimeException();
			}
		});
		pool.execute(new Runnable() {
			public void run() {
				throw new RuntimeException();
			}
		});

		pool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + " : Are you ok1?");
			}
		});

		pool.execute(new Runnable() {
			public void run() {
				throw new RuntimeException();
			}
		});

		Thread.sleep(1000);
		pool.execute(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + " : Are you ok2?");
			}
		});
	}
}
