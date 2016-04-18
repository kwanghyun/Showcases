package concurrency.book.threadpool;

import java.util.concurrent.*;

/**
 * MyThreadFactory
 * <p/>
 * Custom thread factory
 *
 * Whenever a thread pool needs to create a thread, it does so through a thread
 * factory (see Listing 8.5). The default thread factory creates a new,
 * nondaemon thread with no special configuration. Specifying a thread factory
 * allows you to customize the configuration of pool threads. ThreadFactory has
 * a single method, newThread, that is called whenever a thread pool needs to
 * create a new thread.
 *
 * There are a number of reasons to use a custom thread factory. You might want
 * to specify an UncaughtExceptionHandler for pool threads, or instantiate an
 * instance of a custom Thread class, such as one that performs debug logging.
 * You might want to modify the priority (generally not a very good idea; see
 * Section 10.3.1) or set the daemon status (again, not all that good an idea;
 * see Section 7.4.2) of pool threads. Or maybe you just want to give pool
 * threads more meaningful names to simplify interpreting thread dumps and error
 * logs.
 */
public class I03_MyThreadFactory implements ThreadFactory {
	private final String poolName;

	public I03_MyThreadFactory(String poolName) {
		this.poolName = poolName;
	}

	public Thread newThread(Runnable runnable) {
		return new I04_MyAppThread(runnable, poolName);
	}
}