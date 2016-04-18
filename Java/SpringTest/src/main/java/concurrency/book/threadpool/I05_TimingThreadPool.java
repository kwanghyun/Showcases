package concurrency.book.threadpool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.logging.*;

/**
 * TimingThreadPool
 * <p/>
 * Thread pool extended with logging and timing
 * 
 * TimingThreadPool in Listing 8.9 shows a custom thread pool that uses
 * before-Execute, afterExecute, and terminated to add logging and statistics
 * gathering. To measure a task's runtime, beforeExecute must record the start
 * time and store it somewhere afterExecute can find it. Because execution hooks
 * are called in the thread that executes the task, a value placed in a
 * ThreadLocal by beforeExecute can be retrieved by afterExecute.
 * TimingThreadPool uses a pair of AtomicLongs to keep track of the total number
 * of tasks processed and the total processing time, and uses the terminated
 * hook to print a log message showing the average task time.
 */
public class I05_TimingThreadPool extends ThreadPoolExecutor {

	public I05_TimingThreadPool() {
		super(1, 1, 0L, TimeUnit.SECONDS, null);
	}

	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	private final Logger log = Logger.getLogger("TimingThreadPool");
	private final AtomicLong numTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		log.fine(String.format("Thread %s: start %s", t, r));
		startTime.set(System.nanoTime());
	}

	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			log.fine(String.format("Thread %s: end %s, time=%dns", t, r, taskTime));
		} finally {
			super.afterExecute(r, t);
		}
	}

	protected void terminated() {
		try {
			log.info(String.format("Terminated: avg time=%dns", totalTime.get() / numTasks.get()));
		} finally {
			super.terminated();
		}
	}
}