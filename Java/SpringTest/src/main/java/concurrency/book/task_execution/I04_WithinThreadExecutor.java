package concurrency.book.task_execution;

import java.util.concurrent.*;

/**
 * WithinThreadExecutor
 * <p/>
 * Executor that executes tasks synchronously in the calling thread
 *
 */
public class I04_WithinThreadExecutor implements Executor {
	public void execute(Runnable r) {
		r.run();
	};
}