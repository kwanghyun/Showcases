package concurrency.book.shutdown;

import java.util.concurrent.*;

import concurrency.book.building_blocks.LaunderThrowable;

/**
 * TimedRun
 * <p/>
 * Cancelling a task using Future
 *
 * We've already used an abstraction for managing the lifecycle of a task,
 * dealing with exceptions, and facilitating cancellation—Future. Following the
 * general principle that it is better to use existing library classes than to
 * roll your own, let's build timedRun using Future and the task execution
 * framework.
 */
public class I07_TimedRun {
	private static final ExecutorService taskExec = Executors.newCachedThreadPool();

	public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
		Future<?> task = taskExec.submit(r);
		try {
			task.get(timeout, unit);
		} catch (TimeoutException e) {
			// task will be cancelled below
		} catch (ExecutionException e) {
			// exception thrown in task; rethrow
			throw LaunderThrowable.launderThrowable(e.getCause());
		} finally {
			// Harmless if task already completed
			task.cancel(true); // interrupt if running
		}
	}
}