package concurrency.book.shutdown;

import java.util.concurrent.*;

/**
 * NoncancelableTask
 * <p/>
 * Noncancelable task that restores interruption before exit
 *
 * If you don't want to or cannot propagate InterruptedException (perhaps
 * because your task is defined by a Runnable), you need to find another way to
 * preserve the interruption request. The standard way to do this is to restore
 * the interrupted status by calling interrupt again. What you should not do is
 * swallow the InterruptedException by catching it and doing nothing in the
 * catch block, unless your code is actually implementing the interruption
 * policy for a thread. PrimeProducer swallows the interrupt, but does so with
 * the knowledge that the thread is about to terminate and that therefore there
 * is no code higher up on the call stack that needs to know about the
 * interruption. Most code does not know what thread it will run in and so
 * should preserve the interrupted status.
 * 
 */
public class I04_NoncancelableTask {
	public Task getNextTask(BlockingQueue<Task> queue) {
		boolean interrupted = false;
		try {
			while (true) {
				try {
					return queue.take();
				} catch (InterruptedException e) {
					interrupted = true;
					// fall through and retry
				}
			}
		} finally {
			if (interrupted)
				Thread.currentThread().interrupt();
		}
	}

	interface Task {
	}
}