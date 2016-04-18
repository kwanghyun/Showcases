package concurrency.book.explicit_lock;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * DeadlockAvoidance
 * <p/>
 * Avoiding lock-ordering deadlock using tryLock
 *
 * Just as timed lock acquisition allows exclusive locking to be used within
 * time-limited activities, interruptible lock acquisition allows locking to be
 * used within cancellable activities. Section 7.1.6 identified several
 * mechanisms, such as acquiring an intrinsic lock, that are not responsive to
 * interruption. These noninterruptible blocking mechanisms complicate the
 * implementation of cancellable tasks. The lockInterruptibly method allows you
 * to try to acquire a lock while remaining responsive to interruption, and its
 * inclusion in Lock avoids creating another category of non-interruptible
 * blocking mechanisms.
 */
public class I01_DeadlockAvoidance {
	private static Random rnd = new Random();

	public boolean transferMoney(Account fromAcct, Account toAcct, DollarAmount amount, long timeout, TimeUnit unit)
			throws InsufficientFundsException, InterruptedException {
		long fixedDelay = getFixedDelayComponentNanos(timeout, unit);
		long randMod = getRandomDelayModulusNanos(timeout, unit);
		long stopTime = System.nanoTime() + unit.toNanos(timeout);

		while (true) {
			/*
			 * tryLock(): Acquires the lock only if it is free at the time of
			 * invocation.
			 * 
			 * Acquires the lock if it is available and returns immediately with
			 * the value true. If the lock is not available then this method
			 * will return immediately with the value false.
			 */
			if (fromAcct.lock.tryLock()) {
				try {
					if (toAcct.lock.tryLock()) {
						try {
							if (fromAcct.getBalance().compareTo(amount) < 0)
								throw new InsufficientFundsException();
							else {
								fromAcct.debit(amount);
								toAcct.credit(amount);
								return true;
							}
						} finally {
							toAcct.lock.unlock();
						}
					}
				} finally {
					fromAcct.lock.unlock();
				}
			}
			if (System.nanoTime() < stopTime)
				return false;
			NANOSECONDS.sleep(fixedDelay + rnd.nextLong() % randMod);
		}
	}

	private static final int DELAY_FIXED = 1;
	private static final int DELAY_RANDOM = 2;

	static long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
		return DELAY_FIXED;
	}

	static long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
		return DELAY_RANDOM;
	}

	static class DollarAmount implements Comparable<DollarAmount> {
		public int compareTo(DollarAmount other) {
			return 0;
		}

		DollarAmount(int dollars) {
		}
	}

	class Account {
		/*
		 * Lock implementations provide more extensive locking operations than
		 * can be obtained using synchronized methods and statements. They allow
		 * more flexible structuring, may have quite different properties, and
		 * may support multiple associated Condition objects.
		 * 
		 * A lock is a tool for controlling access to a shared resource by
		 * multiple threads. Commonly, a lock provides exclusive access to a
		 * shared resource: only one thread at a time can acquire the lock and
		 * all access to the shared resource requires that the lock be acquired
		 * first. However, some locks may allow concurrent access to a shared
		 * resource, such as the read lock of a ReadWriteLock.
		 */
		public Lock lock;

		void debit(DollarAmount d) {
		}

		void credit(DollarAmount d) {
		}

		DollarAmount getBalance() {
			return null;
		}
	}

	class InsufficientFundsException extends Exception {
	}
}