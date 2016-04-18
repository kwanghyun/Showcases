package concurrency.book.threadpool;

import java.util.concurrent.atomic.*;
import java.util.logging.*;

/**
 * MyAppThread
 * <p/>
 * Custom thread base class
 *
 * It instantiates a new MyAppThread, passing a pool-specific name to the
 * constructor so that threads from each pool can be distinguished in thread
 * dumps and error logs. My-AppThread can also be used elsewhere in the
 * application so that all threads can take advantage of its debugging features.
 * 
 * The interesting customization takes place in MyAppThread, shown in Listing
 * 8.7, which lets you provide a thread name, sets a custom
 * UncaughtException-Handler that writes a message to a Logger, maintains
 * statistics on how many threads have been created and destroyed, and
 * optionally writes a debug message to the log when a thread is created or
 * terminates.
 * 
 * If your application takes advantage of security policies to grant permissions
 * to particular codebases, you may want to use the privilegedThreadFactory
 * factory method in Executors to construct your thread factory. It creates pool
 * threads that have the same permissions, AccessControlContext, and
 * contextClassLoader as the thread creating the privilegedThreadFactory.
 * Otherwise, threads created by the thread pool inherit permissions from
 * whatever client happens to be calling execute or submit at the time a new
 * thread is needed, which could cause confusing security-related exceptions
 */
public class I04_MyAppThread extends Thread {
	public static final String DEFAULT_NAME = "MyAppThread";
	private static volatile boolean debugLifecycle = false;
	private static final AtomicInteger created = new AtomicInteger();
	private static final AtomicInteger alive = new AtomicInteger();
	private static final Logger log = Logger.getAnonymousLogger();

	public I04_MyAppThread(Runnable r) {
		this(r, DEFAULT_NAME);
	}

	public I04_MyAppThread(Runnable runnable, String name) {
		super(runnable, name + "-" + created.incrementAndGet());
		setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			public void uncaughtException(Thread t, Throwable e) {
				log.log(Level.SEVERE, "UNCAUGHT in thread " + t.getName(), e);
			}
		});
	}

	public void run() {
		// Copy debug flag to ensure consistent value throughout.
		boolean debug = debugLifecycle;
		if (debug)
			log.log(Level.FINE, "Created " + getName());
		try {
			alive.incrementAndGet();
			super.run();
		} finally {
			alive.decrementAndGet();
			if (debug)
				log.log(Level.FINE, "Exiting " + getName());
		}
	}

	public static int getThreadsCreated() {
		return created.get();
	}

	public static int getThreadsAlive() {
		return alive.get();
	}

	public static boolean getDebug() {
		return debugLifecycle;
	}

	public static void setDebug(boolean b) {
		debugLifecycle = b;
	}
}