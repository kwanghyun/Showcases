package concurrency.showcases;

import java.util.concurrent.TimeUnit;

import transaction.entity.Account;

/* 
 * 1. Synchronization is required for reliable communication between threads as well as for 
 * 	mutual exclusion. (Like the bellow example)
 * 2. synchronization has no effect unless both read and write operations are synchronized.
 * */
public class SynchronizedTest {

	private static boolean stopRequested;

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				while (true) {
					try {
						wait();
					} catch (InterruptedException e) {
						System.out.println("Thread1 Interupted :"
								+ e.getMessage());
						
					}
					System.out.println("Thread1 count : " + i++);
				}
			}
		}, "Thread1");

		Thread thread2 = new Thread(new Runnable() {
			public void run() {
				int i = 0;
				while (true) {
					System.out.println("Thread2 Started : " + i++);
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {}
					notify();
					System.out.println("Thread2 count : " + i++);
				}
			}
		}, "Thread2");
		
		thread1.start();

		TimeUnit.SECONDS.sleep(1);
		thread1.start();

	}
}
