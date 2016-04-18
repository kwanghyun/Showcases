package concurrency.book.explicit_lock;

import java.util.concurrent.locks.*;

/**
 * InterruptibleLocking
 *
 * Just as timed lock acquisition allows exclusive locking to be used within
 * timelimited activities, interruptible lock acquisition allows locking to be
 * used within cancellable activities.
 * 
 * Just as timed lock acquisition allows exclusive locking to be used within
 * timelimited activities, interruptible lock acquisition allows locking to be
 * used within cancellable activities. Section 7.1.6 identified several
 * mechanisms, such as acquiring an intrinsic lock, that are not responsive to
 * interruption. These noninterruptible blocking mechanisms complicate the
 * implementation of cancellable tasks. The lockInterruptibly method allows you
 * to try to acquire a lock while remaining responsive to interruption, and its
 * inclusion in Lock avoids creating another category of non-interruptible
 * blocking mechanisms.
 *
 * The canonical structure of interruptible lock acquisition is slightly more
 * complicated than normal lock acquisition, as two try blocks are needed. (If
 * the interruptible lock acquisition can throw InterruptedException, the
 * standard try-finally locking idiom works.) Listing 13.5 uses
 * lockInterruptibly to implement sendOnSharedLine from Listing 13.4 so that we
 * can call it from a cancellable task. The timed tryLock is also responsive to
 * interruption and so can be used when you need both timed and interruptible
 * lock acquisition.
 */
public class I03_InterruptibleLocking {
	private Lock lock = new ReentrantLock();

	public boolean sendOnSharedLine(String message) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			return cancellableSendOnSharedLine(message);
		} finally {
			lock.unlock();
		}
	}

	private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
		/* send something */
		return true;
	}

}
