package concurrency.book.shutdown;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

/**
 * CheckForMail
 * <p/>
 * Using a private \Executor whose lifetime is bounded by a method call
 *
 * The checkMail method in Listing 7.20 checks for new mail in parallel on a
 * number of hosts. It creates a private executor and submits a task for each
 * host: it then shuts down the executor and waits for termination, which occurs
 * when all the mail-checking tasks have completed.[4]
 * 
 * [4] The reason an AtomicBoolean is used instead of a volatile boolean is that
 * in order to access the hasNewMail flag from the inner Runnable, it would have
 * to be final, which would preclude modifying it.
 */
public class I13_CheckForMail {
	public boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
		
		ExecutorService exec = Executors.newCachedThreadPool();
		final AtomicBoolean hasNewMail = new AtomicBoolean(false);
		
		try {
			for (final String host : hosts)
				exec.execute(new Runnable() {
					public void run() {
						if (checkMail(host))
							hasNewMail.set(true);
					}
				});
		} finally {
			exec.shutdown();
			exec.awaitTermination(timeout, unit);
		}
		return hasNewMail.get();
	}

	private boolean checkMail(String host) {
		// Check for mail
		return false;
	}
}