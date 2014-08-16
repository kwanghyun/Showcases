package concurrency.semaphore;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class MySemaphoreTest {

	private int max_avaliable = 2;
	private final int wating = 3000;
	private Map<String, String> seedData = new HashMap<String, String>();

	private final Semaphore semaphore = new Semaphore(max_avaliable, true);

	public void firstMethod(String key, String value) throws InterruptedException {
		System.out.println("[" + Thread.currentThread().getName()
				+ "] FIRST - Entering");
		semaphore.acquire();
		System.out.println("[" + Thread.currentThread().getName()
				+ "] FIRST - After Acquire");
		seedData.put(key, value);
		Thread.sleep(wating);
		secondMethod();
		System.out.println("[" + Thread.currentThread().getName()
				+ "] FIRST - Exiting");
		
	}
	
	public void secondMethod() throws InterruptedException {
		System.out.println("[" + Thread.currentThread().getName()
				+ "] MIDDLE - Entering");
		Thread.sleep(wating);
		semaphore.release();
		System.out.println("[" + Thread.currentThread().getName()
				+ "] MIDDLE - After Release");
		Thread.sleep(wating);
		System.out.println("[" + Thread.currentThread().getName()
				+ "] MIDDLE - Exiting");
	}

	public boolean isDataExist(String key) {
		return seedData.containsKey(key);
	}

	public static class Producer implements Runnable {

		int index;
		MySemaphoreTest shareData;

		public Producer(int index, MySemaphoreTest shareData) {
			super();
			this.index = index;
			this.shareData = shareData;
		}

		public void run() {
			try {
				shareData.firstMethod("Key" + index, "Value" + index);
			} catch (InterruptedException e) {
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final MySemaphoreTest aUserAction = new MySemaphoreTest();
		int nTestThread = 2;
		
		for (int i = 0; i < nTestThread; i++)
			new Thread(new Producer(i, aUserAction)).start();

	}
}

/*###OUT PUT
 * 
 * 
[Thread-0] WRITE - Entering
[Thread-0] WRITE - After Acquire
[Thread-3] WRITE - Entering
[Thread-1] WRITE - Entering
[Thread-3] WRITE - After Acquire
[Thread-2] WRITE - Entering

[Thread-0] MIDDLE - Entering
[Thread-3] MIDDLE - Entering

[Thread-3] MIDDLE - After Release
[Thread-2] WRITE - After Acquire
[Thread-1] WRITE - After Acquire
[Thread-0] MIDDLE - After Release

[Thread-1] MIDDLE - Entering
[Thread-0] MIDDLE - Exiting
[Thread-3] MIDDLE - Exiting
[Thread-3] WRITE - Exiting
[Thread-2] MIDDLE - Entering
[Thread-0] WRITE - Exiting

[Thread-2] MIDDLE - After Release
[Thread-1] MIDDLE - After Release

[Thread-1] MIDDLE - Exiting
[Thread-2] MIDDLE - Exiting
[Thread-2] WRITE - Exiting
[Thread-1] WRITE - Exiting
 * 
 * 
 * */

