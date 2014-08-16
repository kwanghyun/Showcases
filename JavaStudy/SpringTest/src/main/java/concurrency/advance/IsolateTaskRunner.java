package concurrency.advance;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import junit.framework.TestCase;

public class IsolateTaskRunner {

	private Map<Class<? extends Runnable>, Lock> mLocks = new HashMap<Class<? extends Runnable>, Lock>();

	public void runTaskIsolate(Runnable task, int secondsToWait) throws InterruptedException {
		Lock lock = getLock(task.getClass());
		boolean acquired = lock.tryLock(secondsToWait, TimeUnit.SECONDS);
		if (acquired) {
			try {
				task.run();
			} finally {
				lock.unlock();
			}
		} else {
			// failure code here
		}
	}

	private synchronized Lock getLock(Class clazz) {
		Lock look = mLocks.get(clazz);
		if (look == null) {
			look = new ReentrantLock();
			mLocks.put(clazz, look);
		}
		return look;
	}
}
