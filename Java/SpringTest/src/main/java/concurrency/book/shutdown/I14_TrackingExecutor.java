package concurrency.book.shutdown;

import java.util.*;
import java.util.concurrent.*;

/**
 * TrackingExecutor
 * <p/>
 * ExecutorService that keeps track of cancelled tasks after shutdown
 *
 * When an ExecutorService is shut down abruptly with shutdownNow, it attempts
 * to cancel the tasks currently in progress and returns a list of tasks that
 * were submitted but never started so that they can be logged or saved for
 * later processing.
 * 
 * However, there is no general way to find out which tasks started but did not
 * complete. This means that there is no way of knowing the state of the tasks
 * in progress at shutdown time unless the tasks themselves perform some sort of
 * checkpointing. To know which tasks have not completed, you need to know not
 * only which tasks didn't start, but also which tasks were in progress when the
 * executor was shut down.[6]
 * 
 * TrackingExecutor in Listing 7.21 shows a technique for determining which
 * tasks were in progress at shutdown time. By encapsulating an ExecutorService
 * and instrumenting execute (and similarly submit, not shown) to remember which
 * tasks were cancelled after shutdown, TrackingExecutor can identify which
 * tasks started but did not complete normally. After the executor terminates,
 * getCancelledTasks returns the list of cancelled tasks. In order for this
 * technique to work, the tasks must preserve the thread's interrupted status
 * when they return, which well behaved tasks will do anyway.
 */
public class I14_TrackingExecutor extends AbstractExecutorService {
	private final ExecutorService exec;

	/*
	 * Collections.synchronizedSet() : Returns a synchronized (thread-safe) set
	 * backed by the specified set. In order to guarantee serial access, it is
	 * critical that all access to the backing set is accomplished through the
	 * returned set.
	 * 
	 * It is imperative that the user manually synchronize on the returned set
	 * when iterating over it:
	 */
	private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<Runnable>());

	public I14_TrackingExecutor(ExecutorService exec) {
		this.exec = exec;
	}

	public void shutdown() {
		exec.shutdown();
	}

	/*
	 * Attempts to stop all actively executing tasks, halts the processing of
	 * waiting tasks, and returns a list of the tasks that were awaiting
	 * execution.
	 */
	public List<Runnable> shutdownNow() {
		return exec.shutdownNow();
	}

	public boolean isShutdown() {
		return exec.isShutdown();
	}

	public boolean isTerminated() {
		return exec.isTerminated();
	}

	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return exec.awaitTermination(timeout, unit);
	}

	public List<Runnable> getCancelledTasks() {
		if (!exec.isTerminated())
			throw new IllegalStateException(/* ... */);
		return new ArrayList<Runnable>(tasksCancelledAtShutdown);
	}

	public void execute(final Runnable runnable) {
		exec.execute(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} finally {
					if (isShutdown() && Thread.currentThread().isInterrupted())
						tasksCancelledAtShutdown.add(runnable);
				}
			}
		});
	}
}