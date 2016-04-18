package concurrency.book.explicit_lock;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * TimedLocking
 * <p/>
 * Locking with a time budget
 *

 */
public class I02_TimedLocking {
	/*
	 * java.util.concurrent.locks.ReentrantLock.ReentrantLock()
	 * 
	 * Creates an instance of ReentrantLock. This is equivalent to using
	 * ReentrantLock(false).
	 */
	private Lock lock = new ReentrantLock();

	public boolean trySendOnSharedLine(String message, long timeout, TimeUnit unit) throws InterruptedException {
		long nanosToLock = unit.toNanos(timeout) - estimatedNanosToSend(message);
		if (!lock.tryLock(nanosToLock, NANOSECONDS))
			return false;
		try {
			return sendOnSharedLine(message);
		} finally {
			lock.unlock();
		}
	}

	private boolean sendOnSharedLine(String message) {
		/* send something */
		return true;
	}

	long estimatedNanosToSend(String message) {
		return message.length();
	}
}